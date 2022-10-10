
package com.project.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ipldashboard.model.Match;
import com.project.ipldashboard.model.Team;
import com.project.ipldashboard.repository.MatchRepository;
import com.project.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;

    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        super();
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/teams/{teamName}")
    public Team geTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);

        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));

        return team;
    }
    
    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate enDate = LocalDate.of(year, 12, 31);
        return this.matchRepository
                .getMatchesByTeamBetweenDates(teamName, startDate, enDate);
    }
}
