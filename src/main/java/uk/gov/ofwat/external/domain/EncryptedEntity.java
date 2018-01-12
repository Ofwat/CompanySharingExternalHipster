package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@TypeDef(
    name = "encryptedStr",
    defaultForType = EncryptedStringType.class,
    typeClass = EncryptedStringType.class,
    parameters = {
        @Parameter(name = "algorithm", value = "PBEWithMD5AndDES"),
        @Parameter(name = "password", value = "s56k3N5xh782")
    }
)
public class EncryptedEntity {

    @Id
    private long id;

    private String notEncryptedProperty;

    @Type(type = "encryptedStr")
    private String encryptedProperty;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotEncryptedProperty() {
        return notEncryptedProperty;
    }

    public void setNotEncryptedProperty(String notEncryptedProperty) {
        this.notEncryptedProperty = notEncryptedProperty;
    }

    public String getEncryptedProperty() {
        return encryptedProperty;
    }

    public void setEncryptedProperty(String encryptedProperty) {
        this.encryptedProperty = encryptedProperty;
    }
}
