package uk.gov.ofwat.external.domain.data;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.gov.ofwat.external.domain.DataCollection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "row")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DCSRow {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="row")
    @OrderColumn(name="id")
    private List<DCSCell> cells;

    @Column(name = "item_code")
    private String itemCode;

    @ManyToOne(optional = false)
    @JoinColumn(name="TABLE_ID", nullable=false)
    @NotNull
    private DCSTable table;

    public DCSRow(List<DCSCell> cells, String itemCode, DCSTable table) {
        this.cells = cells;
        this.itemCode = itemCode;
        this.table = table;
    }

    public DCSRow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DCSCell> getCells() {
        return cells;
    }

    public void setCells(List<DCSCell> cells) {
        this.cells = cells;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public DCSTable getTable() {
        return table;
    }

    public void setTable(DCSTable table) {
        this.table = table;
    }
}
