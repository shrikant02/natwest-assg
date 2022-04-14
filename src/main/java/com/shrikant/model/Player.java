package com.shrikant.model;

import com.shrikant.enums.Move;
import com.shrikant.enums.Result;

public class Player {
	
	private String name;
	private Move move;
	private Result result;
	
	public Player(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Move getMove() {
		return move;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	

}
