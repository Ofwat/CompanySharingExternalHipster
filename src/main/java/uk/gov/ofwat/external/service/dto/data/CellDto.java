package uk.gov.ofwat.external.service.dto.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gson.annotations.SerializedName;

//import uk.gov.ofwat.fountain.api.report.CellType;
//import uk.gov.ofwat.fountain.audit.SkipAudit;

@XmlRootElement(name = "cellDto")
@XmlType(propOrder = { "row", "col", "value", "decimalPlaces", "cellFormat", "dataType", "dataFormat", "style", "errorFlag", "errorText", "key", "cellType" })
public class CellDto implements Cloneable {

    @SerializedName("r")
    private int row;
    @SerializedName("c")
    private int col;
    @SerializedName("v")
    private String value;
    private int decimalPlaces;	// We now have the dataFormat so may not need this.
    private String cellFormat;	// "percentage", "numeric", "text".
    private String dataType;	// "calc", "input".
    private String dataFormat;	// Format string. EG "#,##0".
    private String style;		// "text", "calc", "input", "error".
    @SerializedName("e")
    private boolean errorFlag;
    private String errorText;
    private String key;			// data key.
    private String cellType;

	public CellDto(int row, int col, String value, int decimalPlaces,
			String cellFormat, String dataType, String dataFormat,
			String style, boolean errorFlag, String errorText, String key) {
		super();
		this.row = row;
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
	}
	public CellDto() {
		super();
	}

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cellFormat == null) ? 0 : cellFormat.hashCode());
		result = prime * result
				+ ((cellType == null) ? 0 : cellType.hashCode());
		result = prime * result + col;
		result = prime * result
				+ ((dataFormat == null) ? 0 : dataFormat.hashCode());
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + decimalPlaces;
		result = prime * result + (errorFlag ? 1231 : 1237);
		result = prime * result
				+ ((errorText == null) ? 0 : errorText.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + row;
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellDto other = (CellDto) obj;
		if (cellFormat == null) {
			if (other.cellFormat != null)
				return false;
		} else if (!cellFormat.equals(other.cellFormat))
			return false;
		if (cellType != other.cellType)
			return false;
		if (col != other.col)
			return false;
		if (dataFormat == null) {
			if (other.dataFormat != null)
				return false;
		} else if (!dataFormat.equals(other.dataFormat))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (decimalPlaces != other.decimalPlaces)
			return false;
		if (errorFlag != other.errorFlag)
			return false;
		if (errorText == null) {
			if (other.errorText != null)
				return false;
		} else if (!errorText.equals(other.errorText))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (row != other.row)
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public boolean equalsValuesAndCoordinates(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellDto other = (CellDto) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (value == null) {
			if (other.value != null &&
				!other.value.isEmpty())
				return false;
		} else if (!valuesEqual(value, other.value)) {
			return false;
		}
		return true;
	}

	private boolean valuesEqual(String value, String otherValue) {
		try {
			if (Double.parseDouble(value) == Double.parseDouble(otherValue)) {
				// no change to the value
				return true;
			}
		} catch (NumberFormatException e) {
			// Not a number. Text is valid so use a string comparison.
			if (value.equals(otherValue)) {
				// no change to the value
				return true;
			}
		}
		return false;
	}

	protected double toDouble(String value) {
		if (value==null || value.length()==0) return 0;
		return Double.parseDouble(value.replaceAll(",", ""));
	}
	@Override
	public String toString() {
		return "CellDto [row=" + row + ", col=" + col + ", value=" + value
				+ ", decimalPlaces=" + decimalPlaces + ", cellFormat="
				+ cellFormat + ", dataType=" + dataType + ", dataFormat="
				+ dataFormat + ", style=" + style + ", errorFlag=" + errorFlag
				+ ", errorText=" + errorText + ", key=" + key + ", cellType="
				+ cellType + "]";
	}
}
