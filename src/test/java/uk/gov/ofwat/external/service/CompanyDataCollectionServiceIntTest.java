package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.CompanyDataCollectionRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataCollectionMapper;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class CompanyDataCollectionServiceIntTest {

    @InjectMocks
    CompanyDataCollectionService service;

    @Mock
    User user;

    @Mock
    CompanyDataBundle companyDataBundle;

    @Mock
    CompanyDataCollectionRepository companyDataCollectionRepository;

    @Mock
    CompanyStatus status;

    @Mock
    CompanyDataCollectionMapper companyDataCollectionMapper;

    @Mock
    CompanyDataCollectionDTO companyDataCollectionDTO;


    /**
     * Set up.
     */
    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CompanyDataCollectionService(companyDataCollectionRepository, companyDataCollectionMapper);
    }

    @Test
    @Transactional
    public void shouldRetrieveDataCollectionById() {
        when(companyDataCollectionRepository.findOne(anyLong())).thenReturn(getCompanyDataCollection());
        service.findOne(anyLong());
        verify(companyDataCollectionMapper,times(1)).toDto(any(CompanyDataCollection.class));
        assertNotNull(companyDataCollectionDTO);
    }

    @Test
    @Transactional
    public void shouldDeleteDataCollectionById() {
        service.delete(anyLong());
        verify(companyDataCollectionRepository,times(1)).delete(anyLong());
    }


    @Test
    @Transactional
    public void shouldSaveDataCollection() {
        when(companyDataCollectionMapper.toEntity(any(CompanyDataCollectionDTO.class))).thenReturn(getCompanyDataCollection());
        when(companyDataCollectionRepository.save(any(CompanyDataCollection.class))).thenReturn(getCompanyDataCollection());
        service.save(companyDataCollectionDTO);
        verify(companyDataCollectionMapper,times(1)).toDto(any(CompanyDataCollection.class));
    }

    private Company getCompany() {
        Company company = new Company();
        company.setId(new Long(1));
        company.setFountainId(new Long(1));
        return company;
    }

    private CompanyDataBundle[] getDataBundles() {
        CompanyDataBundle[] array = {companyDataBundle};
        return array;
    }

    public CompanyDataCollection getCompanyDataCollection() {
        CompanyDataCollection companyDataCollection = new CompanyDataCollection();
        companyDataCollection.setCompany(getCompany());
        companyDataCollection.setCompanyDataCollectionOrderIndex(new Long(1));
        companyDataCollection.setName("Test");
        companyDataCollection.setCompanyOwner(user);
        companyDataCollection.setCompanyDataBundles(getDataBundles());
        companyDataCollection.setStatus(status);
        companyDataCollection.setCompanyReviewer(user);
        companyDataCollection.setCompanyReviewer(user);
        return companyDataCollection;
    }


}
