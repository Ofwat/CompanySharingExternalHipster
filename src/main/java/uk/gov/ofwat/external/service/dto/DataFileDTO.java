package uk.gov.ofwat.external.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DataFile entity.
 */
public class DataFileDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

    private Long companyDataInputId;

    private String companyDataInputName;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyDataInputId() {
        return companyDataInputId;
    }

    public void setCompanyDataInputId(Long companyDataInputId) {
        this.companyDataInputId = companyDataInputId;
    }

    public String getCompanyDataInputName() {
        return companyDataInputName;
    }

    public void setCompanyDataInputName(String companyDataInputName) {
        this.companyDataInputName = companyDataInputName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataFileDTO)) return false;

        DataFileDTO that = (DataFileDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getLocation() != null ? !getLocation().equals(that.getLocation()) : that.getLocation() != null)
            return false;
        if (getCompanyDataInputId() != null ? !getCompanyDataInputId().equals(that.getCompanyDataInputId()) : that.getCompanyDataInputId() != null)
            return false;
        if (getCompanyDataInputName() != null ? !getCompanyDataInputName().equals(that.getCompanyDataInputName()) : that.getCompanyDataInputName() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getLastModifiedDate() != null ? !getLastModifiedDate().equals(that.getLastModifiedDate()) : that.getLastModifiedDate() != null)
            return false;
        return getLastModifiedBy() != null ? getLastModifiedBy().equals(that.getLastModifiedBy()) : that.getLastModifiedBy() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputId() != null ? getCompanyDataInputId().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputName() != null ? getCompanyDataInputName().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataFileDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", location='" + location + '\'' +
            ", companyDataInputId=" + companyDataInputId +
            ", companyDataInputName='" + companyDataInputName + '\'' +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            '}';
    }
}
