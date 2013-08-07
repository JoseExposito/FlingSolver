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
package com.eggsoftware.flingsolver.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class BoardCanvas extends View {

	/**
	 * Dimensions of the board.
	 */
	private static final int BOARD_NUM_COLUMNS = 7;
	private static final int BOARD_NUM_ROWS    = 8;
	
	/**
	 * Paints to draw the board and the Flings.
	 */
	private Paint boardPaint;
	private Paint flingPaint;

	/**
	 * The board to draw.
	 */
	private boolean[][] board;

	/**
	 * Some values to draw the board.
	 */
	private int squareSize = -1;
	private int topMargin  = -1;
	private int leftMargin = -1;
	private boolean acceptEditFlings = false;
	
	
	/**
	 * Default constructor.
	 */
	public BoardCanvas(Context context) {
		super(context);
		
		// Initialize the painters
		this.boardPaint = new Paint();
		this.boardPaint.setColor(Color.BLACK);
		
		this.flingPaint = new Paint();
		this.flingPaint.setColor(Color.BLACK);
		
		// Initialize the board
		this.board = new boolean[BOARD_NUM_ROWS][BOARD_NUM_COLUMNS];
		for (int row=0; row<BOARD_NUM_ROWS; row++) {
			for (int col=0; col<BOARD_NUM_COLUMNS; col++) {
				this.board[row][col] = false;
			}
		}
	}
	
	/**
	 * To allow or not to edit the board by touching.
	 * By default it is false.
	 */
	public void setAcceptEditFlings(boolean acceptEditFlings) {
		this.acceptEditFlings = acceptEditFlings;
	}
	
	/**
	 * Returns the drawn board.
	 */
	public boolean[][] getBoardRepresentation() {
		return this.board;
	}
	
	/**
	 * Auxiliary function to calculate once the board margins and size. 
	 */
	private void calculateMargins() {
		this.squareSize = this.getWidth() / BOARD_NUM_ROWS;
		this.topMargin  = (this.getHeight() - squareSize*BOARD_NUM_ROWS) / 2;
		this.leftMargin = (this.getWidth() - squareSize*BOARD_NUM_COLUMNS) / 2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (squareSize == -1)
			this.calculateMargins();
		
		// Draw the board borders
		for (int n=0; n<=BOARD_NUM_COLUMNS; n++) {
			canvas.drawLine(n*squareSize+leftMargin, topMargin, 
					n*squareSize+leftMargin, squareSize*BOARD_NUM_ROWS+topMargin, 
					this.boardPaint);
		}
		
		for (int n=0; n<=BOARD_NUM_ROWS; n++) {
			canvas.drawLine(leftMargin, topMargin+n*squareSize,
					leftMargin+BOARD_NUM_COLUMNS*squareSize, topMargin+n*squareSize,
					this.boardPaint);
		}
		
		// Draw the flings
		for (int row=0; row<BOARD_NUM_ROWS; row++) {
			for (int col=0; col<BOARD_NUM_COLUMNS; col++) {
				if (this.board[row][col]) {
					canvas.drawCircle(leftMargin+col*squareSize+squareSize/2, topMargin+row*squareSize+squareSize/2, squareSize/2-5, this.flingPaint);
				}
			}
		}
	}
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		if (this.acceptEditFlings && action == MotionEvent.ACTION_DOWN) {
			if (squareSize == -1)
				this.calculateMargins();
			int touchedRow = (int)((event.getY() - this.topMargin) / this.squareSize);
			int touchedCol = (int)((event.getX() - this.leftMargin) / this.squareSize);
			
			if (touchedRow >= 0 && touchedCol >= 0 && touchedRow < BOARD_NUM_ROWS && touchedCol < BOARD_NUM_COLUMNS) {
				this.board[touchedRow][touchedCol] = !this.board[touchedRow][touchedCol];
				this.invalidate();
				return true;
			}
		}
		return false;
	}
	
}
