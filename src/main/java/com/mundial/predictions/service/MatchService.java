package com.mundial.predictions.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mundial.predictions.dto.TeamDTO;
import com.mundial.predictions.dto.WorldCupDTO;
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

	private LocalDateTime parseMatchDateTime(String date, String time) {
		if (date == null)
			return null;

		if (time == null || time.isBlank()) {
			return LocalDate.parse(date).atStartOfDay();
		}

		// "13:00 UTC-6" → ["13:00", "UTC-6"]
		String[] parts = time.split(" ");
		String cleanTime = parts[0].trim();

		LocalDateTime localDT = LocalDateTime.parse(date + "T" + cleanTime);

		if (parts.length > 1) {
			// "UTC-6" → ZoneOffset de -6 horas
			String offsetStr = parts[1].replace("UTC", "");
			ZoneOffset offset = ZoneOffset.of(offsetStr);
			// Convertir a UTC
			return localDT.atOffset(offset).withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
		}

		return localDT;
	}

	@Scheduled(cron = "0 0 * * * *")
	public void importMatches() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();

			String jsonStr = restTemplate.getForObject(
					"https://raw.githubusercontent.com/openfootball/worldcup.json/master/2026/worldcup.json",
					String.class);
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
				if (matchDTO.getDate() == null || matchDTO.getTeam1().length() > 20) {
					return;
				}
				Optional<Match> existing = matchRepository.findByHomeTeamAndAwayTeam(matchDTO.getTeam1(),
						matchDTO.getTeam2());
				if (existing.isPresent()) {
					Match match = existing.get();
					match.setLocation(matchDTO.getGround());
					match.setRound(matchDTO.getRound());
					match.setMatchDate(parseMatchDateTime(matchDTO.getDate(), matchDTO.getTime()));
					match.setHomeTeamFlag(teamData.get(matchDTO.getTeam1()));
					match.setAwayTeamFlag(teamData.get(matchDTO.getTeam2()));

					if (matchDTO.getScore() != null && matchDTO.getScore().getFt() != null) {
						match.setHomeGoals(matchDTO.getScore().getFt()[0]);
						match.setAwayGoals(matchDTO.getScore().getFt()[1]);

					}

					matchRepository.save(match);
				} else {
					Match match = new Match();
					match.setHomeTeam(matchDTO.getTeam1());
					match.setAwayTeam(matchDTO.getTeam2());
					match.setLocation(matchDTO.getGround());
					match.setGroup(matchDTO.getGroup());
					match.setRound(matchDTO.getRound());
					match.setMatchDate(parseMatchDateTime(matchDTO.getDate(), matchDTO.getTime()));
					match.setHomeTeamFlag(teamData.get(matchDTO.getTeam1()));
					match.setAwayTeamFlag(teamData.get(matchDTO.getTeam2()));
					matchRepository.save(match);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}