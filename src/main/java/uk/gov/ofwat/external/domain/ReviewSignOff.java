package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ReviewSignOff.
 */
@Entity
@Table(name = "review_sign_off")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReviewSignOff implements Serializable {

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
    private CompanyDataInput companyDataInput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public ReviewSignOff status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public ReviewSignOff reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getSignatory() {
        return signatory;
    }

    public ReviewSignOff signatory(User user) {
        this.signatory = user;
        return this;
    }

    public void setSignatory(User user) {
        this.signatory = user;
    }

    public CompanyDataInput getCompanyDataInput() {
        return companyDataInput;
    }

    public ReviewSignOff companyDataInput(CompanyDataInput companyDataInput) {
        this.companyDataInput = companyDataInput;
        return this;
    }

    public void setCompanyDataInput(CompanyDataInput companyDataInput) {
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
        ReviewSignOff reviewSignOff = (ReviewSignOff) o;
        if (reviewSignOff.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewSignOff.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewSignOff{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
