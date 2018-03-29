package uk.gov.ofwat.external.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.domain.DataJobStatus;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.repository.DataJobRepository;

import java.util.List;

@Service
public class CompanyDataInputUpdateService {


    @Autowired
    DataJobRepository dataJobRepository;

    @Autowired
    CompanyDataInputRepository companyDataInputRepository;

    public CompanyDataInputUpdateService(DataJobRepository dataJobRepository,CompanyDataInputRepository companyDataInputRepository) {
        this.dataJobRepository = dataJobRepository;
        this.companyDataInputRepository = companyDataInputRepository;
    }


    @Scheduled(cron = "0 0 0 25 12 ?")
    public void updateCompanyDataInputStatus() {
        List<DataJob> dataJobsList =dataJobRepository.findByUpdatedOrderByCompanyDataInputId(true);
        dataJobsList.stream().forEach( dataJob ->{
                CompanyDataInput companyDataInput =  companyDataInputRepository.findOne(dataJob.getCompanyDataInputId());
                CompanyStatus cs = new CompanyStatus();
                cs.setId(new Long(3));
                cs.setStatus("RECEIVED");
                companyDataInput.setStatus(cs);
                companyDataInputRepository.save(companyDataInput);
                dataJob.setJobStatus(DataJobStatus.ACCEPTED.name());
                dataJob.setUpdated(false);
                dataJobRepository.save(dataJob);
        }
        );
    }
}
