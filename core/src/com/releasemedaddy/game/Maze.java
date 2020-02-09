package com.releasemedaddy.game;

import java.util.Random;

public class Maze
{
	private int[][] retMatrix, timMatrix;
	private boolean[][] vstMatrix;
	private int[] rowAdd = {0, 0, 1, -1};
	private int[] colAdd = {-1, 1, 0, 0};
	private int arrayRow, arrayCol;
	private Random rand;
	private int curRow, curCol;
	private int targetRow, targetCol;
	private boolean ended = false;
	private boolean won = false;

	public Maze(int width, int length, long seed)
	{
		super();
		this.retMatrix = new int[width * 2 + 1][length * 2 + 1];
		this.vstMatrix = new boolean[width * 2 + 1][length * 2 + 1];
		this.timMatrix = new int[width * 2 + 1][length * 2 + 1];
		this.arrayRow = width * 2 + 1;
		this.arrayCol = length * 2 + 1;
		for(int i = 0; i < width * 2 + 1; i++)
			for(int j = 0; j < length * 2 + 1; j++)
			{
				this.retMatrix[i][j] = 1;
				this.vstMatrix[i][j] = false;
			}
		
		for(int i = 1; i < width * 2 + 1; i += 2)
		{
			for(int j = 1; j < length * 2 + 1; j += 2)
			{
				this.retMatrix[i][j] = 0;
			}
		}
		this.rand = new Random(seed);
		int row = (rand.nextInt(width)) * 2 + 1;
		int col = (rand.nextInt(length)) * 2 + 1;
		timMatrix[row][col] = 0;
		dfs(row, col);
		retMatrix[row][col] = 2;
		int maxRow = 0, maxCol = 0;
		int maxDepth = 0;
		for(int i = 1; i < width * 2 + 1; i += 2)
		{
			for(int j = 1; j < length * 2 + 1; j += 2)
			{
				if(maxDepth < timMatrix[i][j])
				{
					maxRow = i;
					maxCol = j;
					maxDepth = timMatrix[i][j];
				}
			}
		}
		retMatrix[maxRow][maxCol] = 3;
		this.curRow = row;
		this.curCol = col;
		int[][] newRetMatrix = new int[this.arrayRow / 2 * 4 + 1][this.arrayCol / 2 * 4 + 1];
		for(int i = 0; i < this.arrayRow; i++)
		{
			for(int j = 0; j < this.arrayCol; j++)
			{
				if(i % 2 == 0 && j % 2 == 0)
				{
					newRetMatrix[i * 2][j * 2] = 1;
				}
				if(i % 2 == 0 && j % 2 == 1)
				{
					if(retMatrix[i][j] == 1)
					{
						newRetMatrix[i * 2][j / 2 * 4 + 1] = 1;
						newRetMatrix[i * 2][j / 2 * 4 + 2] = 1;
						newRetMatrix[i * 2][j / 2 * 4 + 3] = 1;
					}
					else
					{
						int k = rand.nextInt(3) + 1;
						newRetMatrix[i * 2][j / 2 * 4 + 1] = rand.nextInt(2);
						newRetMatrix[i * 2][j / 2 * 4 + 2] = rand.nextInt(2);
						newRetMatrix[i * 2][j / 2 * 4 + 3] = rand.nextInt(2);
						newRetMatrix[i * 2][j / 2 * 4 + k] = 0;
					}
				}
				if(i % 2 == 1 && j % 2 == 0)
				{
					if(retMatrix[i][j] == 1)
					{
						newRetMatrix[i / 2 * 4 + 1][j * 2] = 1;
						newRetMatrix[i / 2 * 4 + 2][j * 2] = 1;
						newRetMatrix[i / 2 * 4 + 3][j * 2] = 1;
					}
					else
					{
						int k = rand.nextInt(3) + 1;
						newRetMatrix[i / 2 * 4 + 1][j * 2] = rand.nextInt(2);
						newRetMatrix[i / 2 * 4 + 2][j * 2] = rand.nextInt(2);
						newRetMatrix[i / 2 * 4 + 3][j * 2] = rand.nextInt(2);
						newRetMatrix[i / 2 * 4 + k][j * 2] = 0;
					}
				}
				if(i % 2 == 1 && j % 2 == 1)
				{
					newRetMatrix[i / 2 * 4 + 1][j / 2 * 4 + 1] = 0;
					newRetMatrix[i / 2 * 4 + 1][j / 2 * 4 + 2] = 0;
					newRetMatrix[i / 2 * 4 + 1][j / 2 * 4 + 3] = 0;
					newRetMatrix[i / 2 * 4 + 2][j / 2 * 4 + 1] = 0;
					newRetMatrix[i / 2 * 4 + 2][j / 2 * 4 + 2] = 0;
					newRetMatrix[i / 2 * 4 + 2][j / 2 * 4 + 3] = 0;
					newRetMatrix[i / 2 * 4 + 3][j / 2 * 4 + 1] = 0;
					newRetMatrix[i / 2 * 4 + 3][j / 2 * 4 + 2] = 0;
					newRetMatrix[i / 2 * 4 + 3][j / 2 * 4 + 3] = 0;
				}
			}
		}
		newRetMatrix[maxRow * 2][maxCol * 2] = 3;
		newRetMatrix[curRow * 2][curCol * 2] = 2;
		curRow *= 2;
		curCol *= 2;
		this.targetRow = maxRow * 2;
		this.targetCol = maxCol * 2;
		this.retMatrix = newRetMatrix;
		this.arrayRow = this.arrayRow / 2 * 4 + 1;
		this.arrayCol = this.arrayCol / 2 * 4 + 1;
	}

	public int getCurRow()
	{
		return this.curRow;
	}

	public int getCurCol()
	{
		return this.curCol;
	}

	public int getTargetRow()
	{
		return this.targetRow;
	}

	public int getTargetCol()
	{
		return this.targetCol;
	}

	public boolean isWon()
	{
		return this.won;
	}

	public boolean isEnded()
	{
		return this.ended;
	}

	public void setEnded() { this.ended = true;}

	public int[][] getMaze()
	{
		return this.retMatrix;
	}

	public int getArrayRow()
	{
		return this.arrayRow;
	}

	public int getArrayCol()
	{
		return this.arrayCol;
	}

	public void move(int ind)
	{
		if(this.ended) 
			return ;
		if(this.retMatrix[curRow + rowAdd[ind]][curCol + colAdd[ind]] == 3)
		{
			this.retMatrix[curRow + rowAdd[ind]][curCol + colAdd[ind]] = 2;
			this.retMatrix[curRow][curCol] = 0;
			curRow += rowAdd[ind];
			curCol += colAdd[ind];
			this.ended = true;
			this.won = true;
			return ;
		}
		if(this.retMatrix[curRow + rowAdd[ind]][curCol + colAdd[ind]] == 0)
		{
			this.retMatrix[curRow + rowAdd[ind]][curCol + colAdd[ind]] = 2;
			this.retMatrix[curRow][curCol] = 0;
			curRow += rowAdd[ind];
			curCol += colAdd[ind];
			return ;
		}
	}

	public boolean checkInBound(int x, int y)
	{
		return (0 <= x && x < arrayRow && y >= 0 && y < arrayCol);
	}

	public void dfs(int row, int col)
	{
		vstMatrix[row][col] = true;
		int[] ind = {0, 1, 2, 3};
		for(int i = 0; i < 4; i++)
		{
			int k = rand.nextInt(4 - i);
			int tg = ind[i + k];
			ind[i + k] = ind[i];
			ind[i] = tg;
		}
		for(int i = 0; i < 4; i ++)
		{
			int k = ind[i];
			if(checkInBound(row + 2 * rowAdd[k], col + 2 * colAdd[k]) && !vstMatrix[row + 2 * rowAdd[k]][col + 2 * colAdd[k]])
			{
				int x = row + 2 * rowAdd[k];
				int y = col + 2 * colAdd[k];
				retMatrix[x - rowAdd[k]][y - colAdd[k]] = 0;
				timMatrix[x][y] = timMatrix[row][col] + 1;
				dfs(x, y);
			}
		}
	}

	public int getCode(int x, int y)
	{
		int ans = 0;
		if(retMatrix[x - 1][y] % 2 != 1) ans += 1;
		if(retMatrix[x][y + 1] % 2 != 1) ans += 2;
		if(retMatrix[x + 1][y] % 2 != 1) ans += 4;
		if(retMatrix[x][y - 1] % 2 != 1) ans += 8;
		return ans;

	}

}