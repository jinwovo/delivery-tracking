package com.hycu.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycu.model.TrackingInfo;

public class SweetTrackerResponse {
    @JsonProperty("resultCode")
    private int resultCode;
    
    @JsonProperty("errorMessage")
    private String errorMessage; 
    
    private TrackingInfo trackingInfo;

    public SweetTrackerResponse() {
    }

    public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public TrackingInfo getTrackingInfo() {
		return trackingInfo;
	}

	public void setTrackingInfo(TrackingInfo trackingInfo) {
		this.trackingInfo = trackingInfo;
	}

	public SweetTrackerResponse(int resultCode, TrackingInfo trackingInfo) {
        this.resultCode = resultCode;
        this.trackingInfo = trackingInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
