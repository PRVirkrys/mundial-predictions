package com.mundial.predictions.dto;

import java.util.List;

public class WorldCupDTO {

	private String name;
	private List<MatchDTO> matches;

	public WorldCupDTO() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MatchDTO> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchDTO> matches) {
		this.matches = matches;
	}

}
