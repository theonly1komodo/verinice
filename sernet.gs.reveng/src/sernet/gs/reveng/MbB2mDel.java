package sernet.gs.reveng;

// Generated Jun 5, 2015 1:28:30 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * MbB2mDel generated by hbm2java
 */
public class MbB2mDel implements java.io.Serializable {

	private MbB2mDelId id;
	private MbBaustMassnGsiegel mbBaustMassnGsiegel;
	private SysImport sysImport;
	private int usn;
	private String guid;
	private String guidOrg;
	private Date erstelltAm;
	private String erstelltDurch;

	public MbB2mDel() {
	}

	public MbB2mDel(MbB2mDelId id, MbBaustMassnGsiegel mbBaustMassnGsiegel,
			SysImport sysImport, int usn, String guid, Date erstelltAm,
			String erstelltDurch) {
		this.id = id;
		this.mbBaustMassnGsiegel = mbBaustMassnGsiegel;
		this.sysImport = sysImport;
		this.usn = usn;
		this.guid = guid;
		this.erstelltAm = erstelltAm;
		this.erstelltDurch = erstelltDurch;
	}

	public MbB2mDel(MbB2mDelId id, MbBaustMassnGsiegel mbBaustMassnGsiegel,
			SysImport sysImport, int usn, String guid, String guidOrg,
			Date erstelltAm, String erstelltDurch) {
		this.id = id;
		this.mbBaustMassnGsiegel = mbBaustMassnGsiegel;
		this.sysImport = sysImport;
		this.usn = usn;
		this.guid = guid;
		this.guidOrg = guidOrg;
		this.erstelltAm = erstelltAm;
		this.erstelltDurch = erstelltDurch;
	}

	public MbB2mDelId getId() {
		return this.id;
	}

	public void setId(MbB2mDelId id) {
		this.id = id;
	}

	public MbBaustMassnGsiegel getMbBaustMassnGsiegel() {
		return this.mbBaustMassnGsiegel;
	}

	public void setMbBaustMassnGsiegel(MbBaustMassnGsiegel mbBaustMassnGsiegel) {
		this.mbBaustMassnGsiegel = mbBaustMassnGsiegel;
	}

	public SysImport getSysImport() {
		return this.sysImport;
	}

	public void setSysImport(SysImport sysImport) {
		this.sysImport = sysImport;
	}

	public int getUsn() {
		return this.usn;
	}

	public void setUsn(int usn) {
		this.usn = usn;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGuidOrg() {
		return this.guidOrg;
	}

	public void setGuidOrg(String guidOrg) {
		this.guidOrg = guidOrg;
	}

	public Date getErstelltAm() {
		return this.erstelltAm;
	}

	public void setErstelltAm(Date erstelltAm) {
		this.erstelltAm = erstelltAm;
	}

	public String getErstelltDurch() {
		return this.erstelltDurch;
	}

	public void setErstelltDurch(String erstelltDurch) {
		this.erstelltDurch = erstelltDurch;
	}

}