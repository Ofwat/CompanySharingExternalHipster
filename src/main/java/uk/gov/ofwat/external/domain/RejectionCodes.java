package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rejection_codes")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RejectionCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof RejectionCodes)) return false;
        if (!super.equals(object)) return false;

        RejectionCodes that = (RejectionCodes) object;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "RejectionCodes{" +
            "id=" + id +
            ", description='" + description + '\'' +
            '}';
    }
}
