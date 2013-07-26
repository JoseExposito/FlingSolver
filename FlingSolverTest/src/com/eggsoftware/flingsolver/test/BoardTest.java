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
package com.eggsoftware.flingsolver.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import android.test.AndroidTestCase;
import com.eggsoftware.flingsolver.Board;
import com.eggsoftware.flingsolver.Board.Direction;
import com.eggsoftware.flingsolver.SolutionStep;

/**
 * Tests for com.eggsoftware.flingsolver.Board
 */
public class BoardTest extends AndroidTestCase {

	public void testCanMove(){
		try {
			boolean[][] boardArray = new boolean[][] { 
					{true,  true,  false, true,  false, false, false},
					{true,  false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{true,  false, false, true,  false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, true } };
			Method method = Board.class.getDeclaredMethod("canMove", int.class, int.class, Board.Direction.class, boolean[][].class);
			method.setAccessible(true);

			assertFalse("null direction", (Boolean)method.invoke(null, 0, 0, null, boardArray));
			assertFalse("Out of range coordinate", (Boolean)method.invoke(null, 10, 0, Board.Direction.UP, boardArray));
			assertFalse("Out of range coordinate", (Boolean)method.invoke(null, 0, 10, Board.Direction.UP, boardArray));
			assertFalse("Out of range coordinate", (Boolean)method.invoke(null, 7, 8, Board.Direction.UP, boardArray));
			assertFalse("Not Fling! in coordinate", (Boolean)method.invoke(null, 1, 1, Board.Direction.UP, boardArray));

			assertFalse("Border Fling!", (Boolean)method.invoke(null, 0, 0, Board.Direction.UP, boardArray));
			assertFalse("Border Fling!", (Boolean)method.invoke(null, 7, 6, Board.Direction.DOWN, boardArray));
			assertFalse("Border Fling!", (Boolean)method.invoke(null, 0, 0, Board.Direction.LEFT, boardArray));
			assertFalse("Border Fling!", (Boolean)method.invoke(null, 7, 6, Board.Direction.RIGHT, boardArray));

			assertFalse("Stuck Fling!", (Boolean)method.invoke(null, 1, 0, Board.Direction.UP, boardArray));
			assertFalse("Stuck Fling!", (Boolean)method.invoke(null, 0, 0, Board.Direction.DOWN, boardArray));
			assertFalse("Stuck Fling!", (Boolean)method.invoke(null, 0, 1, Board.Direction.LEFT, boardArray));
			assertFalse("Stuck Fling!", (Boolean)method.invoke(null, 0, 0, Board.Direction.RIGHT, boardArray));

			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 2, 2, Board.Direction.UP, boardArray));
			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 2, 2, Board.Direction.DOWN, boardArray));
			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 2, 2, Board.Direction.LEFT, boardArray));
			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 2, 2, Board.Direction.RIGHT, boardArray));
			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 6, 3, Board.Direction.DOWN, boardArray));
			assertFalse("Not collide Fling!", (Boolean)method.invoke(null, 4, 5, Board.Direction.DOWN, boardArray));

			assertTrue("Can move Fling!", (Boolean)method.invoke(null, 3, 3, Board.Direction.UP, boardArray));
			assertTrue("Can move Fling!", (Boolean)method.invoke(null, 3, 3, Board.Direction.DOWN, boardArray));
			assertTrue("Can move Fling!", (Boolean)method.invoke(null, 3, 3, Board.Direction.LEFT, boardArray));
			assertTrue("Can move Fling!", (Boolean)method.invoke(null, 3, 3, Board.Direction.RIGHT, boardArray));
		} catch (Exception e) {
			fail();
		}
	}

	public void testApplyTransformation() {

		Method method = null;
		try {
			method = Board.class.getDeclaredMethod("applyTransformation", int.class, int.class, Board.Direction.class, boolean[][].class);
			method.setAccessible(true);

			boolean[][] boardArray = new boolean[][] { 
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, false, true,  false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, true,  true,  false, false, true } };

			// Basic UP
			assertTrue(Arrays.deepEquals((boolean[][])method.invoke(null, 3, 3, Board.Direction.UP, boardArray), new boolean[][] { 
					{false, false, false, false, false, false, false},
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, false, false, false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, true,  true,  false, false, true }
			}));

			// Basic DOWN
			assertTrue(Arrays.deepEquals((boolean[][])method.invoke(null, 3, 3, Board.Direction.DOWN, boardArray), new boolean[][] { 
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, false, false, false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, true,  false, false, false},
					{true,  false, true,  false, false, false, true }
			}));

			// Basic LEFT
			assertTrue(Arrays.deepEquals((boolean[][])method.invoke(null, 3, 3, Board.Direction.LEFT, boardArray), new boolean[][] { 
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, true,  false, false, false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, true,  true,  false, false, true }
			}));

			// Basic RIGHT
			assertTrue(Arrays.deepEquals((boolean[][])method.invoke(null, 3, 3, Board.Direction.RIGHT, boardArray), new boolean[][] { 
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, false, false, true,  false, false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, true,  true,  false, false, true }
			}));

			// Fling! cannon
			assertTrue(Arrays.deepEquals((boolean[][])method.invoke(null, 7, 0, Board.Direction.RIGHT, boardArray), new boolean[][] { 
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{true,  false, false, true,  false, true,  false},
					{false, false, false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, true,  true,  false, false, true,  false}
			}));
		} catch (Exception e) {
			fail();
		}
	}

	public void testSolveBoard() {
		// No solution
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{true,  false, false, false, false, false, false},
					{false, true,  false, false, false, false, false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, true,  false,  false},
					{false, false, true,  false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false}
				});
			assertTrue(solution == null);
		} catch (Exception e) {
			fail();
		}

		// Easy solution
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{true,  false, false, false, true,  false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, true,  false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false}
				});
			ArrayList<SolutionStep> expected = new ArrayList<SolutionStep>(2);
			expected.add(new SolutionStep(0, 0, Direction.RIGHT));
			expected.add(new SolutionStep(0, 3, Direction.DOWN));
			assertTrue(expected.equals(solution));

		} catch (Exception e) {
			fail();
		}
		 
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{true,  false, false, false, true,  false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, true,  false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false}
				});
			ArrayList<SolutionStep> expected = new ArrayList<SolutionStep>(2);
			expected.add(new SolutionStep(0, 4, Direction.LEFT));
			expected.add(new SolutionStep(0, 1, Direction.DOWN));
			assertTrue(expected.equals(solution));

		} catch (Exception e) {
			fail();
		}
		
		// Hard solution
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, false, false, false, false, false, false},
					{false, true,  false, true,  false, false, false},
					{true,  true,  false, false, false, false, false},
					{false, false, false, true,  true,  false, false},
					{true,  false, true,  false, true,  false, false},
					{false, false, false, true,  false, false, false}
			});
			
			ArrayList<SolutionStep> expected = new ArrayList<SolutionStep>(2);
			expected.add(new SolutionStep(6, 2, Direction.RIGHT));
			expected.add(new SolutionStep(6, 3, Direction.LEFT));
			expected.add(new SolutionStep(6, 1, Direction.UP));
			expected.add(new SolutionStep(5, 3, Direction.LEFT));
			expected.add(new SolutionStep(5, 2, Direction.RIGHT));
			expected.add(new SolutionStep(5, 3, Direction.DOWN));
			expected.add(new SolutionStep(6, 3, Direction.UP));
			expected.add(new SolutionStep(4, 1, Direction.RIGHT));
			expected.add(new SolutionStep(4, 0, Direction.RIGHT));
			assertTrue(expected.equals(solution));
		} catch (Exception e) {
			fail();
		}
		
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{false, false, false, false, false, false, false},
					{true,  false, false, false, false, true,  true },
					{false, false, false, false, false, false, true },
					{false, false, false, false, false, false, true },
					{false, false, true,  false, true,  false, false},
					{false, false, false, false, true,  true,  false},
					{true,  false, false, false, true,  false, false},
					{true,  false, false, false, false, false, false}
			});
			
			ArrayList<SolutionStep> expected = new ArrayList<SolutionStep>(2);
			expected.add(new SolutionStep(6, 0, Direction.RIGHT));
			expected.add(new SolutionStep(7, 0, Direction.UP));
			expected.add(new SolutionStep(2, 6, Direction.LEFT));
			expected.add(new SolutionStep(1, 6, Direction.DOWN));
			expected.add(new SolutionStep(2, 6, Direction.LEFT));
			expected.add(new SolutionStep(5, 5, Direction.UP));
			expected.add(new SolutionStep(2, 2, Direction.RIGHT));
			expected.add(new SolutionStep(4, 4, Direction.UP));
			expected.add(new SolutionStep(3, 4, Direction.DOWN));
			expected.add(new SolutionStep(4, 2, Direction.RIGHT));
			expected.add(new SolutionStep(4, 3, Direction.DOWN));
			assertTrue(expected.equals(solution));
		} catch (Exception e) {
			fail();
		}
		
		try {
			ArrayList<SolutionStep> solution = Board.solveBoard(new boolean[][] { 
					{false, false, false, false, false, false, false},
					{false, false, true,  true,  false, false, false},
					{false, false, true,  false, false, false, true },
					{false, false, true,  false, false, false, false},
					{true,  false, false, false, false, false, false},
					{false, true,  false, false, true,  false, false},
					{true,  true,  false, false, false, false, false},
					{false, false, false, false, false, true,  true }
			});
			
			ArrayList<SolutionStep> expected = new ArrayList<SolutionStep>(2);
			expected.add(new SolutionStep(7, 6, Direction.UP));
			expected.add(new SolutionStep(3, 2, Direction.RIGHT));
			expected.add(new SolutionStep(3, 5, Direction.DOWN));
			expected.add(new SolutionStep(6, 1, Direction.RIGHT));
			expected.add(new SolutionStep(6, 0, Direction.RIGHT));
			expected.add(new SolutionStep(6, 3, Direction.UP));
			expected.add(new SolutionStep(5, 1, Direction.RIGHT));
			expected.add(new SolutionStep(2, 3, Direction.DOWN));
			expected.add(new SolutionStep(4, 0, Direction.RIGHT));
			expected.add(new SolutionStep(2, 2, Direction.DOWN));
			expected.add(new SolutionStep(1, 2, Direction.DOWN));
			assertTrue(expected.equals(solution));
		} catch (Exception e) {
			fail();
		}
	}
}
