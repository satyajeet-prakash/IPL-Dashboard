import { React } from 'react';
import { Link } from 'react-router-dom';

export const MatchSmallCard = ({ teamName, match }) => {
    if (!match) return null;
    const vsTeam = match.team1 === teamName ? match.team2 : match.team1;
    const otherTeamRoute = `/teams/${vsTeam}`
    return (
        <div className="MatchSmallCard">
            <h3>vs <Link to={otherTeamRoute}>{vsTeam}</Link></h3>
            <p>{match.matchWinner} won by {match.resultMargin} {match.result}</p>
        </div>
    );
}