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
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.external.service.dto.data.TableDto;
import uk.gov.ofwat.external.service.mapper.DCSTableMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import uk.gov.ofwat.jobber.domain.jobs.Job;

@Service
@Transactional
public class DataUploadService {

    private final Logger log = LoggerFactory.getLogger(DataUploadService.class);
    private final CompanyDataInputService companyDataInputService;
    private final DataFileRepository dataFileRepository;
    private final SharePointOAuthClient sharePointOAuthClient;
    private final DataJobService dataJobService;
    private final ExcelReaderService excelReaderService;
    private final DCSTableMapper dcsTableMapper;
    private volatile boolean running = false;

    @Value("${localUploadCompanyFolder}")
    private String localUploadCompanyFolder;

    @Value("${localUploadOfwatFolder}")
    private String localUploadOfwatFolder;

    @Value("${ofwatFileName}")
    private String ofwatFileName;

    @Value("${env}")
    private String env;

    public DataUploadService(CompanyDataInputService companyDataInputService, DataFileRepository dataFileRepository, SharePointOAuthClient sharePointOAuthClient, DataJobService dataJobService, ExcelReaderService excelReaderService, DCSTableMapper dcsTableMapper) {
        this.companyDataInputService = companyDataInputService;
        this.dataFileRepository = dataFileRepository;
        this.sharePointOAuthClient = sharePointOAuthClient;
        this.dataJobService = dataJobService;
        this.excelReaderService = excelReaderService;
        this.dcsTableMapper = dcsTableMapper;
    }

    public void uploadFile(MultipartFile file) throws IOException, InterruptedException {
        log.debug("Uploaded File Names :" + file.getOriginalFilename());
        Path theDestination1 = Paths.get("C:\\Files\\" + ofwatFileName);
        File newFile = new File(theDestination1.toString());
        try {
            file.transferTo(newFile);
        }catch(Exception e){
            //do nothing if file exists
        }
        //sharePointOAuthClient.uploadFileToSharePoint(newFile);

    }
    public void uploadCompanyFile(String companyInputId, MultipartFile file) throws IOException, JSONException, InterruptedException {
        log.debug("Uploaded File Names :" + file.getOriginalFilename());

        DataFile dataFile = new DataFile();
        CompanyDataInput companyDataInput = companyDataInputService.findCompanyDataInput(Long.parseLong(companyInputId));
        String newFileName = getUniqueFileName(companyDataInput.getCompany().getName().trim(), String.valueOf(companyDataInput.getDataInput().getReportId()), "0", file.getOriginalFilename().trim()).trim();
        newFileName = newFileName.replaceAll(" ", "%20");
        Path theDestination1 = Paths.get("C:\\CompanyFiles\\" + newFileName);
        File newFile = new File(theDestination1.toString());
        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            //do nothing if file exists
        }

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
                    //Create the Job.
                    TableDto tableDto = excelReaderService.readFOut(theDestination1.toString(), companyDataInput.getDataInput().getReportId());
                    DCSTable dcsTable = dcsTableMapper.toEntity(tableDto);
                    DataJob dataJob = dataJobService.createDataJob(companyDataInput, tableDto);
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

