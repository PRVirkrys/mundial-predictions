package com.mundial.predictions.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.repository.MatchRepository;

@Service
public class MatchService {

	private final MatchRepository matchRepository;

	public MatchService(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	public List<Match> getAllMatches() {
		return matchRepository.findAll();
	}

	public Match createMatch(Match match) {
		return matchRepository.save(match);
	}

	public Match getMatchById(Integer id) {
		return matchRepository.findById(id).orElse(null);
	}
}