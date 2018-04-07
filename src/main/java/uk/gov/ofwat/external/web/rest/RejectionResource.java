package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.CompanyDataBundleService;
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.DataJobService;
import uk.gov.ofwat.external.service.dto.RejectionResourceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/rejection")
public class RejectionResource {

    @Autowired
    DataJobService dataJobService;

    @Autowired
    CompanyDataInputService companyDataInputService;


    @Autowired
    CompanyDataBundleService companyDataBundleService;

    @GetMapping("/allrejections")
    @Timed
    public List<RejectionResourceDTO> getList() {
        List<RejectionResourceDTO> rejectionResourceDTOS = new ArrayList<>();
        List<DataJob> dataJobsList =dataJobService.findByUpdatedOrderByCompanyDataInputId(true);
        dataJobsList.stream().forEach( dataJob ->{
            CompanyDataInput companyDataInput =  companyDataInputService.findCompanyDataInput (dataJob.getCompanyDataInputId());
            CompanyDataBundle companyDataBundle = companyDataInput.getCompanyDataBundle();
            RejectionResourceDTO rejectionResourceDTO = new RejectionResourceDTO();
            rejectionResourceDTO.setId(dataJob.getId());
            rejectionResourceDTO.setCompanyDataBundleName(companyDataBundle.getName());
            rejectionResourceDTO.setCompanyDataInputName(companyDataInput.getName());
            rejectionResourceDTO.setCompanyDeadline(String.valueOf(companyDataBundle.getCompanyDeadline()));
            rejectionResourceDTO.setCompanyName(companyDataBundle.getCompany().getName());
            rejectionResourceDTO.setJobStatus(dataJob.getJobStatus());
            rejectionResourceDTO.setRejectedReason(dataJob.getRejectionCodes().getDescription());
            rejectionResourceDTOS.add(rejectionResourceDTO);
        });
        return  rejectionResourceDTOS;
    }
}
