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

import java.util.ArrayList;
import java.util.Arrays;
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
    private final DataInputRepository dataInputRepository;
    private final UserRepository userRepository;
    private final CompanyStatusRepository companyStatusRepository;
    private final CompanyDataCollectionRepository companyDataCollectionRepository;
    private final CompanyDataInputRepository companyDataInputRepository;
    private final InputTypeRepository inputTypeRepository;
    private final CompanyDataBundleRepository companyDataBundleRepository;

    public PublishingStateTransformationService(CompanyRepository companyRepository, DataCollectionRepository dataCollectionRepository,
                                                UserRepository userRepository,
                                                CompanyStatusRepository companyStatusRepository, DataBundleRepository dataBundleRepository, CompanyDataBundleRepository companyDataBundleRepository,
                                                CompanyDataCollectionRepository companydataCollectionRepository, CompanyDataInputRepository companyDataInputRepository, InputTypeRepository inputTypeRepository,
                                                DataInputRepository dataInputRepository) {
        this.companyRepository = companyRepository;
        this.dataCollectionRepository = dataCollectionRepository;
        this.userRepository = userRepository;
        this.companyStatusRepository = companyStatusRepository;
        this.dataBundleRepository = dataBundleRepository;
        this.companyDataCollectionRepository = companydataCollectionRepository;
        this.companyDataBundleRepository = companyDataBundleRepository;
        this.companyDataInputRepository = companyDataInputRepository;
        this.inputTypeRepository = inputTypeRepository;
        this.dataInputRepository=dataInputRepository;
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
            Long orderIndexL= new Long(0);
            for (Company company : listOfCompanies) {

                //Get data collection
                CompanyDataCollection companyDataCollection = new CompanyDataCollection();
                companyDataCollection.setCompany(company);
                companyDataCollection.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                companyDataCollection.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                companyDataCollection.setDataCollection(dataCollection);
                companyDataCollection.setName(company.getName());
                companyDataCollection.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                companyDataCollection.setCompanyDataCollectionOrderIndex(orderIndexL);
                companyDataCollectionRepository.save(companyDataCollection);

                //dataBundleRepository.findByDataCollection()
                //Set Company Data Bundles
                CompanyDataBundle companyDataBundle = new CompanyDataBundle();
                companyDataBundle.setCompany(company);
                companyDataBundle.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                companyDataBundle.setOrderIndex(orderIndexL);
                companyDataBundle.setCompanyDeadline(dataBundleDTO.getDefaultDeadline());
                companyDataBundle.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                companyDataBundle.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                companyDataBundle.setDataBundle(dataBundleRepository.findOne(dataBundleDTO.getId()));
                companyDataBundle.setName(dataBundleDTO.getName());
                companyDataBundle.setCompanyDataCollection(companyDataCollection);
                companyDataBundle.setCompanyDataBundleOrderIndex(orderIndexL);
                companyDataBundleRepository.save(companyDataBundle);
                List<DataInput> dataInputList = null;
                try {
                  dataInputList = dataInputRepository.findByDataBundle(dataBundleDTO.getId());
                }catch(Exception e){
                    dataInputList = new ArrayList(Arrays.asList(dataCollection.getDataBundles()[0].getDataInputs()));
                    //dataInputList.add(dataInput);
                }
                //Set Company Data Inputs
                for (DataInput dataInput : dataInputList) {
                    if ((dataInput.getStatus().equals(new Long(3))) || (dataInput.getStatus().equals(new Long(4)))) {
                        CompanyDataInput companyDataInput = new CompanyDataInput();
                        companyDataInput.setDataInput(dataInput);
                        companyDataInput.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                        companyDataInput.setCompany(company);
                        companyDataInput.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                        companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                        companyDataInput.setName(dataBundleDTO.getName());
                        companyDataInput.setOrderIndex(orderIndexL);
                        companyDataInput.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                        companyDataInput.setCompanyDataBundle(companyDataBundle);
                        companyDataInput.setCompanyDataInputOrderIndex(orderIndexL);
                        companyDataInputRepository.save(companyDataInput);
                    }
                    success = true;
                }
                orderIndexL++;
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
            DataInput dataInput = dataInputRepository.findOne(dataInputDTO.getId());
             if ((dataInput.getStatus().getId().equals(new Long(4)))) {
                 Long orderIndexL= new Long(0);
                    for (Company company : listOfCompanies) {
                        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findByCompanyAndBundle(dataBundle.getId());
                        CompanyDataBundle companyDataBundle = companyDataBundleList.stream().filter(x->x.getCompany().getId().equals(company.getId())).findFirst().get();
                        CompanyDataInput companyDataInput = new CompanyDataInput();
                        companyDataInput.setDataInput(dataInput);
                        companyDataInput.setCompanyReviewer(userRepository.findOne(dataInput.getReviewer().getId()));
                        companyDataInput.setCompany(company);
                        companyDataInput.setCompanyOwner(userRepository.findOne(dataInput.getOwner().getId()));
                        companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                        companyDataInput.setName(dataInput.getName());
                        companyDataInput.setOrderIndex(orderIndexL);
                        companyDataInput.setStatus(companyStatusRepository.findOne(dataInput.getStatus().getId()));
                        companyDataInput.setCompanyDataBundle(companyDataBundle);
                        companyDataInput.setCompanyDataInputOrderIndex(orderIndexL);
                        companyDataInputRepository.save(companyDataInput);
                        orderIndexL++;
                    }
                }
                success = true;
            }

        return success;
    }
}
