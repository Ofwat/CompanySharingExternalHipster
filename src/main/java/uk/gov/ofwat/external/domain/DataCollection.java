package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;

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
    @JsonIgnore
    private PublishingStatus publishingStatus;

    //reinstate    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;

    @ManyToOne(fetch=FetchType.LAZY)
    private User reviewer;

    @Column(name = "description")
    private String description;

    @Column(name = "guidance")
    private String guidance;

    @OneToMany(mappedBy="dataCollection", cascade= CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    @OrderColumn(name="order_Index")
    @JsonIgnore
    private DataBundle[] dataBundles;

    @OneToMany(mappedBy="dataCollection",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @OrderColumn(name="company_data_collection_order_Index")
    @JsonIgnore
    private CompanyDataCollection[] companyDataCollections;


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

    public CompanyDataCollection[] getCompanyDataCollections() {
        return companyDataCollections;
    }

    public void setCompanyDataCollections(CompanyDataCollection[] companyDataCollections) {
        this.companyDataCollections = companyDataCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCollection)) return false;

        DataCollection that = (DataCollection) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (publishingStatus != null ? !publishingStatus.equals(that.publishingStatus) : that.publishingStatus != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (reviewer != null ? !reviewer.equals(that.reviewer) : that.reviewer != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (guidance != null ? !guidance.equals(that.guidance) : that.guidance != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(dataBundles, that.dataBundles)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(companyDataCollections, that.companyDataCollections);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishingStatus != null ? publishingStatus.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (reviewer != null ? reviewer.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (guidance != null ? guidance.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(dataBundles);
        result = 31 * result + Arrays.hashCode(companyDataCollections);
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
            ", companyDataCollections=" + Arrays.toString(companyDataCollections) +
            '}';
    }
}
