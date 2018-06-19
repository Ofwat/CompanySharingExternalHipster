package uk.gov.ofwat.external.service.dto;

import java.io.Serializable;

public class DataJobDto implements Serializable {

    private Long id;
    private String uuid;
    private String jobStatus;
    private String fountainReportId;
    private String companyId;
    private String auditComment;
    private String runId;
    private String excelMongoDocId;
    private Long companyDataInputId;
    private String rejectedReason;
    private Boolean updated;
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
