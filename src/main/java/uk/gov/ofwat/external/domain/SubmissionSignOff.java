package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SubmissionSignOff.
 */
@Entity
@Table(name = "submission_sign_off")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubmissionSignOff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(optional = false)
    @NotNull
    private User signatory;

    @ManyToOne(optional = false)
    @NotNull
    private CompanyDataBundle companyDataBundle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public SubmissionSignOff status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public SubmissionSignOff reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getSignatory() {
        return signatory;
    }

    public SubmissionSignOff signatory(User user) {
        this.signatory = user;
        return this;
    }

    public void setSignatory(User user) {
        this.signatory = user;
    }

    public CompanyDataBundle getCompanyDataBundle() {
        return companyDataBundle;
    }

    public SubmissionSignOff companyDataBundle(CompanyDataBundle companyDataBundle) {
        this.companyDataBundle = companyDataBundle;
        return this;
    }

    public void setCompanyDataBundle(CompanyDataBundle companyDataBundle) {
        this.companyDataBundle = companyDataBundle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubmissionSignOff submissionSignOff = (SubmissionSignOff) o;
        if (submissionSignOff.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), submissionSignOff.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubmissionSignOff{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
