package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the DataCollection entity.
 */
public class DataCollectionDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;
    private PublishingStatus publishingStatus;
    private User owner;
    private User reviewer;
    private String description;
    private String guidance;
    private Instant createdDate;
    private String createdBy;
    private Instant lastModifiedDate;
    private String lastModifiedBy;

    public DataCollectionDTO() {
    }

    public DataCollectionDTO(DataCollection dataCollection) {
        this.id = dataCollection.getId();
        this.name = dataCollection.getName();
        this.publishingStatus = dataCollection.getPublishingStatus();
        this.owner = dataCollection.getOwner();
        this.reviewer = dataCollection.getReviewer();
        this.description = dataCollection.getDescription();
        this.guidance = dataCollection.getGuidance();
        this.createdDate = dataCollection.getCreatedDate();
        this.createdBy = dataCollection.getCreatedBy();
        this.lastModifiedDate = dataCollection.getLastModifiedDate();
        this.lastModifiedBy = dataCollection.getLastModifiedBy();
    }

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

    public PublishingStatus getPublishingStatus() {
        return publishingStatus;
    }

    public void setPublishingStatus(PublishingStatus publishingStatus) {
        this.publishingStatus = publishingStatus;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
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
        if (!(o instanceof DataCollectionDTO)) return false;

        DataCollectionDTO that = (DataCollectionDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getPublishingStatus() != that.getPublishingStatus()) return false;
        if (getOwner() != null ? !getOwner().equals(that.getOwner()) : that.getOwner() != null) return false;
        if (getReviewer() != null ? !getReviewer().equals(that.getReviewer()) : that.getReviewer() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
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
        result = 31 * result + (getPublishingStatus() != null ? getPublishingStatus().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        result = 31 * result + (getReviewer() != null ? getReviewer().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataCollectionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", publishingStatus=" + publishingStatus +
            ", owner=" + owner +
            ", reviewer=" + reviewer +
            ", description='" + description + '\'' +
            ", guidance='" + guidance + '\'' +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            '}';
    }
}
