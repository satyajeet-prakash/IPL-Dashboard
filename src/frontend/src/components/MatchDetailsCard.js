import { React } from 'react';
import { Link } from 'react-router-dom';

export const MatchDetailsCard = ({ teamName, match }) => {
    if (!match) return null;

    const vsTeam = match.team1 === teamName ? match.team2 : match.team1;
    const otherTeamRoute = `/teams/${vsTeam}`
    return (
        <div className="MatchDetailsCard">
            <h3>Latest Matches</h3>
            <h1>vs <Link to={otherTeamRoute}>{vsTeam}</Link></h1>
            <h2>{match.date}</h2>
            <h3>at {match.venue}</h3>
            <h3>{match.matchWinner} won by {match.resultMargin} {match.result}</h3>
        </div>
    );
}