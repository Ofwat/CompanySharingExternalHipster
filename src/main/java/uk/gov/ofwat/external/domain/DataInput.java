package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

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

    @Column(name = "report_id", nullable = false)
    private Long reportId;

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
    @JsonIgnore
    private PublishingStatus status;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="DATA_BUNDLE_ID", nullable=false)
    @NotNull
    @JsonIgnore
    private DataBundle dataBundle;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @NotNull
    private User owner;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @NotNull
    private User reviewer;

    @OneToMany(mappedBy="dataInput",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataInput)) return false;

        DataInput dataInput = (DataInput) o;

        if (id != null ? !id.equals(dataInput.id) : dataInput.id != null) return false;
        if (name != null ? !name.equals(dataInput.name) : dataInput.name != null) return false;
        if (reportId != null ? !reportId.equals(dataInput.reportId) : dataInput.reportId != null) return false;
        if (description != null ? !description.equals(dataInput.description) : dataInput.description != null)
            return false;
        if (guidance != null ? !guidance.equals(dataInput.guidance) : dataInput.guidance != null) return false;
        if (defaultDeadline != null ? !defaultDeadline.equals(dataInput.defaultDeadline) : dataInput.defaultDeadline != null)
            return false;
        if (orderIndex != null ? !orderIndex.equals(dataInput.orderIndex) : dataInput.orderIndex != null) return false;
        if (fileName != null ? !fileName.equals(dataInput.fileName) : dataInput.fileName != null) return false;
        if (fileLocation != null ? !fileLocation.equals(dataInput.fileLocation) : dataInput.fileLocation != null)
            return false;
        if (status != null ? !status.equals(dataInput.status) : dataInput.status != null) return false;
        if (dataBundle != null ? !dataBundle.equals(dataInput.dataBundle) : dataInput.dataBundle != null) return false;
        if (owner != null ? !owner.equals(dataInput.owner) : dataInput.owner != null) return false;
        if (reviewer != null ? !reviewer.equals(dataInput.reviewer) : dataInput.reviewer != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(companyDataInput, dataInput.companyDataInput);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (guidance != null ? guidance.hashCode() : 0);
        result = 31 * result + (defaultDeadline != null ? defaultDeadline.hashCode() : 0);
        result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dataBundle != null ? dataBundle.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (reviewer != null ? reviewer.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(companyDataInput);
        return result;
    }

    @Override
    public String toString() {
        return "DataInput{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", reportId=" + reportId +
            ", description='" + description + '\'' +
            ", guidance='" + guidance + '\'' +
            ", defaultDeadline=" + defaultDeadline +
            ", orderIndex=" + orderIndex +
            ", fileName='" + fileName + '\'' +
            ", fileLocation='" + fileLocation + '\'' +
            ", status=" + status +
            ", dataBundle=" + dataBundle +
            ", owner=" + owner +
            ", reviewer=" + reviewer +
            ", companyDataInput=" + Arrays.toString(companyDataInput) +
            '}';
    }
}
