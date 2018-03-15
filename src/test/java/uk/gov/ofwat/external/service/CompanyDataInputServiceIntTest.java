package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.CompanyDataBundleRepository;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataInputDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataInputMapper;

import javax.transaction.Transactional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class CompanyDataInputServiceIntTest {


    @Mock
    CompanyDataInputMapper companyDataInputMapper;


    @Mock
    CompanyDataInputRepository companyDataInputRepository;

    @Mock
    Company company;

    @Mock
    CompanyStatus status;

    @Mock
    User user;

    @Mock
    CompanyDataBundle companyDataBundle;

    @Mock
    CompanyDataInputDTO companyDataInputDTO;

    @Mock
    InputType input;

    @InjectMocks
    CompanyDataInputService service;

    /**
     * Set up.
     */
    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CompanyDataInputService(companyDataInputRepository, companyDataInputMapper);
    }

    @Test
    @Transactional
    public void shouldRetrieveCompanyDataInputById() {
        when(companyDataInputRepository.findOne(anyLong())).thenReturn(getCompanyDataInput());
        service.findOne(anyLong());
        verify(companyDataInputMapper, times(1)).toDto(any(CompanyDataInput.class));
    }

    @Test
    @Transactional
    public void shouldSaveCompanyDataInput() {
        when(companyDataInputMapper.toEntity(any(CompanyDataInputDTO.class))).thenReturn(getCompanyDataInput());
        when(companyDataInputRepository.save(any(CompanyDataInput.class))).thenReturn(getCompanyDataInput());
        service.save(companyDataInputDTO);
        verify(companyDataInputMapper, times(1)).toDto(any(CompanyDataInput.class));
    }

    @Test
    @Transactional
    public void shouldDeleteCompanyDataInputById() {
        service.delete(anyLong());
        verify(companyDataInputRepository, times(1)).delete(anyLong());
    }

    public CompanyDataInput getCompanyDataInput() {
        CompanyDataInput dataInput = new CompanyDataInput();
        dataInput.setCompanyDataInputOrderIndex(new Long(1));
        dataInput.setCompany(company);
        dataInput.setCompanyDataBundle(companyDataBundle);
        dataInput.setStatus(status);
        dataInput.setOrderIndex(new Long(1));
        dataInput.setInputType(input);
        dataInput.setName("Test");
        dataInput.setCompanyOwner(user);
        dataInput.setCompanyReviewer(user);
        return dataInput;
    }

}
