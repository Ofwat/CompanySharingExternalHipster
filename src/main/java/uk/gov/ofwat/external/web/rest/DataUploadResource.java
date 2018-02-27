package uk.gov.ofwat.external.web.rest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.ofwat.external.config.SharePointOAuthClient;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.domain.TableMetadata;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.external.service.CompanySharingJobService;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.jobber.domain.jobs.Job;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * REST controller for managing DataUpload.
 */
@RestController
@RequestMapping("/api")
public class DataUploadResource {

    private final Logger log = LoggerFactory.getLogger(DataUploadResource.class);
    private static final String ENTITY_NAME = "dataUpload";

    private final DataFileRepository dataFileRepository;
    private final CompanyDataInputRepository companyDataInputRepository;
    private final CompanySharingJobService companySharingJobService;
    private final SharePointOAuthClient sharePointOAuthClient;

    public DataUploadResource(DataFileRepository dataFileRepository,
                              CompanyDataInputRepository companyDataInputRepository,
                              CompanySharingJobService companySharingJobService,
                              SharePointOAuthClient sharePointOAuthClient){
        this.dataFileRepository=dataFileRepository;
        this.companyDataInputRepository=companyDataInputRepository;
        this.companySharingJobService = companySharingJobService;
        this.sharePointOAuthClient =  sharePointOAuthClient;
    }

    /**
     * POST  /data-inputs : Updates an existing dataInput.
     *
     * @param files or array Multipart[]
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataInputDTO,
     * or with status 400 (Bad Request) if the dataInputDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataInputDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping(value = "/data-upload-company")
    public ResponseEntity<DataInputDTO> uploadFile(@RequestParam(value = "uploadFiles", required = false) MultipartFile[] files,
                                                   @RequestParam(value = "companyInputId", required = false) String companyInputId) throws IOException, JSONException {
        //-- my stuff with formDataObject and uploaded files
        log.debug("REST request to upload Data : {}");
        String directoryName="C:\\CompanyFiles\\";
        File directory = new File(String.valueOf(directoryName));
        if(!directory.exists()) {
            directory.mkdir();
        }
        for(MultipartFile file: files){
            log.debug("Uploaded File Names :"+file.getOriginalFilename());
            Path theDestination1 = Paths.get("C:\\CompanyFiles\\"+file.getOriginalFilename());
            File newFile = new File(theDestination1.toString());
            file.transferTo(newFile);
            DataFile dataFile = new DataFile();
            CompanyDataInput companyDataInput = companyDataInputRepository.findOne(Long.parseLong(companyInputId));
            dataFile.setCompanyDataInput(companyDataInput);
            dataFile.setLocation("C:\\CompanyFiles\\");
            dataFile.setName(file.getOriginalFilename());
            dataFileRepository.save(dataFile);

            sharePointOAuthClient.uploadFileToSharePoint(newFile);

            TableMetadata tableMetadata = new TableMetadata(
                                        companyDataInput.getCompany().getId(),
                                        "Generated by CompanySharingJobService",
                                        new Long(0),
                                        companyDataInput.getDataInput().getReportId(),
                                        "N/A");

            //Create the Job.
            Job job = companySharingJobService.processUpload(theDestination1.toString(), tableMetadata);
            log.info(job.getUuid().toString());


        }


        //return dummy obj might change it to empty string and discussion
        DataInputDTO result = new DataInputDTO();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(result);
    }
    @PostMapping(value = "/data-upload")
    public ResponseEntity<DataInputDTO> uploadFile(@RequestParam(value = "uploadFiles", required = false) MultipartFile[] files) throws IOException, JSONException {
        //-- my stuff with formDataObject and uploaded files
        log.debug("REST request to upload Data : {}");
        String directoryName="C:\\Files\\";
        File directory = new File(String.valueOf(directoryName));
        if(!directory.exists()) {
            directory.mkdir();
        }
        for(MultipartFile file: files){
            log.debug("Uploaded File Names :"+file.getOriginalFilename());
            Path theDestination1 = Paths.get("C:\\Files\\"+file.getOriginalFilename());
            File newFile = new File(theDestination1.toString());
            file.transferTo(newFile);
            sharePointOAuthClient.uploadFileToSharePoint(newFile);
        }

        //return dummy obj might change it to empty string and discussion
        DataInputDTO result = new DataInputDTO();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(result);
    }
}
