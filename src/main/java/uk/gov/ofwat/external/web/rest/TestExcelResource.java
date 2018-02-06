package uk.gov.ofwat.external.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/excel")
public class TestExcelResource {

    Logger logger = LoggerFactory.getLogger(TestExcelResource.class);

    @GetMapping("/download")
    public ResponseEntity getExcel(){
        File file2Upload = new File("c:/Files/test_fout_sheet_1.xlsx");

        Path path = Paths.get(file2Upload.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            logger.error("there was an error getting the file bytes ", e);
        }

        return ResponseEntity.ok()
            .contentLength(file2Upload.length())
            .header("Content-Disposition","attachment; filename=" + file2Upload.getName() )
            .body(resource);
    }

}
