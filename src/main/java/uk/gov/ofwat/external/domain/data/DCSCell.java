package uk.gov.ofwat.external.domain.data;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cell")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DCSCell {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "col")
    private int col;
    @Column(name = "value")
    private String value;
    @Column(name = "decimal_places")
    private int decimalPlaces;
    @Column(name = "cell_format")
    private String cellFormat;
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "data_format")
    private String dataFormat;
    @Column(name = "style")
    private String style;
    @Column(name = "error_flag")
    private boolean errorFlag;
    @Column(name = "error_test")
    private String errorText;
    @Column(name = "key")
    private String key;
    @Column(name = "cell_type")
    private String cellType;

    @ManyToOne(optional = false)
    @JoinColumn(name="ROW_ID", nullable=false)
    @NotNull
    private DCSRow row;

    public DCSCell(int col, String value, int decimalPlaces, String cellFormat, String dataType, String dataFormat, String style, boolean errorFlag, String errorText, String key, String cellType, DCSRow row) {
        this.col = col;
        this.value = value;
        this.decimalPlaces = decimalPlaces;
        this.cellFormat = cellFormat;
        this.dataType = dataType;
        this.dataFormat = dataFormat;
        this.style = style;
        this.errorFlag = errorFlag;
        this.errorText = errorText;
        this.key = key;
        this.cellType = cellType;
        this.row = row;
    }

    public DCSCell() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getCellFormat() {
        return cellFormat;
    }

    public void setCellFormat(String cellFormat) {
        this.cellFormat = cellFormat;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public DCSRow getRow() {
        return row;
    }

    public void setRow(DCSRow row) {
        this.row = row;
    }
}
