package com.shrikant.controller;

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

	Game game;

	/* controller for creating new game */
	@PostMapping("/api/games")
	public ResponseEntity<String> newGame(@RequestBody Map<String, String> body) {
		Player player1 = new Player(body.get("name"));
		game = new Game(State.STARTED);
		game.setPlayer1(player1);
		gameRepository.save(game);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Player 1 joined: " + player1.getName() + " " + "Game id : " + game.getGameId());
	}

	/* controller for joining the game */
	@PostMapping("/api/games/{id}/join")
	public ResponseEntity<String> joinGame(@PathVariable UUID id, @RequestBody Map<String, String> body) {
		Player player2 = new Player(body.get("name"));
		game.setPlayer2(player2);
		gameRepository.save(game);
		return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined:" + player2.getName());
	}

	/* controller for making the move */
	@PostMapping("/api/games/{id}/move")
	public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String, String> body) {
		if (game.getGameId().toString().equals(id.toString())) {

			if (game.getPlayer1().getName().equals(body.get("name"))) {
				game.getPlayer1().setMove(Move.valueOf(body.get("move")));
				return ResponseEntity.status(HttpStatus.OK).body("Player :" + body.get("name") + " has made the move");
			} else if (game.getPlayer2().getName().equals(body.get("name"))) {
				game.getPlayer2().setMove(Move.valueOf(body.get("move")));
				return ResponseEntity.status(HttpStatus.OK).body("Player :" + body.get("name") + " has made the move");
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player not found");
	}

	/* controller for checking the state */
	@GetMapping("/api/games/{id}")
	public ResponseEntity<String> checkState(@PathVariable UUID id) {

		if (!game.getGameId().equals(id.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game Id was not found");
		}

		game.evaluateMove(game.getPlayer1(), game.getPlayer2());

		return ResponseEntity.status(HttpStatus.OK).body(game.toString());
	}

}
