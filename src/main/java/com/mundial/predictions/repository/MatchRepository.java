package com.mundial.predictions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.predictions.model.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

}