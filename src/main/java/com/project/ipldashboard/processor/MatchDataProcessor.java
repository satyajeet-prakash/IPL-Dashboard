package com.project.ipldashboard.processor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.project.ipldashboard.model.Match;
import com.project.ipldashboard.model.MatchInput;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger LOG = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final Match match = new Match();

        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate(), DateFormatter));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        if ((matchInput.getTeam1().equalsIgnoreCase(matchInput.getToss_winner())
                && "field".equalsIgnoreCase(matchInput.getToss_decision()))
                || (matchInput.getTeam2().equalsIgnoreCase(matchInput.getToss_winner())
                        && "bat".equalsIgnoreCase(matchInput.getToss_decision()))) {
            match.setTeam1(matchInput.getTeam2());
            match.setTeam2(matchInput.getTeam1());
        } else {
            match.setTeam1(matchInput.getTeam1());
            match.setTeam2(matchInput.getTeam2());
        }
 
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setMatchWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        LOG.info("Converting (" + matchInput + ") into (" + match + ")");

        return match;
    }

}