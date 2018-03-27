package uk.gov.ofwat.external.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.service.DataJobService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataJob.
 */
@RestController
@RequestMapping("/data-job")
public class DataJobResource {

    Logger log = LoggerFactory.getLogger(DataJobResource.class);

    private final DataJobService dataJobService;

    public DataJobResource(DataJobService dataJobService){
        this.dataJobService = dataJobService;
    }

    @GetMapping("/new-data-jobs")
    public ResponseEntity<List<DataJob>> getAllDataJobs() {
        log.info("REST request to get all jobs");
        List<DataJob> dataJobs = dataJobService.getNewJobs();
        return new ResponseEntity<List<DataJob>>(dataJobs, HttpStatus.OK);
    }

    @GetMapping("/next-data-job")
    public ResponseEntity<DataJob> getNextDataJob() {
        log.info("REST request to get the next job");
        Optional<DataJob> schrodingersJob = dataJobService.getNextDataJob();
        if(!schrodingersJob.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<DataJob>(schrodingersJob.get(), HttpStatus.OK);
    }

    @PostMapping("/data-job/{uuid}")
    public ResponseEntity updateJob(@PathVariable String uuid, @RequestBody DataJob dataJob) {
        log.info("REST request to update a DataJob");
        if (!dataJob.getUuid().equals(uuid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Optional<DataJob> schrodingersJob = dataJobService.getDataJobByUuid(uuid);
        if(!schrodingersJob.isPresent()){
            return ResponseEntity.notFound().build();
        }

        dataJobService.updateDataJob(dataJob);
        return new ResponseEntity(HttpStatus.OK);
    }

}
