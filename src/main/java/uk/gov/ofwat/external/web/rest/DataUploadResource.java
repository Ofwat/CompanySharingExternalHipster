package uk.gov.ofwat.external.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.ofwat.external.domain.DataFile;
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

    public DataUploadResource(DataFileRepository dataFileRepository,
                              CompanyDataInputRepository companyDataInputRepository,
                              CompanySharingJobService companySharingJobService){
        this.dataFileRepository=dataFileRepository;
        this.companyDataInputRepository=companyDataInputRepository;
        this.companySharingJobService = companySharingJobService;
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
                                                   @RequestParam(value = "companyInputId", required = false) String companyInputId) throws IOException {
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
            DataFile dataFIle = new DataFile();
            dataFIle.setCompanyDataInput(companyDataInputRepository.findOne(Long.parseLong(companyInputId)));
            dataFIle.setLocation("C:\\CompanyFiles\\");
            dataFIle.setName(file.getOriginalFilename());
            dataFileRepository.save(dataFIle);

        }


        //return dummy obj might change it to empty string and discussion
        DataInputDTO result = new DataInputDTO();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(result);
    }
    @PostMapping(value = "/data-upload")
    public ResponseEntity<DataInputDTO> uploadFile(@RequestParam(value = "uploadFiles", required = false) MultipartFile[] files) throws IOException {
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

            //Create the Job.
            Job job = companySharingJobService.processUpload(theDestination1.toString());
            log.info(job.getUuid().toString());

        }

        //return dummy obj might change it to empty string and discussion
        DataInputDTO result = new DataInputDTO();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(result);
    }
}
