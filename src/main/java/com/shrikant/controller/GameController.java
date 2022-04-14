package com.shrikant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	Game game;
	Map<String, List<Player>> gameMap = new HashMap<>();
	Map<String, String> playerMap = new HashMap<>();
	
	/* controller for creating new game */
	@PostMapping("/api/games")
    public ResponseEntity<String> newGame(@RequestBody Map<String,String> body){
    	Player player1 = new Player(body.get("name"));
        game = new Game(State.STARTED);
    	game.setPlayer1(player1);
    	List<Player> players = new ArrayList<>();
    	players.add(player1);
    	gameMap.put(game.getGameId().toString(), players );
    	System.out.print(gameMap);
    	return ResponseEntity.status(HttpStatus.CREATED).body("Player 1 joined: " + player1.getName() + " " + "Game id : " + game.getGameId());
    }
	
	/* controller for joining the game */
	@PostMapping("/api/games/{id}/join")
	public ResponseEntity<String> joinGame( @PathVariable UUID id, @RequestBody Map<String,String> body){
		Player player2 = new Player(body.get("name"));
		List<Player> players = gameMap.get(id.toString());
		game.setPlayer2(player2);
		players.add(player2);
		return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined:" + player2.getName());
	}
	
	/* controller for making the move */
	@PostMapping("/api/games/{id}/move")
	public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String,String> body) {
		boolean isPlayerFound = false;
		if(gameMap.containsKey(id.toString())) {
			
			List<Player> players = gameMap.get(id.toString());
			
			for (Player player : players) {
				if (player.getName().equals(body.get("name"))) {
					player.setMove(Move.valueOf(body.get("move")));
					isPlayerFound = true;
				} 
			}				
		}
		if(isPlayerFound) {
			return ResponseEntity.status(HttpStatus.OK).body("Player :" + body.get("name")+" has made the move");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player not found");
		}
		
	}
	
	/* controller for checking the state */
	@GetMapping("/api/games/{id}")
	public ResponseEntity<String> checkState(@PathVariable UUID id) {
		if(!gameMap.containsKey(id.toString())) {
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game Id was not found");
		}
		System.out.print(gameMap.get(id.toString()).get(0).getName());
		System.out.print(gameMap.get(id.toString()).get(1).getName());
		
		game.evaluateMove(gameMap.get(id.toString()).get(0), gameMap.get(id.toString()).get(1));
		return ResponseEntity.status(HttpStatus.OK).body(game.toString());

	}
	
	

}
