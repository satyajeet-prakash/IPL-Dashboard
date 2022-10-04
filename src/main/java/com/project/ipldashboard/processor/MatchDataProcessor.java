package com.project.ipldashboard.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import com.project.ipldashboard.data.MatchInput;

public class MatchDataProcessor implements ItemProcessor<MatchInput, MatchInput> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public MatchInput process(final MatchInput match) throws Exception {
        final String team1 = match.getTeam1().toUpperCase();
        final String team2 = match.getTeam2().toUpperCase();

        final MatchInput processedMatch = new MatchInput();

        log.info("Converting (" + match + ") into (" + processedMatch + ")");

        return processedMatch;
    }

}