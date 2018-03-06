package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.gov.ofwat.external.domain.message.CompanyStatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyStatus.
 */
@Entity
@Table(name = "company_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public CompanyStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyStatus companyStatus = (CompanyStatus) o;
        if (companyStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
