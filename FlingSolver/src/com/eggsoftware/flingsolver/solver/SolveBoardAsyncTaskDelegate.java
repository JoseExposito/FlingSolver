package com.eggsoftware.flingsolver.solver;

import java.util.ArrayList;

public interface SolveBoardAsyncTaskDelegate {

	/**
	 * Called when the task finish returning the result.
	 */
	public void boardSolved(SolveBoardAsyncTask task, ArrayList<SolutionStep> solution);
	
}
