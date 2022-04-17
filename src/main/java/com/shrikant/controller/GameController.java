package com.shrikant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.shrikant.repository.GameRepository;

@RestController
public class GameController {

	@Autowired
	private GameRepository gameRepository;

	private List<Player> players = new ArrayList<>();

	Game game;

	/* controller for creating new game */
	@PostMapping("/api/games")
	public ResponseEntity<String> newGame(@RequestBody Map<String, String> body) {
		Player player1 = new Player(body.get("name"));
		game = new Game(State.STARTED);
		players.add(player1);
		gameRepository.save(game);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Player 1 joined: " + player1.getName() + " " + "Game id : " + game.getGameId());
	}

	/* controller for joining the game */
	@PostMapping("/api/games/{id}/join")
	public ResponseEntity<String> joinGame(@PathVariable UUID id, @RequestBody Map<String, String> body) {
		Player player2 = new Player(body.get("name"));
		if (players.size() <= 2) {
			players.add(player2);
		}
		game.setPlayers(players);
		gameRepository.save(game);
		return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined:" + player2.getName());
	}

	/* controller for making the move */
	@PostMapping("/api/games/{id}/move")
	public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String, String> body) {
		boolean isPlayerFound = false;
		if (game.getGameId().equals(id.toString())) {
			List<Player> players = game.getPlayers();

			for (Player player : players) {
				if (player.getName().equals(body.get("name"))) {
					player.setMove(Move.valueOf(body.get("move")));
					isPlayerFound = true;
				}
			}
		}
		if (isPlayerFound) {
			return ResponseEntity.status(HttpStatus.OK).body("Player :" + body.get("name") + " has made the move");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player not found");
		}
	}

	/* controller for checking the state */
	@GetMapping("/api/games/{id}")
	public ResponseEntity<String> checkState(@PathVariable UUID id) {
		/*if (game.getGameId().equals(id.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game Id was not found");
		} */
		System.out.print(game.getPlayers().get(0).getName());
		System.out.print(game.getPlayers().get(1).getName());

		game.evaluateMove(game.getPlayers().get(0), game.getPlayers().get(1));
		return ResponseEntity.status(HttpStatus.OK).body(game.toString());

	}
}
