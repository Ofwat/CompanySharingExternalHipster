package uk.gov.ofwat.external.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.ofwat.external.repository.EncryptedEntityRepository;

import javax.annotation.Resource;

@Resource
public class EncryptedEntityResource {

    EncryptedEntityRepository encryptedEntityRepository;

    public EncryptedEntityResource(EncryptedEntityRepository encryptedEntityRepository){
        this.encryptedEntityRepository = encryptedEntityRepository;
    }

    @GetMapping("/encrypt/add")
    public void addEncryptedRecord(){

    }

}
