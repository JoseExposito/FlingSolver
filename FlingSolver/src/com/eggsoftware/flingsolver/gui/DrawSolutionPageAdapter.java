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

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eggsoftware.flingsolver.solver.SolutionStep;

/**
 * The DrawSolutionActivity class uses a ViewPager to present the solution steps.
 * This class extends PagerAdapter to bring to this activity the different solution steps views.
 */
public class DrawSolutionPageAdapter extends PagerAdapter {

	private ArrayList<SolutionStep> solution;
	private Context context;  
	
	public DrawSolutionPageAdapter(Context context, ArrayList<SolutionStep> solution) {
		super();
		this.solution = solution;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return this.solution.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == (BoardCanvas)object);
	}

	@Override  
    public void destroyItem(View collection, int position, Object view) {  
        ((ViewPager) collection).removeView((BoardCanvas) view);
    }  
	
	@Override  
    public Object instantiateItem(View collection, int position) {        
        BoardCanvas board = new BoardCanvas(this.context);
        board.setBoardRepresentation(this.solution.get(position).getBoard());
        board.setArrow(this.solution.get(position).getRow(), this.solution.get(position).getCol(), this.solution.get(position).getDirection());
        ((ViewPager) collection).addView(board, 0);  
        return board;
    }
}
