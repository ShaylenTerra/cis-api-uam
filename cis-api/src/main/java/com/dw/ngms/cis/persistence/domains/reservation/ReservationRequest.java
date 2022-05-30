package com.dw.ngms.cis.persistence.domains.reservation;

import javax.persistence.*;

@Entity
@Table(name = "RESERVATION_REQUEST")
public class ReservationRequest {
    @Id
    @Column(name = "REQUEST_ID")
    private Long requestId;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "DRAFT_ID")
    private Long draftId;

    @Column(name = "STEP_NO")
    private Long stepNo;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "PARCEL")
    private Long parcel;

    @Column(name = "PORTION")
    private Long portion;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getDraftId() {
        return this.draftId;
    }

    public void setDraftId(Long draftId) {
        this.draftId = draftId;
    }

    public Long getStepNo() {
        return this.stepNo;
    }

    public void setStepNo(Long stepNo) {
        this.stepNo = stepNo;
    }

    public String getLpi() {
        return this.lpi;
    }

    public void setLpi(String lpi) {
        this.lpi = lpi;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getParcel() {
        return this.parcel;
    }

    public void setParcel(Long parcel) {
        this.parcel = parcel;
    }

    public Long getPortion() {
        return this.portion;
    }

    public void setPortion(Long portion) {
        this.portion = portion;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
