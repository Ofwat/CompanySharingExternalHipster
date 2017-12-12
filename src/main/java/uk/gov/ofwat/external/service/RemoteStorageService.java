package uk.gov.ofwat.external.service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.ofwat.external.service.serviceException.RemoteStorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;

@Service
public class RemoteStorageService {

    public static final String tempFilePath = System.getProperty("java.io.tmpdir");

    public static final String storageConnectionString =
        "DefaultEndpointsProtocol=http;"
            + "AccountName=companysharingtest;"
            + "AccountKey=2MJ7Z2dMGOqP3OtR8SdtCJA93a+GCT6DoMHpGnl2yXK0B0xSxVh1wD09bi9dlYyIYaJCxnq1rCrZWB8/uXVSzw==";

    private final Logger log = LoggerFactory.getLogger(RemoteStorageService.class);

    public File retrieveFile(String name){
        File returnedFile;
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();
            // Container name must be lower case.
            CloudBlobContainer container = serviceClient.getContainerReference("company-sharing-test-container");
            CloudBlockBlob blob = container.getBlockBlobReference(name);
            returnedFile = new File(tempFilePath, name + ".tmp");
            blob.downloadToFile(returnedFile.getAbsolutePath());
        }catch(StorageException storageException){
            log.error(storageException.toString());
            throw new RemoteStorageException();
        }
        catch(FileNotFoundException fileNotFoundException){
            log.error(fileNotFoundException.toString());
            throw new RemoteStorageException();
        }
        catch(InvalidKeyException invalidKeyException){
            log.error(invalidKeyException.toString());
            throw new RemoteStorageException();
        }
        catch(Exception exception){
            log.error(exception.toString());
            throw new RemoteStorageException();
        }
        return returnedFile;
    }

    public String storeFile(File sourceFile) throws RemoteStorageException{
        String ref = "";
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();
            // Container name must be lower case.
            CloudBlobContainer container = serviceClient.getContainerReference("company-sharing-test-container");
            container.createIfNotExists();
            CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());
        }catch(StorageException storageException){
            log.error(storageException.toString());
            throw new RemoteStorageException();
        }
        catch(FileNotFoundException fileNotFoundException){
            log.error(fileNotFoundException.toString());
            throw new RemoteStorageException();
        }
        catch(InvalidKeyException invalidKeyException){
            log.error(invalidKeyException.toString());
            throw new RemoteStorageException();
        }
        catch(Exception exception){
            log.error(exception.toString());
            throw new RemoteStorageException();
        }
        return ref;
    }

}
