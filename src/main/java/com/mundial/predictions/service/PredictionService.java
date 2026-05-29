package com.mundial.predictions.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.model.Prediction;
import com.mundial.predictions.model.User;
import com.mundial.predictions.repository.MatchRepository;
import com.mundial.predictions.repository.PredictionRepository;
import com.mundial.predictions.repository.UserRepository;

@Service
public class PredictionService {

	private final PredictionRepository predictionRepository;
	private final UserRepository userRepository;
	private final MatchRepository matchRepository;

	public PredictionService(PredictionRepository predictionRepository, UserRepository userRepository,
			MatchRepository matchRepository) {
		this.predictionRepository = predictionRepository;
		this.userRepository = userRepository;
		this.matchRepository = matchRepository;
	}

	public List<Prediction> getAllPredictions() {
		return predictionRepository.findAll();
	}

	public Prediction createPrediction(Integer userId, Integer matchId, Integer homeGoals, Integer awayGoals) {
		User user = userRepository.findById(userId).orElse(null);
		Match match = matchRepository.findById(matchId).orElse(null);

		Prediction prediction = new Prediction();
		prediction.setUser(user);
		prediction.setMatch(match);
		prediction.setPredictedHomeGoals(homeGoals);
		prediction.setPredictedAwayGoals(awayGoals);

		return predictionRepository.save(prediction);
	}

	public void evaluatePredictions(Match match) {

		String realWinner = null;
		boolean isTie = false;

		String predictionWinner = null;
		boolean predictionTie = false;

		if (match.getMatchDate().isBefore(LocalDateTime.now())) {

			List<Prediction> predictions = predictionRepository.findByMatch(match);

			if (match.getHomeGoals() > match.getAwayGoals()) {
				realWinner = match.getHomeTeam();
			} else if (match.getAwayGoals() < match.getHomeGoals()) {
				realWinner = match.getAwayTeam();
			} else {
				isTie = true;
			}

			for (Prediction prediction : predictions) {

				if (prediction.getPredictedHomeGoals() > prediction.getPredictedAwayGoals()) {
					predictionWinner = match.getHomeTeam();
				} else if (prediction.getPredictedHomeGoals() < prediction.getPredictedAwayGoals()) {
					predictionWinner = match.getAwayTeam();
				} else {
					predictionTie = true;
				}

				if (isTie && predictionTie) {
					prediction.setCorrectWinner(true);
				} else if (!isTie && !predictionTie && predictionWinner.equals(realWinner)) {
					prediction.setCorrectWinner(true);
				} else {
					prediction.setCorrectWinner(false);
				}

				if (prediction.getPredictedHomeGoals().equals(match.getHomeGoals())
						&& prediction.getPredictedAwayGoals().equals(match.getAwayGoals())) {
					prediction.setCorrectScore(true);
				} else {
					prediction.setCorrectScore(false);
				}

				predictionRepository.save(prediction);

			}

		}

	}

}
