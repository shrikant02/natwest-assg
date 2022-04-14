package com.shrikant.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	@OneToOne(mappedBy = "game")
	private Player player1;
	@OneToOne(mappedBy = "game")
	private Player player2;
	
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
	public Player getPlayer1() {
		return player1;
	}
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	@Override
	public String toString() {

        return "GameId: " + gameId +
                "\nGame state: " + state +
                "\nPlayer 1: " + player1.getName() +
                "\nPlayer 1 move: " + player1.getMove() +
                "\nPlayer 2: " + player2.getName() +
                "\nPlayer 2 move: " + player2.getMove() +
                "\nRESULT: " + "Player 1 - " + player1.getResult() + ", Player 2 - " + player2.getResult() + "\n";
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
