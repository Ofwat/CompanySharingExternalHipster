package uk.gov.ofwat.external.service;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.ofwat.external.config.SharePointOAuthClient;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.domain.TableMetadata;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.jobber.domain.jobs.Job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class DataUploadService {

    private final Logger log = LoggerFactory.getLogger(DataUploadService.class);
    private final CompanyDataInputService companyDataInputService;
    private final DataFileRepository dataFileRepository;
    private final SharePointOAuthClient sharePointOAuthClient;
    private final CompanySharingJobService companySharingJobService;
    private volatile boolean running = false;

    @Value("${env}")
    private String env;

    public DataUploadService(CompanyDataInputService companyDataInputService, DataFileRepository dataFileRepository, SharePointOAuthClient sharePointOAuthClient, CompanySharingJobService companySharingJobService) {
        this.companyDataInputService = companyDataInputService;
        this.dataFileRepository = dataFileRepository;
        this.sharePointOAuthClient = sharePointOAuthClient;
        this.companySharingJobService = companySharingJobService;
    }

    public void uploadFile(String companyInputId, MultipartFile file) throws IOException, JSONException, InterruptedException {
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

    String getUniqueFileName(String companyName, String reportId, String runId, String name) {
        String temp = String.valueOf(Instant.now());
        return temp.substring(0, temp.indexOf(":")) + "_" + companyName + "_" + reportId + "_" + runId + "_" + env + name.substring(name.indexOf('.'), name.length());
    }
}

