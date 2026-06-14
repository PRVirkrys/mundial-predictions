package com.mundial.predictions.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDTO {

	private int[] ft; // full time [golesLocal, golesVisitante]

	public ScoreDTO() {

	}

	public int[] getFt() {
		return ft;
	}

	public void setFt(int[] ft) {
		this.ft = ft;
	}

}
