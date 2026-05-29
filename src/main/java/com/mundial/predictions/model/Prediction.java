package com.mundial.predictions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "predictions")
public class Prediction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "match_id")
	private Match match;

	private Integer predictedHomeGoals;
	private Integer predictedAwayGoals;
	private Boolean correctWinner;
	private Boolean correctScore;

	public Prediction() {
	}

	public Prediction(Integer id, User user, Match match, Integer predictedHomeGoals, Integer predictedAwayGoals,
			Boolean correctWinner, Boolean correctScore) {
		super();
		this.id = id;
		this.user = user;
		this.match = match;
		this.predictedHomeGoals = predictedHomeGoals;
		this.predictedAwayGoals = predictedAwayGoals;
		this.correctWinner = correctWinner;
		this.correctScore = correctScore;
	}

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Integer getPredictedHomeGoals() {
		return predictedHomeGoals;
	}

	public void setPredictedHomeGoals(Integer predictedHomeGoals) {
		this.predictedHomeGoals = predictedHomeGoals;
	}

	public Integer getPredictedAwayGoals() {
		return predictedAwayGoals;
	}

	public void setPredictedAwayGoals(Integer predictedAwayGoals) {
		this.predictedAwayGoals = predictedAwayGoals;
	}

	public Boolean getCorrectWinner() {
		return correctWinner;
	}

	public void setCorrectWinner(Boolean correctWinner) {
		this.correctWinner = correctWinner;
	}

	public Boolean getCorrectScore() {
		return correctScore;
	}

	public void setCorrectScore(Boolean correctScore) {
		this.correctScore = correctScore;
	}
}