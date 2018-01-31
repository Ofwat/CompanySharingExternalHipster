package uk.gov.ofwat.external.domain.data;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.gov.ofwat.external.domain.Company;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "table")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DCSTable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fountain_report_id")
    private Long fountainReportId;

    @ManyToOne(optional = false)
    @JoinColumn(name="COMPANY_ID", nullable=false)
    @NotNull
    private Company company;

    @Column(name = "fountain_run_id")
    private Long fountainRunId;

    @Column(name = "name")
    private String name;
    @Column(name = "team")
    private String team;
    @Column(name = "user")
    private String user;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "error")
    private Boolean error;


    @OneToMany(mappedBy="table")
    @OrderColumn(name="id")
    private List<DCSRow> rows;


    public DCSTable(Long fountainReportId, Company company, Long fountainRunId, String name, String team, String user, String errorMessage, Boolean error, List<DCSRow> rows) {
        this.fountainReportId = fountainReportId;
        this.company = company;
        this.fountainRunId = fountainRunId;
        this.name = name;
        this.team = team;
        this.user = user;
        this.errorMessage = errorMessage;
        this.error = error;
        this.rows = rows;
    }

    public DCSTable() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFountainReportId() {
        return fountainReportId;
    }

    public void setFountainReportId(Long fountainReportId) {
        this.fountainReportId = fountainReportId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getFountainRunId() {
        return fountainRunId;
    }

    public void setFountainRunId(Long fountainRunId) {
        this.fountainRunId = fountainRunId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<DCSRow> getRows() {
        return rows;
    }

    public void setRows(List<DCSRow> rows) {
        this.rows = rows;
    }
}
