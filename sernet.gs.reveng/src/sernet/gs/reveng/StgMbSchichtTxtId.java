package sernet.gs.reveng;

// Generated Jun 5, 2015 1:28:30 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * StgMbSchichtTxtId generated by hbm2java
 */
public class StgMbSchichtTxtId implements java.io.Serializable {

	private Integer schId;
	private Integer schImpId;
	private Short sprId;
	private String name;
	private String beschreibung;
	private String guid;
	private Date timestamp;
	private Byte impNeu;
	private String guidOrg;

	public StgMbSchichtTxtId() {
	}

	public StgMbSchichtTxtId(Integer schId, Integer schImpId, Short sprId,
			String name, String beschreibung, String guid, Date timestamp,
			Byte impNeu, String guidOrg) {
		this.schId = schId;
		this.schImpId = schImpId;
		this.sprId = sprId;
		this.name = name;
		this.beschreibung = beschreibung;
		this.guid = guid;
		this.timestamp = timestamp;
		this.impNeu = impNeu;
		this.guidOrg = guidOrg;
	}

	public Integer getSchId() {
		return this.schId;
	}

	public void setSchId(Integer schId) {
		this.schId = schId;
	}

	public Integer getSchImpId() {
		return this.schImpId;
	}

	public void setSchImpId(Integer schImpId) {
		this.schImpId = schImpId;
	}

	public Short getSprId() {
		return this.sprId;
	}

	public void setSprId(Short sprId) {
		this.sprId = sprId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Byte getImpNeu() {
		return this.impNeu;
	}

	public void setImpNeu(Byte impNeu) {
		this.impNeu = impNeu;
	}

	public String getGuidOrg() {
		return this.guidOrg;
	}

	public void setGuidOrg(String guidOrg) {
		this.guidOrg = guidOrg;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StgMbSchichtTxtId))
			return false;
		StgMbSchichtTxtId castOther = (StgMbSchichtTxtId) other;

		return ((this.getSchId() == castOther.getSchId()) || (this.getSchId() != null
				&& castOther.getSchId() != null && this.getSchId().equals(
				castOther.getSchId())))
				&& ((this.getSchImpId() == castOther.getSchImpId()) || (this
						.getSchImpId() != null
						&& castOther.getSchImpId() != null && this
						.getSchImpId().equals(castOther.getSchImpId())))
				&& ((this.getSprId() == castOther.getSprId()) || (this
						.getSprId() != null && castOther.getSprId() != null && this
						.getSprId().equals(castOther.getSprId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getBeschreibung() == castOther.getBeschreibung()) || (this
						.getBeschreibung() != null
						&& castOther.getBeschreibung() != null && this
						.getBeschreibung().equals(castOther.getBeschreibung())))
				&& ((this.getGuid() == castOther.getGuid()) || (this.getGuid() != null
						&& castOther.getGuid() != null && this.getGuid()
						.equals(castOther.getGuid())))
				&& ((this.getTimestamp() == castOther.getTimestamp()) || (this
						.getTimestamp() != null
						&& castOther.getTimestamp() != null && this
						.getTimestamp().equals(castOther.getTimestamp())))
				&& ((this.getImpNeu() == castOther.getImpNeu()) || (this
						.getImpNeu() != null && castOther.getImpNeu() != null && this
						.getImpNeu().equals(castOther.getImpNeu())))
				&& ((this.getGuidOrg() == castOther.getGuidOrg()) || (this
						.getGuidOrg() != null && castOther.getGuidOrg() != null && this
						.getGuidOrg().equals(castOther.getGuidOrg())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSchId() == null ? 0 : this.getSchId().hashCode());
		result = 37 * result
				+ (getSchImpId() == null ? 0 : this.getSchImpId().hashCode());
		result = 37 * result
				+ (getSprId() == null ? 0 : this.getSprId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37
				* result
				+ (getBeschreibung() == null ? 0 : this.getBeschreibung()
						.hashCode());
		result = 37 * result
				+ (getGuid() == null ? 0 : this.getGuid().hashCode());
		result = 37 * result
				+ (getTimestamp() == null ? 0 : this.getTimestamp().hashCode());
		result = 37 * result
				+ (getImpNeu() == null ? 0 : this.getImpNeu().hashCode());
		result = 37 * result
				+ (getGuidOrg() == null ? 0 : this.getGuidOrg().hashCode());
		return result;
	}

}
