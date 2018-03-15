package uk.gov.ofwat.external.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.ofwat.external.config.SharePointOAuthClient;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.domain.TableMetadata;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.CompanySharingJobService;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsServerMessage;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.jobber.domain.jobs.Job;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * REST controller for managing DataUpload.
 */
@RestController
@RequestMapping("/api")
public class DataUploadResource {


    private final Logger log = LoggerFactory.getLogger(DataUploadResource.class);
    private static final String ENTITY_NAME = "dataUpload";
    private volatile boolean running = false;
    private final DataFileRepository dataFileRepository;
    private final CompanyDataInputService companyDataInputService;
    private final CompanySharingJobService companySharingJobService;
    private final SharePointOAuthClient sharePointOAuthClient;

    @Value("${localUploadCompanyFolder}")
    private String localUploadCompanyFolder;

    @Value("${localUploadOfwatFolder}")
    private String localUploadOfwatFolder;

    @Value("${ofwatFileName}")
    private String ofwatFileName;

    @Value("${env}")
    private String env;


    public DataUploadResource(DataFileRepository dataFileRepository,
                              CompanyDataInputService companyDataInputService,
                              CompanySharingJobService companySharingJobService,
                              SharePointOAuthClient sharePointOAuthClient) {
        this.dataFileRepository = dataFileRepository;
        this.companyDataInputService = companyDataInputService;
        this.companySharingJobService = companySharingJobService;
        this.sharePointOAuthClient = sharePointOAuthClient;
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
                                                   @RequestParam(value = "companyInputId", required = false) String companyInputId) throws IOException, JSONException, InterruptedException {
        //-- my stuff with formDataObject and uploaded files
        log.debug("REST request to upload Data : {}");
        File directory = new File(String.valueOf(localUploadCompanyFolder));
        if (!directory.exists()) {
            directory.mkdir();
        }
        for (MultipartFile file : files) {
            // ToDo move this to a file service
            log.debug("Uploaded File Names :" + file.getOriginalFilename());

            DataFile dataFile = new DataFile();
            CompanyDataInput companyDataInput = companyDataInputService.findCompanyDataInput(Long.parseLong(companyInputId));
            String newFileName = getUniqueFileName(companyDataInput.getCompany().getName().trim(), String.valueOf(companyDataInput.getDataInput().getReportId()), "0", file.getOriginalFilename().trim()).trim();
            newFileName = newFileName.replaceAll(" ", "%20");
            Path theDestination1 = Paths.get("C:\\CompanyFiles\\" + newFileName);
            File newFile = new File(theDestination1.toString());
            file.transferTo(newFile);

            dataFile.setCompanyDataInput(companyDataInput);
            dataFile.setLocation("C:\\CompanyFiles\\");
            dataFile.setName(newFileName);
            dataFileRepository.save(dataFile);

            sharePointOAuthClient.uploadFileToSharePoint(newFile);
            Thread current = Thread.currentThread();
            try {
                new Thread(() -> {
                    running = true;
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        TableMetadata tableMetadata = new TableMetadata(
                            companyDataInput.getCompany().getId(),
                            "Generated by CompanySharingJobService",
                            new Long(0),
                            companyDataInput.getDataInput().getReportId(),
                            "N/A",
                            companyInputId);
                        //Create the Job.
                        Job job = companySharingJobService.processUpload(theDestination1.toString(), tableMetadata, companyDataInput);
                        log.info(job.getUuid().toString());
                        current.interrupt();
                    });
                    executorService.shutdown();
                }).start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(String.valueOf(e));
            }
            current.sleep(1000);
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

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        File directory = new File(String.valueOf(localUploadOfwatFolder));
        if (!directory.exists()) {
            directory.mkdir();
        }
        for (MultipartFile file : files) {
            log.debug("Uploaded File Names :" + file.getOriginalFilename());
            Path theDestination1 = Paths.get("C:\\Files\\" + ofwatFileName);
            File newFile = new File(theDestination1.toString());

            file.transferTo(newFile);

            sharePointOAuthClient.uploadFileToSharePoint(newFile);
        }

        //return dummy obj might change it to empty string and discussion
        DataInputDTO result = new DataInputDTO();
        result.setFileName(ofwatFileName);
           return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(result);
    }


    private ResponseEntity<String> buildResponseEntity(DcsServerMessage dcsServerMessage) throws JsonProcessingException {
        ObjectMapper obm = new ObjectMapper();
        String x = obm.writeValueAsString(dcsServerMessage);
        return new ResponseEntity<>(x, dcsServerMessage.getStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) throws JsonProcessingException {
        log.debug("handling Exception", exception);
        return buildResponseEntity(new DcsServerMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed", new Throwable(new Exception("exception"))));
    }

    String getUniqueFileName(String companyName, String reportId, String runId, String name) {
        String temp = String.valueOf(Instant.now());
        return temp.substring(0, temp.indexOf(":")) + "_" + companyName + "_" + reportId + "_" + runId + "_" + env + name.substring(name.indexOf('.'), name.length());
    }

}
