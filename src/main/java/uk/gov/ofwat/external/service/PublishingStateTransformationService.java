package uk.gov.ofwat.external.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Publishing.
 */
@Service
@Transactional
public class PublishingStateTransformationService {

    private final Logger log = LoggerFactory.getLogger(PublishingStateTransformationService.class);
    private final CompanyRepository companyRepository;
    private final DataCollectionRepository dataCollectionRepository;
    private final DataBundleRepository dataBundleRepository;
    private final DataInputRepository dataInputRepository;
    private final UserRepository userRepository;
    private final CompanyStatusRepository companyStatusRepository;
    private final CompanyDataCollectionRepository companyDataCollectionRepository;
    private final CompanyDataInputRepository companyDataInputRepository;
    private final InputTypeRepository inputTypeRepository;
    private final CompanyDataBundleRepository companyDataBundleRepository;
    private final PublishingStatusRepository publishingStatusRepository;

    /**
     * Constructor for intialisation
     *
     * @param companyRepository
     * @param dataCollectionRepository
     * @param userRepository
     * @param companyStatusRepository
     * @param dataBundleRepository
     * @param companyDataBundleRepository
     * @param companydataCollectionRepository
     * @param companyDataInputRepository
     * @param inputTypeRepository
     * @param dataInputRepository
     * @param publishingStatusRepository
     */
    public PublishingStateTransformationService(CompanyRepository companyRepository, DataCollectionRepository dataCollectionRepository,
                                                UserRepository userRepository,
                                                CompanyStatusRepository companyStatusRepository, DataBundleRepository dataBundleRepository, CompanyDataBundleRepository companyDataBundleRepository,
                                                CompanyDataCollectionRepository companydataCollectionRepository, CompanyDataInputRepository companyDataInputRepository, InputTypeRepository inputTypeRepository,
                                                DataInputRepository dataInputRepository, PublishingStatusRepository publishingStatusRepository) {
        this.companyRepository = companyRepository;
        this.dataCollectionRepository = dataCollectionRepository;
        this.userRepository = userRepository;
        this.companyStatusRepository = companyStatusRepository;
        this.dataBundleRepository = dataBundleRepository;
        this.companyDataCollectionRepository = companydataCollectionRepository;
        this.companyDataBundleRepository = companyDataBundleRepository;
        this.companyDataInputRepository = companyDataInputRepository;
        this.inputTypeRepository = inputTypeRepository;
        this.dataInputRepository = dataInputRepository;
        this.publishingStatusRepository = publishingStatusRepository;
    }

    /**
     * Gets called on change to dataBundle status
     *
     * @param dataBundleDTO
     * @return true or false based on success
     * @throws URISyntaxException
     */
    public boolean publishDataBundleStatus(DataBundleDTO dataBundleDTO) throws URISyntaxException, DcsException {

        boolean success = false;
        try {
            List<Company> listOfCompanies = companyRepository.findAll();

            DataCollection dataCollection = dataCollectionRepository.findOne(dataBundleDTO.getDataCollectionId());
            Optional<PublishingStatus> published = publishingStatusRepository.findOneByStatus("PUBLISHED");
            if ((dataCollection.getPublishingStatus().getId().equals(new Long(4)))
                || (dataCollection.getPublishingStatus().getId().equals(new Long(3)))) {

                //Publish data collection
                dataCollection.setPublishingStatus(published.get());
                dataCollectionRepository.save(dataCollection);
                Long orderIndexL = new Long(0);
                for (Company company : listOfCompanies) {

                    //Get data collection
                    CompanyDataCollection companyDataCollection = companyDataCollectionRepository.findByCompanyByDataCollectionAndCompany(dataCollection.getId(),company.getId());
                    if(companyDataCollection==null)
                    {
                        companyDataCollection = new CompanyDataCollection();
                        companyDataCollection.setCompany(company);
                        companyDataCollection.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                        companyDataCollection.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                        companyDataCollection.setDataCollection(dataCollection);
                        companyDataCollection.setName(company.getName());
                        companyDataCollection.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                        companyDataCollection.setCompanyDataCollectionOrderIndex(orderIndexL);
                        companyDataCollectionRepository.save(companyDataCollection);
                    }


                    CompanyDataBundle companyDataBundle = new CompanyDataBundle();
                    companyDataBundle.setCompany(company);
                    companyDataBundle.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                    Long maxCount = new Long (0);
                    maxCount = companyDataBundleRepository.findByCompanyByDataCollectionAndCompany(companyDataCollection.getId(), company.getId());

                    companyDataBundle.setOrderIndex(maxCount++);
                    companyDataBundle.setCompanyDeadline(dataBundleDTO.getDefaultDeadline());
                    companyDataBundle.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                    companyDataBundle.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                    companyDataBundle.setDataBundle(dataBundleRepository.findOne(dataBundleDTO.getId()));
                    companyDataBundle.setName(dataBundleDTO.getName());
                    companyDataBundle.setCompanyDataCollection(companyDataCollection);
                    companyDataBundle.setCompanyDataBundleOrderIndex(orderIndexL);
                    companyDataBundleRepository.save(companyDataBundle);

                    List<DataInput> dataInputList = new ArrayList(Arrays.asList(dataCollection.getDataBundles()[0].getDataInputs()));

                    //Set Company Data Inputs
                    for (DataInput dataInput : dataInputList) {
                        if ((dataInput.getStatus().getId().equals(new Long(3))) || (dataInput.getStatus().getId().equals(new Long(4)))) {

                            CompanyDataInput companyDataInput = new CompanyDataInput();
                            companyDataInput.setDataInput(dataInput);
                            companyDataInput.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                            companyDataInput.setCompany(company);
                            companyDataInput.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                            companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                            companyDataInput.setName(dataBundleDTO.getName());
                            companyDataInput.setOrderIndex(new Long(0));
                            companyDataInput.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                            companyDataInput.setCompanyDataBundle(companyDataBundle);
                            companyDataInput.setCompanyDataInputOrderIndex(orderIndexL);
                            companyDataInputRepository.save(companyDataInput);
                            //Publish data input
                            dataInput.setStatus(published.get());
                            dataInputRepository.save(dataInput);
                        }
                        success = true;
                    }
                    orderIndexL++;
                }
            } else {
                throw new DcsException("Data Collection " + dataCollection.getName() + " not published yet ");
            }
        } catch (Exception e) {
            throw new DcsException(e.getMessage());
        }
        return success;

    }

    /**
     * Gets called on change in Input status
     *
     * @param dataInputDTO
     * @return true of false based on success or failure of change
     * @throws URISyntaxException
     */
    public boolean publishDataInputStatus(DataInputDTO dataInputDTO) throws URISyntaxException, DcsException {
        boolean success = false;
        try {
            DataBundle dataBundle = dataBundleRepository.findOne(dataInputDTO.getDataBundleId());
            DataInput dataInput = dataInputRepository.findOne(dataInputDTO.getId());
            DataCollection dataCollection = dataCollectionRepository.findOne(dataBundle.getDataCollection().getId());
            if ((dataCollection.getPublishingStatus().getId().equals(new Long(4)))
                && (dataBundle.getStatus().getId().equals(new Long(4)))) {
                // if ((dataInput.getStatus().getId().equals(new Long(4)))) {
                List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findByCompanyAndBundle(dataBundle.getId());
                if(companyDataBundleList.size()==0)
                    throw new DcsException("Company Data Bundle does not exist for this Data Input");
                Optional<CompanyStatus> companyStatus = companyStatusRepository.findOneByStatus("PUBLISHED");
                Long orderIndexL = new Long(0);
                for (CompanyDataBundle companyDataBundle : companyDataBundleList) {
                    CompanyDataInput companyDataInput = new CompanyDataInput();
                    companyDataInput.setDataInput(dataInput);
                    companyDataInput.setCompanyReviewer(userRepository.findOne(dataInput.getReviewer().getId()));
                    companyDataInput.setCompany(companyDataBundle.getCompany());
                    companyDataInput.setCompanyOwner(userRepository.findOne(dataInput.getOwner().getId()));
                    companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                    companyDataInput.setName(dataInput.getName());
                    companyDataInput.setOrderIndex(new Long(0));
                    companyDataInput.setStatus(companyStatus.get());
                    companyDataInput.setCompanyDataBundle(companyDataBundle);
                    companyDataInput.setCompanyDataInputOrderIndex(orderIndexL);
                    companyDataInputRepository.save(companyDataInput);
                    orderIndexL++;
                }

            } else {
                throw new DcsException("Data Collection " + dataCollection.getName() + " or Data Bundle " + dataBundle.getName() + " not published yet ");
            }
        } catch (Exception e) {
            throw new DcsException(e.getMessage());
        }
        return success;
    }
}
