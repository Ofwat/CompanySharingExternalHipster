package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;

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

    @OneToMany(mappedBy="dataCollection")
    @OrderColumn(name="order_Index")
    private DataBundle[] dataBundles;

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

    public DataBundle[] getDataBundles() {
        return dataBundles;
    }

    public void setDataBundles(DataBundle[] dataBundles) {
        this.dataBundles = dataBundles;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DataCollection)) return false;
        if (!super.equals(object)) return false;

        DataCollection that = (DataCollection) object;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getPublishingStatus() != null ? !getPublishingStatus().equals(that.getPublishingStatus()) : that.getPublishingStatus() != null)
            return false;
        if (getOwner() != null ? !getOwner().equals(that.getOwner()) : that.getOwner() != null) return false;
        if (getReviewer() != null ? !getReviewer().equals(that.getReviewer()) : that.getReviewer() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
            return false;
        if (!java.util.Arrays.equals(getDataBundles(), that.getDataBundles())) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPublishingStatus() != null ? getPublishingStatus().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        result = 31 * result + (getReviewer() != null ? getReviewer().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + java.util.Arrays.hashCode(getDataBundles());
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
            ", dataBundles=" + Arrays.toString(dataBundles) +
            '}';
    }
}
