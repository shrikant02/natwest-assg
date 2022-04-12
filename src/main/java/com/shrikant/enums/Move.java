package com.shrikant.enums;

public enum Move {
	
	ROCK {
		@Override
		public boolean winsOver(Move move) {
			// TODO Auto-generated method stub
			return (SCISSOR == move);
		}
	},
	PAPER {
		@Override
		public boolean winsOver(Move move) {
			// TODO Auto-generated method stub
			return (ROCK == move);
		}
	},
	SCISSOR {
		@Override
		public boolean winsOver(Move move) {
			// TODO Auto-generated method stub
			return (PAPER == move);
		}
	};
	
	public abstract boolean winsOver(Move move);

}
