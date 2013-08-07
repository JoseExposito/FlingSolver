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

import java.util.ArrayList;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.eggsoftware.flingsolver.R;
import com.eggsoftware.flingsolver.solver.SolutionStep;
import com.eggsoftware.flingsolver.solver.SolveBoardAsyncTask;
import com.eggsoftware.flingsolver.solver.SolveBoardAsyncTaskDelegate;

public class DrawLevelActivity extends Activity implements SolveBoardAsyncTaskDelegate {

	private BoardCanvas boardCanvas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_draw_level);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// Add an editable BoardCanvas
		this.boardCanvas = new BoardCanvas(this);
		this.boardCanvas.setAcceptEditFlings(true);
		FrameLayout layout = (FrameLayout)findViewById(R.id.frameLayout);
		layout.addView(this.boardCanvas);
	}
	
	/**
	 * Called when the solve button is pressed.
	 * Solves the level and shows the steps.
	 */
	public void onSolveLevelClicked(View solveLevelButton) {
		SolveBoardAsyncTask task = new SolveBoardAsyncTask(this, this.boardCanvas.getBoardRepresentation());
		try {
			task.execute();
		} catch (Exception e) {
			// TODO Show error
			e.printStackTrace();
		}
	}

	@Override
	public void boardSolved(SolveBoardAsyncTask task, ArrayList<SolutionStep> solution) {
		// TODO Show solution
		Log.d("", "Board solved :D");
	}
	
}
