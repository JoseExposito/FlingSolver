/*
 * Fling! Solver
 * Copyright (C) 2013 - José Expósito <jose.exposito89@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.eggsoftware.flingsolver.solver;

import com.eggsoftware.flingsolver.solver.BoardSolver.Direction;

/**
 * Class to represent the solution of a board step by step.
 * Contains the row, column and direction of the Fling! to move.
 */
public class SolutionStep {
	private int row;
	private int col;
	private Direction direction;
	
	public SolutionStep(int row, int col, Direction direction) {
		this.row = row;
		this.col = col;
		this.direction = direction;
	}
	
	@Override
	public boolean equals(Object o) {
		SolutionStep other = (SolutionStep)o;
		return (this.row == other.getRow()) && (this.col == other.getCol()) && (this.direction == other.getDirection());
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
