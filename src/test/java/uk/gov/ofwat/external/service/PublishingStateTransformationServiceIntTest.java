package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsException;

import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class PublishingStateTransformationServiceIntTest {

    @Mock
    CompanyRepository companyRepository;
    @Mock
    DataCollectionRepository dataCollectionRepository;
    @Mock
    DataBundleRepository dataBundleRepository;
    @Mock
    DataInputRepository dataInputRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CompanyStatusRepository companyStatusRepository;
    @Mock
    CompanyDataCollectionRepository companyDataCollectionRepository;
    @Mock
    CompanyDataInputRepository companyDataInputRepository;
    @Mock
    InputTypeRepository inputTypeRepository;
    @Mock
    CompanyDataBundleRepository companyDataBundleRepository;
    @Mock
    PublishingStatusRepository publishingStatusRepository;
    @Mock
    DataBundleDTO dataBundleDTO;
    @Mock
    User user;
    @Mock
    CompanyStatus companyStatus;
    @Mock
    DataBundle dataBundle;
    @Mock
    DataInputDTO dataInputDTO;
    @Mock
    DataInput dataInput;
    @Mock
    InputType inputType;


    @InjectMocks
    PublishingStateTransformationService service;

    /**
     * Set up.
     */
    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new PublishingStateTransformationService(companyRepository, dataCollectionRepository,
            userRepository,
            companyStatusRepository, dataBundleRepository, companyDataBundleRepository,
            companyDataCollectionRepository, companyDataInputRepository, inputTypeRepository,
            dataInputRepository, publishingStatusRepository);
    }

    @Test
    @Transactional
    public void shouldPublishBundleService() throws URISyntaxException,DcsException {
        when(companyRepository.findAll()).thenReturn(getListofCompanies());
        when(dataCollectionRepository.findOne(anyLong())).thenReturn(getDataCollection());
        when(publishingStatusRepository.findOneByStatus(anyString())).thenReturn(getPublishedStatusOptional());
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(companyStatusRepository.findOne(anyLong())).thenReturn(companyStatus);
        when(dataBundleDTO.getId()).thenReturn(new Long(1));
        when(dataBundleDTO.getOwnerId()).thenReturn(new Long(1));
        when(dataBundleDTO.getReviewerId()).thenReturn(new Long(1));
        when(dataBundleDTO.getName()).thenReturn("DB");
        when(dataBundleRepository.findOne(anyLong())).thenReturn(dataBundle);
        when(dataInputRepository.findByDataBundle(anyLong())).thenReturn(getDataInputs());
        service.publishDataBundleStatus(dataBundleDTO);
        verify(dataCollectionRepository, times(1)).save(any(DataCollection.class));
        verify(companyDataCollectionRepository, times(1)).save(any(CompanyDataCollection.class));
        verify(companyDataBundleRepository, times(1)).save(any(CompanyDataBundle.class));
        verify(companyDataInputRepository, times(1)).save(any(CompanyDataInput.class));
        verify(dataInputRepository, times(1)).save(any(DataInput.class));

    }

    @Test
    @Transactional
    public void shouldPublishInputService() throws URISyntaxException,DcsException {
        when(dataInputDTO.getDataBundleId()).thenReturn(new Long(1));
        when(dataInputDTO.getId()).thenReturn(new Long(1));
        when(dataBundleRepository.findOne(anyLong())).thenReturn(getDataBundles());
        when(companyRepository.findAll()).thenReturn(getListofCompanies());
        when(dataCollectionRepository.findOne(anyLong())).thenReturn(getDataCollection());
        when(dataInputRepository.findOne(anyLong())).thenReturn(getDataInput());
        when(companyDataBundleRepository.findByCompanyAndBundle(anyLong())).thenReturn(getCompanyBundles());
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(inputTypeRepository.findOne(anyLong())).thenReturn(inputType);
        when(companyStatusRepository.findOne(anyLong())).thenReturn(companyStatus);
        service.publishDataInputStatus(dataInputDTO);
        verify(companyDataInputRepository, times(1)).save(any(CompanyDataInput.class));
    }

    List<Company> getListofCompanies() {
        List<Company> companyList = new ArrayList<Company>();
        Company company = new Company();
        company.setId(new Long(1));
        company.setFountainId(new Long((1)));
        company.setName("Test");
        companyList.add(company);
        return companyList;
    }

    DataCollection getDataCollection() {
        DataCollection dataCollection = new DataCollection();
        dataCollection.setId(new Long(1));
        dataCollection.setName("dc");
        dataCollection.setPublishingStatus(getPublishedStatus());
        return dataCollection;
    }

    DataBundle getDataBundles() {
        DataBundle dataBundle = new DataBundle();
        dataBundle.setId(new Long(1));
        dataBundle.setName("db");
        dataBundle.setStatus(getPublishedStatus());
        dataBundle.setDataCollection(getDataCollection());
        return dataBundle;
    }

    DataInput getDataInput() {
        DataInput dataInput = new DataInput();
        dataInput.setId(new Long(1));
        dataInput.setName("di");
        dataInput.setDescription("di");
        dataInput.setStatus(getPublishedStatus());
        dataInput.setOwner(getUser());
        dataInput.setReviewer(getUser());
        return dataInput;
    }

    PublishingStatus getPublishedStatus() {
        PublishingStatus publishingStatus = new PublishingStatus();
        publishingStatus.setStatus("PUBLISHED");
        publishingStatus.setId(new Long(4));
        return publishingStatus;
    }

    User getUser() {
        User user = new User();
        user.setId(new Long(1));
        user.setFirstName("admin");
        user.setLastName("admin");
        return user;
    }

    Company getCompany() {
        Company company = new Company();
        company.setId(new Long(1));
        company.setFountainId(new Long((1)));
        company.setName("Test");
        return company;
    }

    Optional<PublishingStatus> getPublishedStatusOptional() {
        PublishingStatus publishingStatus = new PublishingStatus();
        publishingStatus.setStatus("PUBLISHED");
        publishingStatus.setId(new Long(4));
        return (Optional.of(publishingStatus));
    }


    List<DataInput> getDataInputs() {
        List<DataInput> dataInputList = new ArrayList<DataInput>();
        DataInput dataInput = new DataInput();
        dataInput.setId(new Long(1));
        dataInput.setName("di");
        dataInput.setDescription("di");
        dataInput.setStatus(getPublishedStatus());
        dataInputList.add(dataInput);
        return dataInputList;
    }

    List<CompanyDataBundle> getCompanyBundles() {
        List<CompanyDataBundle> companyDataBundles = new ArrayList<CompanyDataBundle>();
        CompanyDataBundle companyDataBundle = new CompanyDataBundle();
        companyDataBundle.setId(new Long(1));
        companyDataBundle.setName("Test");
        companyDataBundle.setCompany(getCompany());
        companyDataBundles.add(companyDataBundle);
        return companyDataBundles;
    }
}
