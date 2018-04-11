package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.CompanyDataBundleService;
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.DataJobService;
import uk.gov.ofwat.external.service.dto.RejectionResourceDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsException;
import uk.gov.ofwat.external.web.rest.errors.DcsServerMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/rejection")
public class RejectionResource {

    private final Logger log = LoggerFactory.getLogger(RejectionResource.class);

    @Autowired
    DataJobService dataJobService;

    @Autowired
    CompanyDataInputService companyDataInputService;


    @Autowired
    CompanyDataBundleService companyDataBundleService;

    @GetMapping("/allrejections")
    @Timed
    public List<RejectionResourceDTO> getList() throws DcsException {
        List<RejectionResourceDTO> rejectionResourceDTOS = new ArrayList<>();
        try {
            List<DataJob> dataJobsList = dataJobService.findByUpdatedOrderByCompanyDataInputId(true);
            dataJobsList.stream().forEach(dataJob -> {
                CompanyDataInput companyDataInput = companyDataInputService.findCompanyDataInput(dataJob.getCompanyDataInputId());
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
        } catch (Exception e) {
            throw new DcsException("Company Data Input not found");
        }
        return rejectionResourceDTOS;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) throws JsonProcessingException {
        log.debug("handling Exception", exception);
        return buildResponseEntity(new DcsServerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), new Throwable(new Exception("exception"))));
    }

    private ResponseEntity<String> buildResponseEntity(DcsServerMessage dcsServerMessage) throws JsonProcessingException {
        ObjectMapper obm = new ObjectMapper();
        String x = obm.writeValueAsString(dcsServerMessage);
        return new ResponseEntity<>(x, dcsServerMessage.getStatus());
    }
}
