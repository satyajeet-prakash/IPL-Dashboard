package com.project.ipldashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.ipldashboard.model.Team;
import com.project.ipldashboard.repository.MatchRepository;
import com.project.ipldashboard.repository.TeamRepository;

@RestController
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
        Team team =  this.teamRepository.findByTeamName(teamName);
        
        team.setMatches(this.matchRepository.getByTeam1OrTeam2(teamName, teamName));
        
        return team;
    }
}
