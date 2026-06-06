package com.mundial.predictions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamDTO {

	private String name;
	@JsonProperty("flag_icon")
	private String flagIcon;
	@JsonProperty("fifa_code")
	private String fifaCode;

	public TeamDTO() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlagIcon() {
		return flagIcon;
	}

	public void setFlagIcon(String flagIcon) {
		this.flagIcon = flagIcon;
	}

	public String getFifaCode() {
		return fifaCode;
	}

	public void setFifaCode(String fifaCode) {
		this.fifaCode = fifaCode;
	}

}
