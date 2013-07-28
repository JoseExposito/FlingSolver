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
package com.eggsoftware.flingsolver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BoardCanvas extends View {

	/**
	 * Dimensions of the board.
	 */
	private static final int BOARD_NUM_COLUMNS = 7;
	private static final int BOARD_NUM_ROWS    = 8;
	
	private Paint boardBorderPaint;
	private Paint flingBorderPaint;
	private Paint flingBackgroundPaint;
	
	/**
	 * Default constructor.
	 */
	public BoardCanvas(Context context) {
		super(context);
		
		this.boardBorderPaint = new Paint();
		this.boardBorderPaint.setColor(Color.BLACK);
		
		this.boardBorderPaint = new Paint();
		this.boardBorderPaint.setColor(Color.BLACK);
		
		this.boardBorderPaint = new Paint();
		this.boardBorderPaint.setColor(Color.BLACK);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Draw the board borders
		int squareSize = this.getWidth() / BOARD_NUM_ROWS;
		int topMargin  = (this.getHeight() - squareSize*BOARD_NUM_ROWS) / 2;
		int leftMargin = (this.getWidth() - squareSize*BOARD_NUM_COLUMNS) / 2;
				
		for (int n=0; n<=BOARD_NUM_COLUMNS; n++) {
			canvas.drawLine(n*squareSize+leftMargin, topMargin, 
					n*squareSize+leftMargin, squareSize*BOARD_NUM_ROWS+topMargin, 
					this.boardBorderPaint);
		}
		
		for (int n=0; n<=BOARD_NUM_ROWS; n++) {
			canvas.drawLine(leftMargin, topMargin+n*squareSize,
					leftMargin+BOARD_NUM_COLUMNS*squareSize, topMargin+n*squareSize,
					this.boardBorderPaint);
		}
		
		// Draw the flings
		
	}
}
