package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

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

}
