package uk.gov.ofwat.external.service;

import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.service.serviceException.RemoteStorageException;
import static org.assertj.core.api.Assertions.*;
import java.io.File;
import java.io.IOError;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class RemoteStorageServiceIntegrationTest
{
    @Autowired
    RemoteStorageService remoteStorageService;

    public final String CONTAINER_NAME = "test_container";

    File testFout1;

    File testFilesDirectory = new File("src/test/resources/dataUpload/");

    @Test
    public void shouldAddAFile(){
        given:
        testFout1 = new File(testFilesDirectory.getAbsoluteFile() + "/test_fout_sheet_1.xlsx");
        when:
        try {
            remoteStorageService.storeFile(testFout1);
        }catch(RemoteStorageException remoteStorageexception){

        }
        File returnedFile = remoteStorageService.retrieveFile(testFout1.getName());
        then:
        assertThat(returnedFile).isNotNull();
        assertThat(checkFileEquality(returnedFile, testFout1)).isTrue();
    }

    private Boolean checkFileEquality(File file1, File file2){
        Boolean matches = false;
        try {
            matches = Files.equal(file1, file2);
        }catch (IOException e){
            matches = false;
        }
        return matches;
    }

    @Test
    public void shouldGetAFile(){
        given:
        testFout1= new File(testFilesDirectory.getAbsoluteFile() + "/test_fout_sheet_1.xlsx");
        remoteStorageService.storeFile(testFout1);
        File returnedFile;
        when:
        returnedFile = remoteStorageService.retrieveFile(testFout1.getName());
        then:
        assertThat(returnedFile).isNotNull();
        assertThat(checkFileEquality(returnedFile, testFout1)).isTrue();
    }

}
