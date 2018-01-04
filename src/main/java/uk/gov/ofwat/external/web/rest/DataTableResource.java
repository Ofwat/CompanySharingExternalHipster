package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.dto.data.TableDto;
import uk.gov.ofwat.external.service.ExcelReaderService;
import uk.gov.ofwat.external.service.mapper.DCSTableMapper;

@RestController
@RequestMapping("/api")
public class DataTableResource {

    private final Logger log = LoggerFactory.getLogger(DataTableResource.class);
    private static final String ENTITY_NAME = "dataInput";
    private final ExcelReaderService excelReaderService;
    private final DCSTableMapper dcsTableMapper;

    public DataTableResource(ExcelReaderService excelReaderService, DCSTableMapper dcsTableMapper) {
        this.excelReaderService = excelReaderService;
        this.dcsTableMapper = dcsTableMapper;
    }

    /**
     * GET  /file-data : get the F_out JSON.
     *
     * @param identifier
     * @return the ResponseEntity with status 200 (OK) and the F_out JSON in body
     */
    @GetMapping("/data-table")
    @Timed
    public ResponseEntity<TableDto> getDataTable() {
        log.debug("REST request to get a page of DataInputs");
        TableDto tableDto = excelReaderService.readFOut();
        DCSTable dcsTable = dcsTableMapper.toEntity(tableDto);
//        dcsTable.setCompany(getCompany());

        HttpHeaders headers = new HttpHeaders();
        headers.add("id", "" + dcsTable.getFountainReportId()); // reportId
        headers.add("companyId", "" + dcsTable.getCompany().getFountainId());
        headers.add("auditComment", "Dataload from DCS");
        headers.add("runId", "" + dcsTable.getFountainRunId());
        headers.add("excelMongoDocId", "unknown");
        return new ResponseEntity<TableDto>(excelReaderService.readFOut(), headers, HttpStatus.OK);
    }



}
