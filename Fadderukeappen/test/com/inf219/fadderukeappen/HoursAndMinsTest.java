package com.inf219.fadderukeappen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.inf219.fadderukeappen.HoursAndMins;

/**
 * Copyright 2013 Marianne Grov and Johan Rusvik
 * 
 * This file is part of Fadderukeappen.
 * Fadderukeappen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Fadderukeappen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Fadderukeappen. If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class HoursAndMinsTest {

	private HoursAndMins testObject1;
	private HoursAndMins testObject2;
	@Before
	public void setUp() throws Exception {
		testObject1 = new HoursAndMins(5, 5);
		testObject2 = new HoursAndMins(10, 59);
		new HoursAndMins(5, 5);
	}

	@Test
	public void testToString() {
		for(int hours = 0; hours <= 24; hours++) {
			for(int mins = 0; mins < 60; mins++) {
				testObject1 = new HoursAndMins(hours, mins);
				if(hours == 24) {
					assertEquals(testObject1.toString(),String.format("%02d:%02d", 0, mins));
				} else {
					assertEquals(testObject1.toString(),String.format("%02d:%02d", hours, mins));
				}
			}
		}
	}

	@Test
	public void testCompareTo() {
		for(int hours = 0; hours <= 24; hours++) {
			for(int mins = 0; mins < 60; mins++) {

				for(int hours2 = 0; hours2 <= 24; hours2++) {
					for(int mins2 = 0; mins2 < 60; mins2++) {
						testObject1 = new HoursAndMins(hours, mins);
						testObject2 = new HoursAndMins(hours2, mins2);

						if(hours > hours2) {
							assertEquals(testObject1.compareTo(testObject2), 1);
						} else if(hours < hours2) {
							assertEquals(testObject1.compareTo(testObject2), -1);
						} else if (mins < mins2) {
							assertEquals(testObject1.compareTo(testObject2), -1);
						} else if (mins > mins2) {
							assertEquals(testObject1.compareTo(testObject2), 1);
						} else if (hours == hours2 && mins == mins2) {
							assertEquals(hours, hours2);
							assertEquals(mins, mins2);
							assertEquals(testObject1.toString(), testObject2.toString());
							assertEquals(testObject1.compareTo(testObject2), 0);
						} else {

						}


					}

				}

			}

		}
	}


	@Test
	public void testEquals() {
		for(int hours = 0; hours <= 24; hours++) {
			for(int mins = 0; mins < 60; mins++) {

				for(int hours2 = 0; hours2 <= 24; hours2++) {
					for(int mins2 = 0; mins2 < 60; mins2++) {
						testObject1 = new HoursAndMins(hours, mins);
						testObject2 = new HoursAndMins(hours2, mins2);

						if (hours == hours2 && mins == mins2) {
							assertEquals(testObject1, testObject2);

						} else {
							assertNotEquals(testObject1, testObject2);
						}

					}

				}

			}

		}
	}

	@Test
	public void testCalculateEndTime() {
		for(int hours = 0; hours <= 24; hours++) {
			for(int mins = 0; mins < 60; mins++) {

				for(int hours2 = 0; hours2 <= 24; hours2++) {
					for(int mins2 = 0; mins2 < 60; mins2++) {
						testObject1 = new HoursAndMins(hours, mins);
						testObject2 = new HoursAndMins(hours2, mins2);

						if(hours + hours2 < 24 &&  mins + mins2 < 60)
							new HoursAndMins(hours + hours2, mins + mins2);
						else if (hours + hours2 >= 24 && mins + mins2 >= 60 ) {
							new HoursAndMins(24 - hours + hours2 + 1, 60 - (mins + mins2));
						} else if (hours + hours2 >= 24)
							new HoursAndMins(24 - hours + hours2, mins + mins2);
						else if (mins + mins2 >= 60 && hours + hours2 >= 23)
							new HoursAndMins(0, 60 - (mins + mins2));

					}
				}
			}
		}

	}

	@Test
	public void testConvertToHoursAndMins() {
		for(int hours = 0; hours <= 24; hours++) {
			for(int mins = 0; mins < 60; mins++) {
				testObject1 = new HoursAndMins(hours, mins);
				assertEquals(HoursAndMins.convertToHoursAndMins(String.format("%02d:%02d", hours, mins)), testObject1);
			}
		}
	}
}
