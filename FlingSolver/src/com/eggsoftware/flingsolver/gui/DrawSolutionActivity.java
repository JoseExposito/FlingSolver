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
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.eggsoftware.flingsolver.R;
import com.eggsoftware.flingsolver.solver.SolutionStep;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

/**
 * Activity with a ViewPager to present the solution of a Fling! level.
 */
public class DrawSolutionActivity extends Activity {

	/**
	 * Key to get the solution array from the Intent.
	 */
	public static final String SOLUTION_KEY = "solution_arraylist";
	
	/**
	 * Solution to draw. 
	 */
	private ArrayList<SolutionStep> solution;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_draw_solution);
	
		// Get the solution from the intent
		this.solution = this.getIntent().getExtras().getParcelableArrayList(SOLUTION_KEY);
		
		// Add the pages to the ViewPager
		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);  
        pager.setAdapter(new DrawSolutionPageAdapter(this, this.solution));
        
        // Initialize AdMob
	    AdView adView = (AdView) findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());
	}	
	
	/**
	 * Called when the help button is pressed.
	 */
	public void onHelpClicked(View solveLevelButton) {
		Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.swipe_instructions), Toast.LENGTH_SHORT).show();
	}
}
