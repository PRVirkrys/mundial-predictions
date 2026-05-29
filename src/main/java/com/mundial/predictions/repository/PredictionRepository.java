package com.mundial.predictions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.predictions.model.Match;
import com.mundial.predictions.model.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {

	List<Prediction> findByMatch(Match match);

}
