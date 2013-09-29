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
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eggsoftware.flingsolver.solver.BoardSolver.Direction;

/**
 * Draws the Fling! board. Allows to edit the board if necessary allowing to the users to draw their own Fling! level to
 * solve.
 */
public class BoardCanvas extends View {

	/**
	 * Dimensions of the board.
	 */
	private static final int BOARD_NUM_COLUMNS = 7;
	private static final int BOARD_NUM_ROWS    = 8;
	
	/**
	 * Paints to draw the board, the Flings and the row.
	 */
	private Paint boardPaint;
	private Paint flingPaint;
	private Paint arrowPaint;

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
	private Rect rect = new Rect();
	Path path = new Path();
	
	/**
	 * To draw an arrow if necessary.
	 */
	private boolean drawArrow = false;
	private int arrowRow  = -1;
	private int arrowCol  = -1;
	private int arrowSize = -1;
	private Direction arrowDirection;
	
	/**
	 * Necessary constructors to be able to add this view to a XML activity.
	 */
	public BoardCanvas(Context context) {
		super(context);
		this.init();
	}

	public BoardCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }
    public BoardCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }
	
    /**
     * Real constructor.
     */
    private void init() {
    	// Initialize the painters
    	this.boardPaint = new Paint();
 		this.boardPaint.setColor(Color.GRAY);
 		
 		this.flingPaint = new Paint();
 		this.flingPaint.setColor(Color.BLACK);
 		
 		this.arrowPaint = new Paint();
 		this.arrowPaint.setColor(Color.BLACK);
 		this.arrowPaint.setStrokeWidth(5);
 		this.arrowPaint.setStyle(Paint.Style.FILL);
 		
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
	 * Returns or sets the drawn board.
	 */
	public boolean[][] getBoardRepresentation() {
		return this.board;
	}
	
	public void setBoardRepresentation(boolean[][] board) {
		this.board = board;
		this.invalidate();
	}
	
	/**
	 * Called BEFORE the board is painted. Sets the dimensions of the board. 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Calculate the size of the board
		this.squareSize = MeasureSpec.getSize(widthMeasureSpec) / BOARD_NUM_ROWS;
		this.leftMargin = (MeasureSpec.getSize(widthMeasureSpec) - squareSize*BOARD_NUM_COLUMNS) / 2;
		this.topMargin  = this.leftMargin;
		
		// Set the size of the board to full width and aspect ratio height
		int desiredWidth  = MeasureSpec.getSize(widthMeasureSpec);
		int desiredHeight = this.topMargin + (BOARD_NUM_ROWS * this.squareSize) + this.topMargin;
		
		this.setMeasuredDimension(desiredWidth, desiredHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);		
		
		// Draw the board
		for (int row=0; row<BOARD_NUM_ROWS; row++) {
			for (int col=0; col<BOARD_NUM_COLUMNS; col++) {
				this.drawSquare(canvas, row, col, this.board[row][col]);
			}
		}
		
		// Draw the arrow if any
		if (this.drawArrow && this.arrowSize != -1) {
			/*if (this.arrowDirection == Direction.UP) {
				canvas.drawLine(this.colToX(this.arrowCol), this.rowToY(this.arrowRow-1),
						this.colToX(this.arrowCol), this.rowToY(this.arrowRow-this.arrowSize+1),
						this.arrowPaint);

				path.moveTo(this.colToX(this.arrowCol), this.rowToY(this.arrowRow-this.arrowSize+1) - this.squareSize/2-5);
				path.lineTo(this.colToX(this.arrowCol)-this.squareSize/2-5, this.rowToY(this.arrowRow-this.arrowSize+1));
				path.lineTo(this.colToX(this.arrowCol)-this.squareSize/2+5, this.rowToY(this.arrowRow-this.arrowSize+1));
				path.lineTo(this.colToX(this.arrowCol), this.rowToY(this.arrowRow-this.arrowSize+1) - this.squareSize/2-5);
				path.close();
				canvas.drawPath(path, this.arrowPaint);
				
			} else if (this.arrowDirection == Direction.DOWN) {
				canvas.drawLine(this.colToX(this.arrowCol), this.rowToY(this.arrowRow+1),
						this.colToX(this.arrowCol), this.rowToY(this.arrowRow+this.arrowSize-1),
						this.arrowPaint);
				
			} else if (this.arrowDirection == Direction.LEFT) {
				canvas.drawLine(this.colToX(this.arrowCol-1), this.rowToY(this.arrowRow),
						this.colToX(this.arrowCol-this.arrowSize+1), this.rowToY(this.arrowRow),
						this.arrowPaint);
				
			} else if (this.arrowDirection == Direction.RIGHT) {
				canvas.drawLine(this.colToX(this.arrowCol+1), this.rowToY(this.arrowRow),
						this.colToX(this.arrowCol+this.arrowSize-1), this.rowToY(this.arrowRow),
						this.arrowPaint);
			}*/
		}
	}
	
	/**
	 * Draws a square with or without Fling!
	 */
	private void drawSquare(Canvas canvas, int row, int col, boolean haveFling) {
		float colToX = leftMargin + col * squareSize+squareSize/2;
		float rowToY = topMargin  + row * squareSize+squareSize/2;
		
		canvas.drawCircle(colToX, rowToY, squareSize/2-5, haveFling ? this.flingPaint : this.boardPaint);
	}
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		if (this.acceptEditFlings && action == MotionEvent.ACTION_DOWN) {
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

	/**
	 * Draws an arrow in the specified position and direction.
	 */
	public void drawArrow(int row, int col, Direction direction) {
		this.drawArrow = true;
		this.arrowRow = row;
		this.arrowCol = col;
		this.arrowDirection = direction;
		
		// Calculate the arrow size
		if (direction == Direction.UP) {
			for (int n=row-1; n>=0; n--) {
				if (this.board[n][col]) {
					this.arrowSize = row - n;
					break;
				}
			}
			
		} else if (direction == Direction.DOWN) {
			for (int n=row+1; n<BOARD_NUM_ROWS; n++) {
				if (this.board[n][col]) {
					this.arrowSize = n - row;
					break;
				}
			}
			
		} else if (direction == Direction.LEFT) {
			for (int n=col-1; n>=0; n--) {
				if (this.board[row][n]) {
					this.arrowSize = col - n;
					break;
				}
			}
			
		} else if (direction == Direction.RIGHT) {
			for (int n=col+1; n<BOARD_NUM_ROWS; n++) {
				if (this.board[row][n]) {
					this.arrowSize = n - col;
					break;
				}
			}
		}
		
		this.invalidate();
	}
	
}
