package com.mundial.predictions.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<Prediction> getAllByUser(User user) {
		return predictionRepository.findByUser(user);
	}

	public Prediction createPrediction(Integer userId, Integer matchId, Integer homeGoals, Integer awayGoals) {
		User user = userRepository.findById(userId).orElse(null);
		Match match = matchRepository.findById(matchId).orElse(null);

		if (user == null || match == null) {
			return null;
		}

		Prediction prediction = new Prediction();
		prediction.setUser(user);
		prediction.setMatch(match);
		prediction.setPredictedHomeGoals(homeGoals);
		prediction.setPredictedAwayGoals(awayGoals);
		prediction.setCorrectWinner(false);
		prediction.setCorrectScore(false);

		return predictionRepository.save(prediction);
	}

	public Prediction updatePrediction(Integer id, Integer homeGoals, Integer awayGoals) {
		Prediction prediction = predictionRepository.findById(id).orElse(null);

		if (prediction == null) {
			return null;
		}

		prediction.setPredictedHomeGoals(homeGoals);
		prediction.setPredictedAwayGoals(awayGoals);

		return predictionRepository.save(prediction);
	}

	@Transactional
	public void evaluatePredictions(Match match) {

		if (match == null) {
			System.out.println("MATCH NULL");
			return;
		}

		System.out.println("ENTRANDO A EVALUATE MATCH ID: " + match.getId());

		if (match.getHomeGoals() == null || match.getAwayGoals() == null) {
			System.out.println("GOLES NULL PARA MATCH ID: " + match.getId());
			return;
		}

		List<Prediction> predictions = predictionRepository.findByMatch(match);

		System.out.println("PREDICCIONES ENCONTRADAS: " + predictions.size());

		for (Prediction prediction : predictions) {

			System.out.println("------------------------------");
			System.out.println("Evaluando prediction ID: " + prediction.getId());

			boolean correctScore = isCorrectScore(prediction, match);
			boolean correctWinner = isCorrectWinner(prediction, match);

			// Si acertó marcador exacto, también acertó ganador.
			if (correctScore) {
				correctWinner = true;
			}

			System.out.println(
					"Predicción: " + prediction.getPredictedHomeGoals() + "-" + prediction.getPredictedAwayGoals());

			System.out.println("Real: " + match.getHomeGoals() + "-" + match.getAwayGoals());

			System.out.println("Nuevo correctWinner: " + correctWinner);
			System.out.println("Nuevo correctScore: " + correctScore);

			prediction.setCorrectWinner(correctWinner);
			prediction.setCorrectScore(correctScore);

			predictionRepository.saveAndFlush(prediction);

			User user = prediction.getUser();

			if (user != null) {
				Integer newTotalScore = calculateTotalScore(user);
				user.setTotalScore(newTotalScore);
				userRepository.saveAndFlush(user);

				System.out.println("Nuevo totalScore usuario " + user.getId() + ": " + newTotalScore);
			}
		}

		match.setEvaluated(true);
		matchRepository.saveAndFlush(match);

		System.out.println("MATCH " + match.getId() + " EVALUADO Y GUARDADO");
	}

	private boolean isCorrectScore(Prediction prediction, Match match) {

		if (prediction == null || match == null) {
			return false;
		}

		if (prediction.getPredictedHomeGoals() == null || prediction.getPredictedAwayGoals() == null
				|| match.getHomeGoals() == null || match.getAwayGoals() == null) {
			return false;
		}

		return prediction.getPredictedHomeGoals().equals(match.getHomeGoals())
				&& prediction.getPredictedAwayGoals().equals(match.getAwayGoals());
	}

	private boolean isCorrectWinner(Prediction prediction, Match match) {

		if (prediction == null || match == null) {
			return false;
		}

		if (prediction.getPredictedHomeGoals() == null || prediction.getPredictedAwayGoals() == null
				|| match.getHomeGoals() == null || match.getAwayGoals() == null) {
			return false;
		}

		int predictedResult = Integer.compare(prediction.getPredictedHomeGoals(), prediction.getPredictedAwayGoals());

		int realResult = Integer.compare(match.getHomeGoals(), match.getAwayGoals());

		return predictedResult == realResult;
	}

	private Integer calculateTotalScore(User user) {

		if (user == null) {
			return 0;
		}

		List<Prediction> predictions = predictionRepository.findByUser(user);

		int totalScore = 0;

		for (Prediction prediction : predictions) {

			Match match = prediction.getMatch();

			if (match == null || !match.isEvaluated()) {
				continue;
			}

			boolean correctScore = isCorrectScore(prediction, match);
			boolean correctWinner = isCorrectWinner(prediction, match);

			if (correctScore) {
				totalScore += 3;
			} else if (correctWinner) {
				totalScore += 1;
			}
		}

		return totalScore;
	}
}