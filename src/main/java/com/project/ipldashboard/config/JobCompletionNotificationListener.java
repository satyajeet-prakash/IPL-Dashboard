package com.project.ipldashboard.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOG.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamData = new HashMap<>();

            em.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(t -> new Team((String) t[0], (long) t[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            em.createQuery("select distinct m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        team.setTotalMatches((long) e[1] + team.getTotalMatches());
                    });

            em.createQuery("select distinct m.matchWinner, count(*) from Match m group by m.matchWinner",
                    Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if (team != null)
                            team.setTotalWins((long) e[1]);
                    });

            teamData.values().forEach(team -> em.persist(team));

            teamData.values().forEach(team -> LOG.info(team.toString()));
        }
    }
}
