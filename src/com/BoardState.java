package com;

import java.util.ArrayList;

public class BoardState {
	
	private int boardSize;
	public Token[] boardTokens; 
	public String movesHistory;
	
	public BoardState(int boardSize)
	{
		this.boardSize = boardSize;
		
		boardTokens = new Token[boardSize];
		
		for(int i = 0; i < this.boardSize; i++)
		{
			if(i%2 == 0)
			{
				boardTokens[i] = new Token(0, i);
			}
			else
			{
				boardTokens[i] = new Token(1, i);
			}
		}	
		
		movesHistory = "";
	}
	
	public BoardState(BoardState parent)
	{
		this.boardSize = parent.boardSize;
		boardTokens = new Token[boardSize];
		
		for(int i = 0; i < this.boardSize; i++)
		{
			boardTokens[i] = new Token(parent.boardTokens[i].row, parent.boardTokens[i].column);
		}	
		
		this.movesHistory = parent.movesHistory;
	}
	
	
	public boolean isGameOver()
	{
		boolean requiredFields[] = new boolean[boardSize];
		
		for(int i = 0; i < this.boardSize; i++)
		{
			requiredFields[i] = false;				
		}
		
		// all tokens
		for(int i = 0; i < this.boardSize; i++)
		{
			if(boardTokens[i].row == boardSize - 1 && boardTokens[i].column % 2 == 0)
			{
				requiredFields[boardTokens[i].column] = true;
			}
			else if(boardTokens[i].row == boardSize - 2 && boardTokens[i].column % 2 == 1)
			{
				requiredFields[boardTokens[i].column] = true;
			}
		}
		
		for(int i = 0; i < this.boardSize; i++)
		{
			if(requiredFields[i] == false)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	public ArrayList<BoardState> getNextStates()
	{
		ArrayList<BoardState> result = new ArrayList<BoardState>();
		
		// all tokens
		for(int i = 0; i < this.boardSize; i++)
		{
			Token current = boardTokens[i];
			
			// check move for 1
			boolean moveNW = ( current.row < boardSize - 1 && current.column < boardSize - 1 );
			for(int j = 0; j < this.boardSize; j++)
			{
				if(moveNW == false) break;
				if(j == i) continue;
				if(boardTokens[j].row == current.row + 1 && boardTokens[j].column == current.column + 1)
				{
					moveNW = false;
					break;
				}				
			}
			/*boolean moveSW = ( current.row > 0 && current.column < boardSize - 1 );
			for(int j = 0; j < this.boardSize; j++)
			{
				if(moveSW == false) break;
				if(j == i) continue;
				if(boardTokens[j].row == current.row - 1 && boardTokens[j].column == current.column + 1)
				{
					moveSW = false;
					break;
				}				
			}
			boolean moveSE = ( current.row > 0 && current.column > 0 );
			for(int j = 0; j < this.boardSize; j++)
			{
				if(moveSE == false) break;
				if(j == i) continue;
				if(boardTokens[j].row == current.row - 1 && boardTokens[j].column == current.column - 1)
				{
					moveSE = false;
					break;
				}				
			}*/
			boolean moveNE = ( current.row < boardSize - 1 && current.column > 0 );
			for(int j = 0; j < this.boardSize; j++)
			{
				if(moveNE == false) break;
				if(j == i) continue;
				if(boardTokens[j].row == current.row + 1 && boardTokens[j].column == current.column - 1)
				{
					moveNE = false;
					break;
				}				
			}
			
			if(moveNW == true)
			{
				BoardState tmp = new BoardState(this);
				tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row+1) + ", " + (tmp.boardTokens[i].column+1) + ")  ";
				tmp.boardTokens[i].move(1, 1);
				result.add(tmp);
			}
			/*if(moveSW == true)
			{
				BoardState tmp = new BoardState(this);
				tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row-1) + ", " + (tmp.boardTokens[i].column+1) + ")  ";
				tmp.boardTokens[i].move(-1, 1);
				result.add(tmp);
			}
			if(moveSE == true)
			{
				BoardState tmp = new BoardState(this);
				tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row-1) + ", " + (tmp.boardTokens[i].column-1) + ")  ";
				tmp.boardTokens[i].move(-1, -1);
				result.add(tmp);
			}*/
			if(moveNE == true)
			{
				BoardState tmp = new BoardState(this);
				tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row+1) + ", " + (tmp.boardTokens[i].column-1) + ")  ";
				tmp.boardTokens[i].move(1, -1);
				result.add(tmp);
			}
			
			
			//check for jumps
			
			// NW
			for(int k = 2; k < boardSize; k = k + 2)
			{
				boolean canNW = ( current.row < boardSize - k && current.column < boardSize - k );
				
				for(int h = 1; h < k; h = h + 2)
				{
					if(canNW == false) break;
					boolean isTokenThere = false;
					for(int j = 0; j < this.boardSize; j++)
					{						
						if(j == i) continue;
						if(boardTokens[j].row == current.row + h && boardTokens[j].column == current.column + h)
						{
							isTokenThere = true;
							break;
						}				
					}					
					if(isTokenThere == false)
					{
						canNW = false;
						break;
					}
				}
				
				
				for(int f = 2; f < k; f = f + 2)
				{
					if(canNW == false) break;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row + f && boardTokens[j].column == current.column + f)
						{
							canNW = false;
							break;
						}				
					}			
				}
				
				for(int j = 0; j < this.boardSize; j++)
				{
					if(j == i) continue;
					if(boardTokens[j].row == current.row + k && boardTokens[j].column == current.column + k)
					{
						canNW = false;
						break;
					}				
				}	
				
				if(canNW == true)
				{
					BoardState tmp = new BoardState(this);
					tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row+k) + ", " + (tmp.boardTokens[i].column+k) + ")  ";
					tmp.boardTokens[i].move(k, k);
					result.add(tmp);
				}
			}
			
			// SW
			/*
			for(int k = 2; k < boardSize; k = k + 2)
			{
				boolean canSW = ( current.row > k - 1 && current.column < boardSize - k );
				
				for(int h = 1; h < k; h = h + 2)
				{
					if(canSW == false) break;
					boolean isTokenThere = false;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row - h && boardTokens[j].column == current.column + h)
						{
							isTokenThere = true;
							break;
						}				
					}					
					if(isTokenThere == false)
					{
						canSW = false;
						break;
					}
				}
				
				for(int f = 2; f < k; f = f + 2)
				{
					if(canSW == false) break;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row - f && boardTokens[j].column == current.column + f)
						{
							canSW = false;
							break;
						}				
					}			
				}
				
				if(canSW == true)
				{
					BoardState tmp = new BoardState(this);
					tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row-k) + ", " + (tmp.boardTokens[i].column+k) + ")  ";
					tmp.boardTokens[i].move(-k, k);
					result.add(tmp);
				}
			}
		
			// SE
			for(int k = 2; k < boardSize; k = k + 2)
			{
				boolean canSE = ( current.row > k - 1 && current.column > k - 1 );
				
				for(int h = 1; h < k; h = h + 2)
				{
					if(canSE == false) break;
					boolean isTokenThere = false;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row - h && boardTokens[j].column == current.column - h)
						{
							isTokenThere = true;
							break;
						}				
					}					
					if(isTokenThere == false)
					{
						canSE = false;
						break;
					}
				}
				
				for(int f = 2; f < k; f = f + 2)
				{
					if(canSE == false) break;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row - f && boardTokens[j].column == current.column - f)
						{
							canSE = false;
							break;
						}				
					}			
				}
				
				if(canSE == true)
				{
					BoardState tmp = new BoardState(this);
					tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row-k) + ", " + (tmp.boardTokens[i].column-k) + ")  ";
					tmp.boardTokens[i].move(-k, -k);
					result.add(tmp);
				}
			}*/
			
			// NE
			for(int k = 2; k < boardSize; k = k + 2)
			{
				boolean canNE = ( current.row < boardSize - k && current.column > k - 1 );
				
				for(int h = 1; h < k; h = h + 2)
				{
					if(canNE == false) break;
					boolean isTokenThere = false;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row + h && boardTokens[j].column == current.column - h)
						{
							isTokenThere = true;
							break;
						}				
					}					
					if(isTokenThere == false)
					{
						canNE = false;
						break;
					}
				}
				
				for(int f = 2; f < k; f = f + 2)
				{
					if(canNE == false) break;
					for(int j = 0; j < this.boardSize; j++)
					{
						if(j == i) continue;
						if(boardTokens[j].row == current.row + f && boardTokens[j].column == current.column - f)
						{
							canNE = false;
							break;
						}				
					}			
				}
				
				for(int j = 0; j < this.boardSize; j++)
				{
					if(j == i) continue;
					if(boardTokens[j].row == current.row + k && boardTokens[j].column == current.column - k)
					{
						canNE = false;
						break;
					}				
				}	
				
				if(canNE == true)
				{
					BoardState tmp = new BoardState(this);
					tmp.movesHistory += "(" + tmp.boardTokens[i].row + ", " + tmp.boardTokens[i].column + ")=>(" + (tmp.boardTokens[i].row+k) + ", " + (tmp.boardTokens[i].column-k) + ")  ";
					tmp.boardTokens[i].move(k, -k);
					result.add(tmp);
				}
			}
			
		}	
		
		return result;
		
	}
	
	@Override
    public String toString() {
		String res = "";
		for(int j = 0; j < this.boardSize; j++)
		{
			res += "(" + this.boardTokens[j].row + ", " + this.boardTokens[j].column + ") ";			
		}	
        return res;
    }
	
	public class Token {
		public int row;
		public int column;
		
		public Token(int row, int column)
		{
			this.row = row;
			this.column = column;
		}
		
		public void move(int byRows, int byColumns)
		{
			this.row += byRows;
			this.column += byColumns;
		}
	}
	

}
