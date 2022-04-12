package com.shrikant.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shrikant.enums.Move;
import com.shrikant.enums.State;
import com.shrikant.model.Game;
import com.shrikant.model.Player;

@RestController
public class GameController {
	
	
	public Game game;
	public Player player1;
	public Player player2;
	
	
	/* controller for creating new game */
	@PostMapping("/api/games")
    public ResponseEntity<String> newGame(@RequestBody Map<String,String> body){
    	player1 = new Player(body.get("name"));
    	game = new Game(State.STARTED);
    	game.setPlayer1(player1);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Player 1 joined: " + player1.getName() + " " + "Game id : " + game.getGameId());
    }
	
	/* controller for joining the game */
	@PostMapping("/api/games/{id}/join")
	public ResponseEntity<String> joinGame( @PathVariable UUID id, @RequestBody Map<String,String> body){
		player2 = new Player(body.get("name"));
		
		if(!id.equals(game.getGameId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game ID was not found");
		}
		game.setPlayer2(player2);
		return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined:" + player2.getName());
	}
	
	/* controller for making the move */
	@PostMapping("/api/games/{id}/move")
	public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String,String> body) {
		
		if(!id.equals(game.getGameId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game ID was not found");
		}
		
		if(body.get("name").equals(player1.getName())) {
			player1.setMove(Move.valueOf(body.get("move")));
			return ResponseEntity.status(HttpStatus.OK).body("Player 1:" + player1.getName() + " made a move:" + player1.getMove().toString());
		} 
		else if(body.get("name").equals(player2.getName())) {
			player2.setMove(Move.valueOf(body.get("move")));
			return ResponseEntity.status(HttpStatus.OK).body("Player 2:" + player2.getName() + " made a move:" + player2.getMove().toString());
		} 
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player does not exist in the game");
		}
	}
	
	/* controller for checking the state */
	@GetMapping("/api/games/{id}")
	public ResponseEntity<String> checkState(@PathVariable UUID id) {
	    if(!id.equals(game.getGameId())) {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game Id was not found");
	    }
	    game.evaluateMove(player1, player2);
		return ResponseEntity.status(HttpStatus.OK).body(game.toString());
	}
	
	

}
