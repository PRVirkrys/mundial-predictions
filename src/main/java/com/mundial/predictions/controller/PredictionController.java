package com.mundial.predictions.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.model.Prediction;
import com.mundial.predictions.model.User;
import com.mundial.predictions.service.MatchService;
import com.mundial.predictions.service.PredictionService;
import com.mundial.predictions.service.UserService;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

	private final PredictionService predictionService;
	private final MatchService matchService;
	private final UserService userService;

	public PredictionController(PredictionService predictionService, MatchService matchService,
			UserService userService) {
		this.predictionService = predictionService;
		this.matchService = matchService;
		this.userService = userService;
	}

	@GetMapping
	public List<Prediction> getAllPredictions() {
		return predictionService.getAllPredictions();
	}

	@GetMapping("/{userId}")
	public List<Prediction> getPredictionByUser(@PathVariable Integer userId) {
		User user = userService.findUserById(userId);
		return predictionService.getAllByUser(user);
	}

	@PostMapping
	public Prediction createPrediction(@RequestParam Integer userId, @RequestParam Integer matchId,
			@RequestParam Integer homeGoals, @RequestParam Integer awayGoals) {
		return predictionService.createPrediction(userId, matchId, homeGoals, awayGoals);
	}

	@PutMapping("/{id}")
	public Prediction updatePrediction(@PathVariable Integer id, @RequestParam Integer homeGoals,
			@RequestParam Integer awayGoals) {
		return predictionService.updatePrediction(id, homeGoals, awayGoals);
	}

	// endopoint para comprobar si funciona con postman
	@PostMapping("/evaluate/{matchId}")
	public void evaluatePredictions(@PathVariable Integer matchId) {
		Match match = matchService.getMatchById(matchId);
		predictionService.evaluatePredictions(match);
	}

	@PostMapping("/evaluate/all")

	public void evaluateAllPredictions() {

		List<Match> matches = matchService.getAllMatches();

		for (Match match : matches) {

			predictionService.evaluatePredictions(match);

		}

	}

}
