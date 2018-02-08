package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.CompanyDataBundleRepository;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataBundleMapper;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompanyDataBundleServiceIntTest {

    @Mock
    CompanyDataBundleRepository companyDataBundleRepository;

    @Mock
    CompanyDataInputRepository companyDataInputRepository;

    @Mock
    Company company;

    @Mock
    CompanyStatus status;

    @Mock
    CompanyDataInput companyDataInput;

    @Mock
    User user;

    @Mock
    CompanyDataInput dataInput;

    @Mock
    CompanyDataBundleDTO companyDataBundleDTO;

    @Mock
    CompanyDataBundleMapper companyDataBundleMapper;

    @InjectMocks
    CompanyDataBundleService service;

    /**
     * Set up.
     */
    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CompanyDataBundleService(companyDataBundleRepository, companyDataBundleMapper, companyDataInputRepository);
    }

    @Test
    @Transactional
    public void shouldRetrieveDataCollectionById() {
        when(companyDataBundleRepository.findOne(anyLong())).thenReturn(getCompanyDataBundle());
        when(companyDataInputRepository.findByCompanyDataBundle(anyLong())).thenReturn(getCompanyDataInputList());
        service.findOne(anyLong());
        verify(companyDataBundleMapper, times(1)).toDto(any(CompanyDataBundle.class));
    }

    @Test
    @Transactional
    public void shouldSaveDataCollection() {
        when(companyDataBundleMapper.toEntity(any(CompanyDataBundleDTO.class))).thenReturn(getCompanyDataBundle());
        when(companyDataBundleRepository.save(any(CompanyDataBundle.class))).thenReturn(getCompanyDataBundle());
        service.save(companyDataBundleDTO);
        verify(companyDataBundleMapper, times(1)).toDto(any(CompanyDataBundle.class));
    }

    @Test
    @Transactional
    public void shouldDeleteCompanyDataBundleById() {
        service.delete(anyLong());
        verify(companyDataBundleRepository, times(1)).delete(anyLong());
    }

    public CompanyDataBundle getCompanyDataBundle() {
        CompanyDataBundle dataBundle = new CompanyDataBundle();
        dataBundle.setCompanyDataInputs(getDataInputs());
        dataBundle.setCompany(company);
        dataBundle.setCompanyDataBundleOrderIndex(new Long(1));
        dataBundle.setOrderIndex(new Long(1));
        dataBundle.setStatus(status);
        dataBundle.setName("Test");
        dataBundle.setCompanyReviewer(user);
        dataBundle.setCompanyOwner(user);
        return dataBundle;
    }


    public List<CompanyDataInput> getCompanyDataInputList() {
        List<CompanyDataInput> newList = new ArrayList<CompanyDataInput>();
        newList.add(dataInput);
        return newList;
    }

    private CompanyDataInput[] getDataInputs() {
        CompanyDataInput[] array = {companyDataInput};
        return array;
    }
}
