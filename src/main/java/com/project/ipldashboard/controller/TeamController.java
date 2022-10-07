package com.project.ipldashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.ipldashboard.model.Team;
import com.project.ipldashboard.repository.TeamRepository;

@RestController
public class TeamController {
    
    private TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        super();
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams/{teamName}")
    public Team geTeam(@PathVariable String teamName) {
        return this.teamRepository.findByTeamName(teamName);
    }
}
