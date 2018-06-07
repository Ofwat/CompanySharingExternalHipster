package uk.gov.ofwat.external.web.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.DataJobService;
import uk.gov.ofwat.external.service.dto.DataJobDto;
import uk.gov.ofwat.external.service.mapper.DataJobMapper;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataJob.
 */
@RestController
@RequestMapping("/data-api")
public class DataJobResource {

    Logger log = LoggerFactory.getLogger(DataJobResource.class);

    private final DataJobService dataJobService;
    private final DataJobMapper dataJobMapper;

    public DataJobResource(DataJobService dataJobService, DataJobMapper dataJobMapper){
        this.dataJobService = dataJobService;
        this.dataJobMapper = dataJobMapper;
    }

//    @GetMapping("/new-data-jobs")
//    public ResponseEntity<List<DataJob>> getAllDataJobs() {
//        log.info("REST request to get all jobs");
//        List<DataJob> dataJobs = dataJobService.getNewJobs();
//        return new ResponseEntity<List<DataJob>>(dataJobs, HttpStatus.OK);
//    }

    @GetMapping("/data-job")
    public ResponseEntity<DataJobDto> getNextDataJob() {
        log.info("REST request to get the next job");
        Optional<DataJob> schrodingersJob = dataJobService.getNextDataJob();
        if(!schrodingersJob.isPresent()){
            return ResponseEntity.notFound().build();
        }

        DataJob dataJob = schrodingersJob.get();
        DataJobDto dataJobDto = dataJobMapper.toDto(dataJob);

        return new ResponseEntity<DataJobDto>(dataJobDto, HttpStatus.OK);
    }

    @PostMapping("/data-job/{uuid}")
    public ResponseEntity updateJob(@PathVariable(value="uuid")final String uuid, @RequestBody DataJobDto dataJobDto) {
        log.info("REST request to update a DataJob");
        if (!dataJobDto.getUuid().equals(uuid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Optional<DataJob> schrodingersJob = dataJobService.getDataJobByUuid(uuid);
        if(!schrodingersJob.isPresent()){
            return ResponseEntity.notFound().build();
        }

        DataJob dataJob = null;
        try {
            dataJob = dataJobMapper.toEntity(dataJobDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataJobService.updateDataJob(dataJob);
        return new ResponseEntity(HttpStatus.OK);
    }

}
