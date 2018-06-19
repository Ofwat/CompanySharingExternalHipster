package uk.gov.ofwat.external.service.dto.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@XmlRootElement(name = "rowDto")
@XmlType(propOrder = { "cells" })
public class RowDto implements Cloneable {
//	@SerializedName("c")
	private List<CellDto> cells;
//	@SerializedName("ic")
	private String itemCode;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public RowDto() {
		cells = new ArrayList<CellDto>();
	}

	public List<CellDto> getCells() {
		return cells;
	}

	public void setCells(List<CellDto> cells) {
		this.cells = cells;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
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
		RowDto other = (RowDto) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
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
		RowDto other = (RowDto) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else {
			Iterator<CellDto> thisCellIterator =  cells.iterator();
			Iterator<CellDto> otherCellIterator =  other.cells.iterator();
			while (thisCellIterator.hasNext() && otherCellIterator.hasNext()) {
				if (!thisCellIterator.next().equalsValuesAndCoordinates(otherCellIterator.next())) {
					return false;
				}
			}
			if (thisCellIterator.hasNext() || otherCellIterator.hasNext()) {
				return false; // should have the same number of cells
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "RowDto [cells=" + cells + "]";
	}

}
