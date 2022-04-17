package com.shrikant.model;

import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import com.shrikant.enums.Result;
import com.shrikant.enums.State;

@Entity
public class Game {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private UUID gameId;
	private State state;
	
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private Map<String,Player> players;
    
	public Game() {
	}

	public Game(State state) {
		this.gameId = generateId();
		this.state = state;
	}
	
	public UUID generateId() {
		return UUID.randomUUID();
	}
	public UUID getGameId() {
		return gameId;
	}
	public void setGameId(UUID gameId) {
		this.gameId = gameId;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", state=" + state + ", players=" + players + "]";
	}

	public void evaluateMove(Player player1, Player player2) {
    	
    	if(player1.getMove().equals(player2.getMove())) {
    		player1.setResult(Result.DRAW);
    		player2.setResult(Result.DRAW);
    	}
    	else if(player1.getMove().winsOver(player2.getMove())) {
    		player1.setResult(Result.WIN);
    		player2.setResult(Result.LOSE);
    	}
    	else {
    		player1.setResult(Result.LOSE);
    		player2.setResult(Result.WIN);
    	}
     }
}
