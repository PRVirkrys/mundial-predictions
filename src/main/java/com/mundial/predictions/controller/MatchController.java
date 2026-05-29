package com.mundial.predictions.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.service.MatchService;

@RestController
@RequestMapping("/matches")
public class MatchController {

	private final MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@GetMapping
	public List<Match> getAllMatches() {
		return matchService.getAllMatches();
	}

	@GetMapping("/{id}")
	public Match findMatch(@PathVariable Integer id) {
		return matchService.getMatchById(id);
	}

	@PostMapping
	public Match createMatch(@RequestBody Match match) {
		return matchService.createMatch(match);
	}

}