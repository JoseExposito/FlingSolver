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

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Simple asynchronous task to solve a board without freeze the user interface and show a loading animation.
 */
public class SolveBoardAsyncTask extends AsyncTask<Void, Void, ArrayList<SolutionStep>> {

	private SolveBoardAsyncTaskDelegate parentActivity;
	private boolean[][] boardToSolve;
	private ProgressDialog progressDialog;
	
	/**
	 * Default constructor.
	 * @param parentActivity The activity over show the ProgressDialog.  
	 * @param boardToSolve   The board to be solved.
	 */
	public SolveBoardAsyncTask(SolveBoardAsyncTaskDelegate parentActivity, boolean[][] boardToSolve) {
		super();
		this.parentActivity = parentActivity;
		this.boardToSolve = boardToSolve;
		
		this.progressDialog = new ProgressDialog((Context)this.parentActivity);
		this.progressDialog.setTitle("Loading...");
		this.progressDialog.setIndeterminate(true);
		this.progressDialog.setCancelable(true);
	}
	
	@Override
    protected void onPreExecute() {
		this.progressDialog.show();
    }
	
	@Override
	protected ArrayList<SolutionStep> doInBackground(Void... params) {
		return BoardSolver.solveBoard(this.boardToSolve);
	}
	
	@Override
    protected void onPostExecute(ArrayList<SolutionStep> result) {
        this.progressDialog.dismiss();
        this.parentActivity.boardSolved(this, result);
    }
}
