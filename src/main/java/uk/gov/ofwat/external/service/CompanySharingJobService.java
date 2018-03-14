package uk.gov.ofwat.external.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.TableMetadata;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.dto.data.TableDto;
import uk.gov.ofwat.external.service.mapper.DCSTableMapper;
import uk.gov.ofwat.jobber.domain.jobs.Job;
import uk.gov.ofwat.jobber.domain.constants.JobTargetPlatformConstants;
import uk.gov.ofwat.jobber.domain.constants.JobTypeConstants;
import uk.gov.ofwat.jobber.service.JobInformation;
import uk.gov.ofwat.jobber.service.JobService;

@Service
@Transactional
public class CompanySharingJobService {

    private final ExcelReaderService excelReaderService;

    private final JobService jobService;

    private final DCSTableMapper dcsTableMapper;

    public CompanySharingJobService(ExcelReaderService excelReaderService, JobService jobService, DCSTableMapper dcsTableMapper){
        this.excelReaderService = excelReaderService;
        this.jobService = jobService;
        this.dcsTableMapper = dcsTableMapper;
    }

    public Job processUpload(String pathToStoredFile, TableMetadata tableMetadata){
        TableDto tableDto = excelReaderService.readFOut(pathToStoredFile);
        DCSTable dcsTable = dcsTableMapper.toEntity(tableDto);
        String jobData = convertTableDtoToJson(tableDto);
        JobInformation jobInformation = createDataJobInformation(jobData, tableMetadata);
        Job job = jobService.createJob(jobInformation);
        return job;
    }

    private String convertTableDtoToJson(TableDto tableDto){
        Gson gson = new Gson();
        String json = gson.toJson(tableDto);
        return json;
    }

    private JobInformation createDataJobInformation(String jobData, TableMetadata tableMetadata){
        JobInformation jobInformation = new JobInformation.Builder(JobTargetPlatformConstants.FOUNTAIN)
            .type(JobTypeConstants.DATA_JOB)
            .data(jobData)
            .originator(JobTargetPlatformConstants.DCS)
            .addMetaData("fountainReportId", tableMetadata.getFountainReportId().toString())
            .addMetaData("companyId", tableMetadata.getCompanyId().toString())
            .addMetaData("auditComment", tableMetadata.getAuditComment())
            .addMetaData("runId", tableMetadata.getRunId().toString())
            .addMetaData("excelDocMongoId", tableMetadata.getExcelDocMongoId())
            .build();
        return jobInformation;
    }
}
