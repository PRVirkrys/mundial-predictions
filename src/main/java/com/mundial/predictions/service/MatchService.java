package com.mundial.predictions.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mundial.predictions.dto.TeamDTO;
import com.mundial.predictions.dto.WorldCupDTO;
import com.mundial.predictions.model.Match;
import com.mundial.predictions.repository.MatchRepository;

import tools.jackson.databind.ObjectMapper;

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

	public void importMatches() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();

		String jsonStr = restTemplate.getForObject(
				"https://raw.githubusercontent.com/openfootball/worldcup.json/master/2026/worldcup.json", String.class);

		WorldCupDTO worldCup = mapper.readValue(jsonStr, WorldCupDTO.class);

		String teamsStr = restTemplate.getForObject(
				"https://raw.githubusercontent.com/openfootball/worldcup.json/refs/heads/master/2026/worldcup.teams.json",
				String.class);
		TeamDTO[] teams = mapper.readValue(teamsStr, TeamDTO[].class);

		Map<String, String> teamData = new HashMap<>();

		for (TeamDTO team : teams) {
			teamData.put(team.getName(), team.getFlagIcon());
		}

		worldCup.getMatches().forEach(matchDTO -> {
			Match match = new Match();
			match.setHomeTeam(matchDTO.getTeam1());
			match.setAwayTeam(matchDTO.getTeam2());
			match.setLocation(matchDTO.getGround());
			match.setGroup(matchDTO.getGroup());
			match.setRound(matchDTO.getRound());
			if (matchDTO.getDate() != null) {
				LocalDate date = LocalDate.parse(matchDTO.getDate());
				match.setMatchDate(date.atStartOfDay());
			}
			match.setHomeTeamFlag(teamData.get(matchDTO.getTeam1()));
			match.setAwayTeamFlag(teamData.get(matchDTO.getTeam2()));
			matchRepository.save(match);
		});

	}

}