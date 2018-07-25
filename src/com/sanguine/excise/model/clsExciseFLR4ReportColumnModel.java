package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "")
public class clsExciseFLR4ReportColumnModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "intId")
	private long intId;

	@Column(name = "strCol1", columnDefinition = "VARCHAR(50)")
	private String strCol1;

	@Column(name = "strCol2", columnDefinition = "VARCHAR(50)")
	private String strCol2;

	@Column(name = "strCol3", columnDefinition = "VARCHAR(50)")
	private String strCol3;

	@Column(name = "strCol4", columnDefinition = "VARCHAR(50)")
	private String strCol4;

	@Column(name = "strCol5", columnDefinition = "VARCHAR(50)")
	private String strCol5;

	@Column(name = "strCol6", columnDefinition = "VARCHAR(50)")
	private String strCol6;

	@Column(name = "strCol7", columnDefinition = "VARCHAR(50)")
	private String strCol7;

	@Column(name = "strCol8", columnDefinition = "VARCHAR(50)")
	private String strCol8;

	@Column(name = "strCol9", columnDefinition = "VARCHAR(50)")
	private String strCol9;

	@Column(name = "strCol10", columnDefinition = "VARCHAR(50)")
	private String strCol10;

	@Column(name = "strCol11", columnDefinition = "VARCHAR(50)")
	private String strCol11;

	@Column(name = "strCol12", columnDefinition = "VARCHAR(50)")
	private String strCol12;

	@Column(name = "strClientCode")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrCol1() {
		return strCol1;
	}

	public void setStrCol1(String strCol1) {
		this.strCol1 = strCol1;
	}

	public String getStrCol2() {
		return strCol2;
	}

	public void setStrCol2(String strCol2) {
		this.strCol2 = strCol2;
	}

	public String getStrCol3() {
		return strCol3;
	}

	public void setStrCol3(String strCol3) {
		this.strCol3 = strCol3;
	}

	public String getStrCol4() {
		return strCol4;
	}

	public void setStrCol4(String strCol4) {
		this.strCol4 = strCol4;
	}

	public String getStrCol5() {
		return strCol5;
	}

	public void setStrCol5(String strCol5) {
		this.strCol5 = strCol5;
	}

	public String getStrCol6() {
		return strCol6;
	}

	public void setStrCol6(String strCol6) {
		this.strCol6 = strCol6;
	}

	public String getStrCol7() {
		return strCol7;
	}

	public void setStrCol7(String strCol7) {
		this.strCol7 = strCol7;
	}

	public String getStrCol8() {
		return strCol8;
	}

	public void setStrCol8(String strCol8) {
		this.strCol8 = strCol8;
	}

	public String getStrCol9() {
		return strCol9;
	}

	public void setStrCol9(String strCol9) {
		this.strCol9 = strCol9;
	}

	public String getStrCol10() {
		return strCol10;
	}

	public void setStrCol10(String strCol10) {
		this.strCol10 = strCol10;
	}

	public String getStrCol11() {
		return strCol11;
	}

	public void setStrCol11(String strCol11) {
		this.strCol11 = strCol11;
	}

	public String getStrCol12() {
		return strCol12;
	}

	public void setStrCol12(String strCol12) {
		this.strCol12 = strCol12;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
