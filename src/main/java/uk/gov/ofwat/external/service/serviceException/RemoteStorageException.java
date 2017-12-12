package uk.gov.ofwat.external.service.serviceException;

public class RemoteStorageException extends RuntimeException {

    public RemoteStorageException(String reason){
        super(reason);
    }

    public RemoteStorageException(){
        super("Couldn't create the remote file!");
    }

}
