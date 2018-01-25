package uk.gov.ofwat.external.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.ofwat.external.service.dto.DataInputDTO;

import java.net.URISyntaxException;

import java.util.List;

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
    private final UserRepository userRepository;
    private final CompanyStatusRepository companyStatusRepository;
    private final CompanyDataCollectionRepository companyDataCollectionRepository;
    private final CompanyDataInputRepository companyDataInputRepository;
    private final InputTypeRepository inputTypeRepository;
    private final CompanyDataBundleRepository companyDataBundleRepository;

    public PublishingStateTransformationService(CompanyRepository companyRepository, DataCollectionRepository dataCollectionRepository,
                                                UserRepository userRepository,
                                                CompanyStatusRepository companyStatusRepository, DataBundleRepository dataBundleRepository, CompanyDataBundleRepository companyDataBundleRepository,
                                                CompanyDataCollectionRepository companydataCollectionRepository, CompanyDataInputRepository companyDataInputRepository, InputTypeRepository inputTypeRepository
    ) {
        this.companyRepository = companyRepository;
        this.dataCollectionRepository = dataCollectionRepository;
        this.userRepository = userRepository;
        this.companyStatusRepository = companyStatusRepository;
        this.dataBundleRepository = dataBundleRepository;
        this.companyDataCollectionRepository = companydataCollectionRepository;
        this.companyDataBundleRepository = companyDataBundleRepository;
        this.companyDataInputRepository = companyDataInputRepository;
        this.inputTypeRepository = inputTypeRepository;
    }

    /**
     * @param dataBundleDTO
     * @return
     * @throws URISyntaxException
     */
    public boolean publishDataBundleStatus(DataBundleDTO dataBundleDTO) throws URISyntaxException {

        boolean success = false;
        List<Company> listOfCompanies = companyRepository.findAll();

        DataCollection dataCollection = dataCollectionRepository.findOne(dataBundleDTO.getDataCollectionId());
        if ((dataCollection.getPublishingStatus().getId().equals(new Long(4)))
            || (dataCollection.getPublishingStatus().getId().equals(new Long(3)))) {

            for (Company company : listOfCompanies) {
                //Get data collection
                CompanyDataCollection companyDataCollection = new CompanyDataCollection();
                companyDataCollection.setCompany(company);
                companyDataCollection.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                companyDataCollection.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                companyDataCollection.setDataCollection(dataCollection);
                companyDataCollection.setName(company.getName());
                companyDataCollection.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                companyDataCollectionRepository.save(companyDataCollection);

                //Set Company Data Bundles
                CompanyDataBundle companyDataBundle = new CompanyDataBundle();
                companyDataBundle.setCompany(company);
                companyDataBundle.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                companyDataBundle.setOrderIndex(new Long(0));
                companyDataBundle.setCompanyDeadline(dataBundleDTO.getDefaultDeadline());
                companyDataBundle.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                companyDataBundle.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                companyDataBundle.setDataBundle(dataBundleRepository.findOne(dataBundleDTO.getId()));
                companyDataBundle.setName(dataBundleDTO.getName());
                companyDataBundle.setCompanyDataCollection(companyDataCollection);
                companyDataBundleRepository.save(companyDataBundle);

                //Set Company Data Inputs
                for (DataInput dataInput : dataCollection.getDataBundles()[0].getDataInputs()) {
                    if ((dataInput.getStatus().equals(new Long(3))) || (dataInput.getStatus().equals(new Long(4)))) {
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
                        companyDataInputRepository.save(companyDataInput);
                    }
                    success = true;
                }
            }
        }
        return success;

    }

    /**
     * @param dataInputDTO
     * @return
     * @throws URISyntaxException
     */
    public boolean publishDataInputStatus(DataInputDTO dataInputDTO) throws URISyntaxException {

        boolean success = false;

        DataBundle dataBundle = dataBundleRepository.findOne(dataInputDTO.getDataBundleId());
        List<Company> listOfCompanies = companyRepository.findAll();
        DataCollection dataCollection = dataCollectionRepository.findOne(dataBundle.getDataCollection().getId());
        if ((dataCollection.getPublishingStatus().getId().equals(new Long(4)))
            && (dataBundle.getStatus().getId().equals(new Long(4)))) {
            for (DataInput dataInput : dataCollection.getDataBundles()[0].getDataInputs()) {
                if ((dataInput.getStatus().getId().equals(new Long(3))) || (dataInput.getStatus().getId().equals(new Long(4)))) {
                    for (Company company : listOfCompanies) {
                        CompanyDataBundle companyDataBundle = companyDataBundleRepository.findByCompanyAndBundle(company.getId(), dataBundle.getId());
                        CompanyDataInput companyDataInput = new CompanyDataInput();
                        companyDataInput.setDataInput(dataInput);
                        companyDataInput.setCompanyReviewer(userRepository.findOne(dataBundle.getReviewer().getId()));
                        companyDataInput.setCompany(company);
                        companyDataInput.setCompanyOwner(userRepository.findOne(dataBundle.getOwner().getId()));
                        companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                        companyDataInput.setName(dataBundle.getName());
                        companyDataInput.setOrderIndex(new Long(0));
                        companyDataInput.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                        companyDataInput.setCompanyDataBundle(companyDataBundle);
                        companyDataInputRepository.save(companyDataInput);
                    }
                }
                success = true;
            }
        }
        return success;
    }
}
