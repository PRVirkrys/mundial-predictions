package com.mundial.predictions.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.predictions.model.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {
	Optional<Match> findByHomeTeamAndAwayTeamAndMatchDate(String homeTeam, String awayTeam, LocalDateTime matchDate);

	Optional<Match> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam);
}