package com.mundial.predictions.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.model.Prediction;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

	private final PredictionService predictionService;
	private final MatchService matchService;

	public PredictionController(PredictionService predictionService, MatchService matchService) {
		this.predictionService = predictionService;
		this.matchService = matchService;
	}

	@GetMapping
	public List<Prediction> getAllPredictions() {
		return predictionService.getAllPredictions();
	}

	@PostMapping
	public Prediction createPrediction(@RequestParam Integer userId, @RequestParam Integer matchId,
			@RequestParam Integer homeGoals, @RequestParam Integer awayGoals) {
		return predictionService.createPrediction(userId, matchId, homeGoals, awayGoals);
	}

	// endopoint para comprobar si funciona con postman
	@PostMapping("/evaluate/{matchId}")
	public void evaluatePredictions(@PathVariable Integer matchId) {
		Match match = matchService.getMatchById(matchId);
		predictionService.evaluatePredictions(match);
	}

}
