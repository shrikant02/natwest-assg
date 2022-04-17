package com.shrikant.model;


import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import com.shrikant.enums.Result;
import com.shrikant.enums.State;

@Entity
public class Game {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String gameId;
	private State state;
	
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private List<Player> players;
    
	public Game() {
	}

	public Game(State state) {
		this.gameId = generateId();
		this.state = state;
	}
	
	public String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {

        return "GameId: " + gameId +
                "\nGame state: " + state +
                "\nPlayer 1: " + this.players.get(0).getName() +
                "\nPlayer 1 move: " + this.players.get(0).getMove() +
                "\nPlayer 2: " + this.players.get(1).getName() +
                "\nPlayer 2 move: " + this.players.get(1).getMove() +
                "\nRESULT: " + "Player 1 - " + this.players.get(0).getResult() + ", Player 2 - " + this.players.get(1).getResult() + "\n";
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
