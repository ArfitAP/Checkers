package com;

import java.util.LinkedList;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*BoardState board = new BoardState(5);
		
		board.boardTokens[0].move(2, 2);
		board.boardTokens[3].move(2, 0);
		
		System.out.println(board);
		
		for(BoardState nextmove : board.getNextStates() )
		{
			System.out.println(nextmove.toString() + " " + nextmove.movesHistory);
		}
		*/
		
		
		LinkedList<BoardState> queue = new LinkedList<BoardState>();
		queue.add(new BoardState(7));
		
		while (queue.size() != 0)
        {
		 	BoardState s = queue.poll();
		 	//System.out.println(s);
		 	
		 	if(s.isGameOver())
		 	{
		 		System.out.println("Game over in " + (s.movesHistory.split("=>", -1).length-1) + " moves");
		 		System.out.println("Borad state: " + s);
		 		System.out.println("Moves: " + s.movesHistory);
		 		return;
		 	}

            queue.addAll(s.getNextStates());
        }
        

	}

}
