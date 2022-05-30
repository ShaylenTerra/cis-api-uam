package com.dw.ngms.cis.enums;

public enum Status {

	Y("Active"),
	N("Inactive");

	private String status;
	
	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
