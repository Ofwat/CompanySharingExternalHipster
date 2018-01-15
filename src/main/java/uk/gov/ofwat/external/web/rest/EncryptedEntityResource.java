package uk.gov.ofwat.external.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ofwat.external.domain.EncryptedEntity;
import uk.gov.ofwat.external.repository.EncryptedEntityRepository;
import uk.gov.ofwat.external.service.DataCollectionService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class EncryptedEntityResource {

    EncryptedEntityRepository encryptedEntityRepository;

    private final Logger log = LoggerFactory.getLogger(EncryptedEntityResource.class);

    public EncryptedEntityResource(EncryptedEntityRepository encryptedEntityRepository){
        this.encryptedEntityRepository = encryptedEntityRepository;
    }

    @GetMapping("/encrypt/add")
    public ResponseEntity addEncryptedRecord(){
        log.info("Adding encrypted entry!");
        EncryptedEntity e = new EncryptedEntity();
        e.setEncryptedProperty("secret");
        e.setNotEncryptedProperty("not secret");
        this.encryptedEntityRepository.save(e);
        return ResponseEntity.ok().build();
    }

}
