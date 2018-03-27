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
import uk.gov.ofwat.external.service.DataUploadService;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsServerMessage;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

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
    private final SharePointOAuthClient sharePointOAuthClient;
    private final DataUploadService dataUploadService;

    @Value("${localUploadCompanyFolder}")
    private String localUploadCompanyFolder;

    @Value("${localUploadOfwatFolder}")
    private String localUploadOfwatFolder;

    @Value("${ofwatFileName}")
    private String ofwatFileName;

    public DataUploadResource(SharePointOAuthClient sharePointOAuthClient,
                              DataUploadService dataUploadService) {
        this.sharePointOAuthClient = sharePointOAuthClient;
        this.dataUploadService = dataUploadService;
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
            dataUploadService.uploadFile(companyInputId, file);
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

}
