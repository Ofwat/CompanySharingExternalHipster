package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * A DataBundle.
 */
@Entity
@Table(name = "data_bundle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataBundle extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "guidance")
    private String guidance;

    @Column(name = "default_deadline")
    private LocalDate defaultDeadline;

    @NotNull
    @Column(name = "order_Index", nullable = false)
    private Long orderIndex;

    @ManyToOne(optional = false)
    @NotNull
    private PublishingStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private User owner;

    @ManyToOne(optional = false)
    @NotNull
    private User reviewer;

    @ManyToOne(optional = false)
    @JoinColumn(name="DATA_COLLECTION_ID", nullable=false)
    @NotNull
    private DataCollection dataCollection;

    @OneToMany(mappedBy="dataBundle")
    @OrderColumn(name="order_Index")
    private DataInput[] dataInputs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataBundle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DataBundle description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidance() {
        return guidance;
    }

    public DataBundle guidance(String guidance) {
        this.guidance = guidance;
        return this;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public LocalDate getDefaultDeadline() {
        return defaultDeadline;
    }

    public DataBundle defaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
        return this;
    }

    public void setDefaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
    }

    public PublishingStatus getStatus() {
        return status;
    }

    public DataBundle status(PublishingStatus publishingStatus) {
        this.status = publishingStatus;
        return this;
    }

    public void setStatus(PublishingStatus publishingStatus) {
        this.status = publishingStatus;
    }

    public User getOwner() {
        return owner;
    }

    public DataBundle owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getReviewer() {
        return reviewer;
    }

    public DataBundle reviewer(User user) {
        this.reviewer = user;
        return this;
    }

    public void setReviewer(User user) {
        this.reviewer = user;
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public DataBundle dataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
        return this;
    }

    public void setDataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }

    public DataInput[] getDataInputs() {
        return dataInputs;
    }

    public void setDataInputs(DataInput[] dataInputs) {
        this.dataInputs = dataInputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBundle)) return false;

        DataBundle that = (DataBundle) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
            return false;
        if (getDefaultDeadline() != null ? !getDefaultDeadline().equals(that.getDefaultDeadline()) : that.getDefaultDeadline() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) return false;
        if (getOwner() != null ? !getOwner().equals(that.getOwner()) : that.getOwner() != null) return false;
        if (getReviewer() != null ? !getReviewer().equals(that.getReviewer()) : that.getReviewer() != null)
            return false;
        if (getDataCollection() != null ? !getDataCollection().equals(that.getDataCollection()) : that.getDataCollection() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getDataInputs(), that.getDataInputs());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + (getDefaultDeadline() != null ? getDefaultDeadline().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        result = 31 * result + (getReviewer() != null ? getReviewer().hashCode() : 0);
        result = 31 * result + (getDataCollection() != null ? getDataCollection().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getDataInputs());
        return result;
    }

}
