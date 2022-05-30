package com.dw.ngms.cis.enums;

public enum IssueLogStatus {

	OPEN("OPEN"), CLOSED("CLOSED");

	private String status;

	IssueLogStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
