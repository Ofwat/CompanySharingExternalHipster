package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.service.DataInputService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * REST controller for managing DataBundle.
 */
@RestController
@RequestMapping("/api")
public class DataDownloadResource {

    private final Logger log = LoggerFactory.getLogger(DataDownloadResource.class);
    private static final String directoryName = "C:\\Files\\";
    private final DataInputService dataInputService;

    public DataDownloadResource(DataInputService dataInputService) {
        this.dataInputService = dataInputService;
    }

    @GetMapping("/data-download/{id}")
    @Timed
    public ResponseEntity getCompanyDataInput(@PathVariable Long id) throws Exception {
        DataInput dataInput = dataInputService.findByDataBundle(id);
        String fileName = dataInput.getFileName().trim();
        ByteArrayResource resource = null;
        File fileUpload = null;
        if (!fileName.equals(null) && !fileName.isEmpty()) {
            Path theDestination = Paths.get(directoryName);
            File dir = new File(theDestination.toString());
            File[] filex = dir.listFiles((d, name) -> name.startsWith(fileName));
            List<File> fileList = Arrays.asList(filex).stream()
                .sorted(Collections.reverseOrder()).collect(Collectors.toList());
            Path filePath = Paths.get(fileList.get(0).toString());
            fileUpload = new File(filePath.toString());
            if (Files.exists(filePath)) {
                try {
                    resource = new ByteArrayResource(Files.readAllBytes(filePath));
                } catch (IOException e) {
                    log.error("there was an error getting the file bytes ", e);
                }

            }
        }
        return ResponseEntity.ok()
            .contentLength(fileUpload.length())
            .header("Content-Disposition", "attachment; filename=" + fileName)
            .body(resource);
    }

}
