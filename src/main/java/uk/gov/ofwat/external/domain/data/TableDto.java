package uk.gov.ofwat.external.domain.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "tableDto")
@XmlType(propOrder = { "id", "name", "team", "user", "rows", "error", "errorMessage" })
public class TableDto implements Cloneable {

	private int id;
	@SerializedName("n")
	private String name;
	@SerializedName("t")
	private String team;
	@SerializedName("u")
	private String user;
	@SerializedName("em")
	private String errorMessage;
	@SerializedName("e")
	private Boolean error;
	@SerializedName("r")
	private List<RowDto> rows;
	@SerializedName("mid")
	private String excelDocMongoId;

	public TableDto() {
		rows = new ArrayList<RowDto>();
	}

	public String getExcelDocMongoId() {
		return excelDocMongoId;
	}

	public void setExcelDocMongoId(String excelDocMongoId) {
		this.excelDocMongoId = excelDocMongoId;
	}



	public List<RowDto> getRows() {
		return rows;
	}

	public void setRows(List<RowDto> rows) {
		this.rows = rows;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Boolean isError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result
				+ ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		TableDto other = (TableDto) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
		TableDto other = (TableDto) obj;
		if (id != other.id)
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else {
			Iterator<RowDto> thisRowIterator =  rows.iterator();
			Iterator<RowDto> otherRowIterator =  other.rows.iterator();
			while (thisRowIterator.hasNext() && otherRowIterator.hasNext()) {
				if (!thisRowIterator.next().equalsValuesAndCoordinates(otherRowIterator.next())) {
					return false;
				}
			}
			if (thisRowIterator.hasNext() || otherRowIterator.hasNext()) {
				return false; // should have the same number of cells
			}
		}
		return true;
	}

	public TableDto clone() {
		TableDto clone = null;
		try {
			clone = (TableDto) super.clone();
		} catch (CloneNotSupportedException e) {
			// Cannot happen as we inherit from Object.
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public String toString() {
		return "TableDto [id=" + id + ", name=" + name + ", team=" + team
				+ ", user=" + user + ", errorMessage=" + errorMessage
				+ ", error=" + error + ", rows=" + rows + "]";
	}



}
