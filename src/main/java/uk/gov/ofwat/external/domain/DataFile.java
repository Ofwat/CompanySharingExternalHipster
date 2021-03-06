package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DataFile.
 */
@Entity
@Table(name = "data_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataFile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPANY_DATA_INPUT_ID", nullable=false)
    private CompanyDataInput companyDataInput;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataFile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public DataFile location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CompanyDataInput getCompanyDataInput() {
        return companyDataInput;
    }

    public DataFile companyDataInput(CompanyDataInput companyDataInput) {
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
        DataFile dataFile = (DataFile) o;
        if (dataFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataFile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
