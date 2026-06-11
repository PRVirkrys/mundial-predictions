package com.mundial.predictions.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "matches", uniqueConstraints = { @UniqueConstraint(columnNames = { "home_team", "away_team" }) })

public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String homeTeam;
	private String awayTeam;
	private String location;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime matchDate;
	private Integer homeGoals;
	private Integer awayGoals;
	@Column(name = "match_group")
	private String group;
	private String round;
	private String homeTeamFlag;
	private String awayTeamFlag;
	private boolean evaluated = false;

	// JPA necesita un constructor vacío obligatoriamente. Es como un requisito del
	// framework para poder crear objetos cuando lee datos de la base de datos.
	public Match() {

	}

	public Match(Integer id, String homeTeam, String awayTeam, String location, LocalDateTime matchDate,
			Integer homeGoals, Integer awayGoals, String homeTeamFlag, String awayTeamFlag, String group) {
		super();
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.location = location;
		this.matchDate = matchDate;
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
		this.homeTeamFlag = homeTeamFlag;
		this.awayTeamFlag = awayTeamFlag;
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDateTime matchDate) {
		this.matchDate = matchDate;
	}

	public Integer getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}

	public Integer getAwayGoals() {
		return awayGoals;
	}

	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getHomeTeamFlag() {
		return homeTeamFlag;
	}

	public void setHomeTeamFlag(String homeTeamFlag) {
		this.homeTeamFlag = homeTeamFlag;
	}

	public String getAwayTeamFlag() {
		return awayTeamFlag;
	}

	public void setAwayTeamFlag(String awayTeamFlag) {
		this.awayTeamFlag = awayTeamFlag;
	}

	public boolean isEvaluated() {
		return evaluated;
	}

	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
	}

}