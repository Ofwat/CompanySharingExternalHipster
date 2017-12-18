package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ofwat.external.domain.data.TableDto;
import uk.gov.ofwat.external.service.ExcelReaderService;

@RestController
@RequestMapping("/api")
public class FileDataResource {

    private final Logger log = LoggerFactory.getLogger(FileDataResource.class);
    private static final String ENTITY_NAME = "dataInput";
    private final ExcelReaderService excelReaderService;

    public FileDataResource(ExcelReaderService excelReaderService) {
        this.excelReaderService = excelReaderService;
    }

    /**
     * GET  /file-data : get the F_out JSON.
     *
     * @param file identifier
     * @return the ResponseEntity with status 200 (OK) and the F_out JSON in body
     */
    @GetMapping("/file-data")
    @Timed
    public ResponseEntity<TableDto> getAllDataInputs() {
        log.debug("REST request to get a page of DataInputs");
        //TODO set metadata to pass through to Fountain
//        MultiValueMap<String, String> headers = new MultiValueMap<String, String>();
        return new ResponseEntity<TableDto>(excelReaderService.readFOut(), HttpStatus.OK);
    }



}
