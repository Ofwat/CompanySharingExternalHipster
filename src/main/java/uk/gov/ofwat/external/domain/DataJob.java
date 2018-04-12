package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "data_job")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataJob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "job_status")
    private String jobStatus;

    @Column(name = "fountain_report_id")
    private String fountainReportId;

    @Column(name = "fountain_company_id")
    private String companyId;

    @Column(name = "fountain_audit_comment")
    private String auditComment;

    @Column(name = "fountain_run_id")
    private String runId;

    @Column(name = "fountain_excel_mongo_id")
    private String excelMongoDocId;

    @Column(name = "company_data_input_id")
    private Long companyDataInputId;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "updated")
    private Boolean updated;

    @Lob
    @Column(name = "data")
    private String data;


    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="REJECTION_CODES_ID")
    @NotNull
    @JsonIgnore
    private RejectionCodes rejectionCodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getFountainReportId() {
        return fountainReportId;
    }

    public void setFountainReportId(String fountainReportId) {
        this.fountainReportId = fountainReportId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getExcelMongoDocId() {
        return excelMongoDocId;
    }

    public void setExcelMongoDocId(String excelMongoDocId) {
        this.excelMongoDocId = excelMongoDocId;
    }

    public Long getCompanyDataInputId() {
        return companyDataInputId;
    }

    public void setCompanyDataInputId(Long companyDataInputId) {
        this.companyDataInputId = companyDataInputId;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RejectionCodes getRejectionCodes() {
        return rejectionCodes;
    }

    public void setRejectionCodes(RejectionCodes rejectionCodes) {
        this.rejectionCodes = rejectionCodes;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DataJob)) return false;
        if (!super.equals(object)) return false;

        DataJob dataJob = (DataJob) object;

        if (getId() != null ? !getId().equals(dataJob.getId()) : dataJob.getId() != null) return false;
        if (getUuid() != null ? !getUuid().equals(dataJob.getUuid()) : dataJob.getUuid() != null) return false;
        if (getJobStatus() != null ? !getJobStatus().equals(dataJob.getJobStatus()) : dataJob.getJobStatus() != null)
            return false;
        if (getFountainReportId() != null ? !getFountainReportId().equals(dataJob.getFountainReportId()) : dataJob.getFountainReportId() != null)
            return false;
        if (getCompanyId() != null ? !getCompanyId().equals(dataJob.getCompanyId()) : dataJob.getCompanyId() != null)
            return false;
        if (getAuditComment() != null ? !getAuditComment().equals(dataJob.getAuditComment()) : dataJob.getAuditComment() != null)
            return false;
        if (getRunId() != null ? !getRunId().equals(dataJob.getRunId()) : dataJob.getRunId() != null) return false;
        if (getExcelMongoDocId() != null ? !getExcelMongoDocId().equals(dataJob.getExcelMongoDocId()) : dataJob.getExcelMongoDocId() != null)
            return false;
        if (getCompanyDataInputId() != null ? !getCompanyDataInputId().equals(dataJob.getCompanyDataInputId()) : dataJob.getCompanyDataInputId() != null)
            return false;
        if (getRejectedReason() != null ? !getRejectedReason().equals(dataJob.getRejectedReason()) : dataJob.getRejectedReason() != null)
            return false;
        if (getUpdated() != null ? !getUpdated().equals(dataJob.getUpdated()) : dataJob.getUpdated() != null)
            return false;
        if (getData() != null ? !getData().equals(dataJob.getData()) : dataJob.getData() != null) return false;
        if (getRejectionCodes() != null ? !getRejectionCodes().equals(dataJob.getRejectionCodes()) : dataJob.getRejectionCodes() != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getUuid() != null ? getUuid().hashCode() : 0);
        result = 31 * result + (getJobStatus() != null ? getJobStatus().hashCode() : 0);
        result = 31 * result + (getFountainReportId() != null ? getFountainReportId().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        result = 31 * result + (getAuditComment() != null ? getAuditComment().hashCode() : 0);
        result = 31 * result + (getRunId() != null ? getRunId().hashCode() : 0);
        result = 31 * result + (getExcelMongoDocId() != null ? getExcelMongoDocId().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputId() != null ? getCompanyDataInputId().hashCode() : 0);
        result = 31 * result + (getRejectedReason() != null ? getRejectedReason().hashCode() : 0);
        result = 31 * result + (getUpdated() != null ? getUpdated().hashCode() : 0);
        result = 31 * result + (getData() != null ? getData().hashCode() : 0);
        result = 31 * result + (getRejectionCodes() != null ? getRejectionCodes().hashCode() : 0);
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "DataJob{" +
            "id=" + id +
            ", uuid='" + uuid + '\'' +
            ", jobStatus='" + jobStatus + '\'' +
            ", fountainReportId='" + fountainReportId + '\'' +
            ", companyId='" + companyId + '\'' +
            ", auditComment='" + auditComment + '\'' +
            ", runId='" + runId + '\'' +
            ", excelMongoDocId='" + excelMongoDocId + '\'' +
            ", companyDataInputId=" + companyDataInputId +
            ", rejectedReason='" + rejectedReason + '\'' +
            ", updated=" + updated +
            ", data='" + data + '\'' +
            ", rejectionCodes=" + rejectionCodes +
            '}';
    }
}
