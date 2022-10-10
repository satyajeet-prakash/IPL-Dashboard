import { React, useEffect, useState } from 'react'; 
import { useParams } from 'react-router-dom';  
import { MatchDetailsCard } from '../components/MatchDetailsCard';

export const MatchPage = () => {

    const [matches, setMatches] = useState([])
    const { teamName, year } = useParams();
    useEffect(
        () => {
            const fetchMatches = async () => {
                const response = await fetch(`http://localhost:8080/teams/${teamName}/matches?year=${year}`);
                const data = await response.json();
                setMatches(data);
            }
            fetchMatches();
        }, []
    )
    return (
        <div className="MatchPage">
            <h1>MatchPage</h1>
            <h1>{teamName}</h1>
            {
                matches.map(match => <MatchDetailsCard teamName={teamName} match={match} />)
            }
        </div>
    );
}