package uk.gov.ofwat.external.domain;

public class TableMetadata {

    private Long companyId;
    private String auditComment;
    private Long runId;
    private Long fountainReportId;
    private String excelDocMongoId;

    public TableMetadata(Long companyId, String auditComment, Long runId, Long fountainReportId, String excelDocMongoId) {
        this.companyId = companyId;
        this.auditComment = auditComment;
        this.runId = runId;
        this.fountainReportId = fountainReportId;
        this.excelDocMongoId = excelDocMongoId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Long getFountainReportId() {
        return fountainReportId;
    }

    public void setFountainReportId(Long fountainReportId) {
        this.fountainReportId = fountainReportId;
    }

    public String getExcelDocMongoId() {
        return excelDocMongoId;
    }

    public void setExcelDocMongoId(String excelDocMongoId) {
        this.excelDocMongoId = excelDocMongoId;
    }
}
