package uk.gov.ofwat.external.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URISyntaxException;

import java.util.List;

/**
 * Service Implementation for managing Publishing.
 */
@Service
@Transactional
public class PublishingService {

    private final Logger log = LoggerFactory.getLogger(PublishingService.class);
    private final CompanyRepository companyRepository;
    private final DataCollectionRepository dataCollectionRepository;
    private final DataBundleRepository dataBundleRepository;
    private final UserRepository userRepository;
    private final CompanyStatusRepository companyStatusRepository;
    private final CompanyDataCollectionRepository companyDataCollectionRepository;
    private final CompanyDataBundleRepository companyDataBundleRepository;
    private final CompanyDataInputRepository companyDataInputRepository;
    private final InputTypeRepository inputTypeRepository;


    public PublishingService(CompanyRepository companyRepository, DataCollectionRepository dataCollectionRepository,
                             DataInputRepository dataInputRepository, UserRepository userRepository,
                             CompanyStatusRepository companyStatusRepository, DataBundleRepository dataBundleRepository, CompanyDataBundleRepository companyDataBundleRepository,
                             CompanyDataCollectionRepository companydataCollectionRepository, CompanyDataInputRepository companyDataInputRepository, InputTypeRepository inputTypeRepository) {
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
     * PUT  /data-bundles : Updates an existing dataBundle.
     *
     * @param dataBundleDTO the dataBundleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataBundleDTO,
     * or with status 400 (Bad Request) if the dataBundleDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataBundleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    public boolean publishDataBundleStatus(DataBundleDTO dataBundleDTO) throws URISyntaxException {

        boolean success = false;
        List<Company> listOfCompanies = companyRepository.findAll();

        for (Company company : listOfCompanies) {
            //Get data collection
            DataCollection dataCollection = dataCollectionRepository.findOne(dataBundleDTO.getDataCollectionId());
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
                CompanyDataInput companyDataInput = new CompanyDataInput();
                companyDataInput.setDataInput(dataInput);
                companyDataInput.setCompanyReviewer(userRepository.findOne(dataBundleDTO.getReviewerId()));
                companyDataInput.setCompany(company);
                companyDataInput.setCompanyOwner(userRepository.findOne(dataBundleDTO.getOwnerId()));
                InputType inputType = new InputType();
                companyDataInput.setInputType(inputTypeRepository.findOne(new Long(1)));
                companyDataInput.setName(dataBundleDTO.getName());
                companyDataInput.setOrderIndex(new Long(0));
                companyDataInput.setStatus(companyStatusRepository.findOne(dataCollection.getPublishingStatus().getId()));
                companyDataInput.setCompanyDataBundle(companyDataBundle);
                companyDataInputRepository.save(companyDataInput);
                success = true;
            }
        }
        return success;
    }
}
