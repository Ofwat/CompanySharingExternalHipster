package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.DataInputService;
import uk.gov.ofwat.external.service.dto.DataDownloadDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private static final String ENTITY_NAME = "dataDownloadDTO";
    private static final String directoryName = "C:\\Files\\";
    Charset charset = StandardCharsets.UTF_8;
    private final DataInputService dataInputService;

    public DataDownloadResource(DataInputService dataInputService) {
        this.dataInputService = dataInputService;
    }
/*
    @GetMapping("/data-download")
    @Timed
    public ResponseEntity<DataDownloadDTO> getAllFiles(@RequestParam("filename") String filename) throws IOException, URISyntaxException {
        log.debug("REST request to get a page of DataBundles");
        DataDownloadDTO dataDownloadDTO = new DataDownloadDTO();
        dataDownloadDTO.setFileContent("");
        dataDownloadDTO.setFileName("");
        StringBuilder temp = new StringBuilder("");

        if (!filename.equals(null) && !filename.isEmpty()) {
            Path theDestination = Paths.get(directoryName);
            File dir = new File(theDestination.toString());
            File[] filex = dir.listFiles((d, name) -> name.startsWith(filename));
            List<File> fileList = Arrays.asList(filex).stream()
                .sorted(Collections.reverseOrder()).collect(Collectors.toList());
            Path filePath = Paths.get(fileList.get(0).toString());
            if (Files.exists(filePath)) {
                try (BufferedReader fileReader = Files.newBufferedReader(filePath, charset);) {
                    int glyph;
                    while ((glyph = fileReader.read()) != -1) {
                        temp.append((char) glyph);
                    }

                }
            }
            dataDownloadDTO.setFileContent(temp.toString());
            dataDownloadDTO.setFileName(filename.trim());
        } else{
            List<String> files = getListofFiles();
            dataDownloadDTO.setFileNames(files);
        }

        return ResponseEntity.created(new URI("/api/data-download/"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "dataDownloadDTO"))
            .body(dataDownloadDTO);
    }*/

    private List<String> getListofFiles() {
        List<String> files = new ArrayList<String>();
        File dir = new File(directoryName);
        File[] fileInDir = dir.listFiles((d, name) -> !name.equals(""));
        List<File> fileList = Arrays.asList(fileInDir).stream()
            .sorted().collect(Collectors.toList());
        for (
            File file : fileList) {
            files.add(file.getName());
        }
        return files;
    }


    @GetMapping("/data-download/{id}")
    @Timed
    public ResponseEntity<DataDownloadDTO> getCompanyDataInput(@PathVariable Long id) throws URISyntaxException, IOException {
        DataInput dataInput = dataInputService.findByDataBundle(id);
        DataDownloadDTO dataDownloadDTO = new DataDownloadDTO();
        String fileName=dataInput.getFileName().trim();
        dataDownloadDTO.setFileContent("");
        dataDownloadDTO.setFileName(fileName);
        StringBuilder temp = new StringBuilder("");

        if (!fileName.equals(null) && !fileName.isEmpty()) {
            Path theDestination = Paths.get(directoryName);
            File dir = new File(theDestination.toString());
            File[] filex = dir.listFiles((d, name) -> name.startsWith(fileName));
            List<File> fileList = Arrays.asList(filex).stream()
                .sorted(Collections.reverseOrder()).collect(Collectors.toList());
            Path filePath = Paths.get(fileList.get(0).toString());
            if (Files.exists(filePath)) {
                try (BufferedReader fileReader = Files.newBufferedReader(filePath, charset);) {
                    int glyph;
                    while ((glyph = fileReader.read()) != -1) {
                        temp.append((char) glyph);
                    }

                }
            }
            dataDownloadDTO.setFileContent(temp.toString());
            dataDownloadDTO.setFileName(fileName.trim());
        }
        return ResponseEntity.created(new URI("/api/data-download/"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "dataDownloadDTO"))
            .body(dataDownloadDTO);
    }


}
