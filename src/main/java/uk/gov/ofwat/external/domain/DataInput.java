package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DataInput.
 */
@Entity
@Table(name = "data_input")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataInput extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "order_index", nullable = false)
    private Long orderIndex;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file_location", nullable = false)
    private String fileLocation;

    @ManyToOne(optional = false)
    @NotNull
    private PublishingStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name="DATA_BUNDLE_ID", nullable=false)
    @NotNull
    private DataBundle dataBundle;

    @ManyToOne(optional = false)
    @NotNull
    private User owner;

    @ManyToOne(optional = false)
    @NotNull
    private User reviewer;

    @OneToMany(mappedBy="dataInput")
    @OrderColumn(name="company_data_input_order_Index")
    private CompanyDataInput[] companyDataInput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataInput name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DataInput description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidance() {
        return guidance;
    }

    public DataInput guidance(String guidance) {
        this.guidance = guidance;
        return this;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public LocalDate getDefaultDeadline() {
        return defaultDeadline;
    }

    public DataInput defaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
        return this;
    }

    public void setDefaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public DataInput orderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public DataInput fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public DataInput fileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public PublishingStatus getStatus() {
        return status;
    }

    public DataInput status(PublishingStatus publishingStatus) {
        this.status = publishingStatus;
        return this;
    }

    public void setStatus(PublishingStatus publishingStatus) {
        this.status = publishingStatus;
    }

    public DataBundle getDataBundle() {
        return dataBundle;
    }

    public DataInput dataBundle(DataBundle dataBundle) {
        this.dataBundle = dataBundle;
        return this;
    }

    public void setDataBundle(DataBundle dataBundle) {
        this.dataBundle = dataBundle;
    }

    public User getOwner() {
        return owner;
    }

    public DataInput owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getReviewer() {
        return reviewer;
    }

    public DataInput reviewer(User user) {
        this.reviewer = user;
        return this;
    }

    public void setReviewer(User user) {
        this.reviewer = user;
    }

    public CompanyDataInput[] getCompanyDataInput() {
        return companyDataInput;
    }

    public void setCompanyDataInput(CompanyDataInput[] companyDataInput) {
        this.companyDataInput = companyDataInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataInput dataInput = (DataInput) o;
        if (dataInput.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataInput.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataInput{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", guidance='" + getGuidance() + "'" +
            ", defaultDeadline='" + getDefaultDeadline() + "'" +
            ", orderIndex='" + getOrderIndex() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileLocation='" + getFileLocation() + "'" +
            "}";
    }
}
