package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DataCollection.
 */
@Entity
@Table(name = "data_collection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataCollection extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

//    @NotNull
//    @Column(name = "publishingStatus", nullable = false)
//    @Column(name = "publishingStatus")
    @ManyToOne
    private PublishingStatus publishingStatus;

//reinstate    @NotNull
    @ManyToOne
    private User owner;

    @ManyToOne
    private User reviewer;

    @Column(name = "description")
    private String description;

    @Column(name = "guidance")
    private String guidance;

//    @Column(name = "dataBundles")
//    private String[] dataBundles;

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

//    public String[] getDataBundles() {
//        return dataBundles;
//    }
//
//    public void setDataBundles(String[] dataBundles) {
//        this.dataBundles = dataBundles;
//    }

    public DataCollection() {
    }

    public DataCollection(String name, PublishingStatus publishingStatus, User owner, User reviewer, String description, String guidance, String[] dataBundles) {
        this.name = name;
        this.publishingStatus = publishingStatus;
        this.owner = owner;
        this.reviewer = reviewer;
        this.description = description;
        this.guidance = guidance;
//        this.dataBundles = dataBundles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCollection)) return false;

        DataCollection that = (DataCollection) o;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (getPublishingStatus() != that.getPublishingStatus()) return false;
        if (!getOwner().equals(that.getOwner())) return false;
        if (getReviewer() != null ? !getReviewer().equals(that.getReviewer()) : that.getReviewer() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null);
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
//        if (!Arrays.equals(getDataBundles(), that.getDataBundles())) return false;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPublishingStatus().hashCode();
        result = 31 * result + getOwner().hashCode();
        result = 31 * result + (getReviewer() != null ? getReviewer().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
//        result = 31 * result + Arrays.hashCode(getDataBundles());
        return result;
    }

    @Override
    public String toString() {
        return "DataCollection{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", publishingStatus=" + publishingStatus +
            ", owner=" + owner +
            ", reviewer=" + reviewer +
            ", description='" + description + '\'' +
            ", guidance='" + guidance + '\'' +
//            ", dataBundles=" + Arrays.toString(dataBundles) +
            '}';
    }
}
