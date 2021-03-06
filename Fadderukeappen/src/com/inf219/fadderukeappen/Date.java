package com.inf219.fadderukeappen;
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

/**
 * Date contains information about day, month and year.
 * 
 * @author Marianne
 *
 */
public class Date implements Comparable<Date> {
	public final static int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private final static int MONTHS_IN_YEAR = 12;

	private int year;
	private int month;
	private int day;

	/**
	 * Constructor that sets the year, month and day of the date
	 * 
	 * @param year The date's year 
	 * @param month The date's month
	 * @param day The date's day number
	 */
	public Date(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 *  * Constructor that sets the year, month and day of the date
	 * 
	 * @param date The date given as a string on the format DD.MM.YYYY
	 */
	public Date(String date) {
		super();
		String[] parts = date.split("\\.");
		this.day = Integer.parseInt(parts[0]);
		this.month = Integer.parseInt(parts[1]);
		this.year = Integer.parseInt(parts[2]);

	}

	/**
	 * @return The year of the date
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the date
	 * 
	 * @param year The year of the date
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return The date's month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Sets the date's month
	 * 
	 * @param month The date's month number
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return The date's day number
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets the date's day number
	 * 
	 * @param day The date's day number
	 */
	public void setDay(int day) {
		this.day = day;
	}

	
	public String toString() {
		return String.format("%02d.%02d.%d", day, month, year);

	}

	@Override
	public int compareTo(Date otherDate) {
		if (this.getYear() != otherDate.getYear()) {
			if (this.getYear() > otherDate.getYear()) {
				return 1;
			} else {
				return -1;
			}
		} else {
			if (this.getMonth() != otherDate.getMonth()) {
				if (this.getMonth() > otherDate.getMonth()) {
					return 1;
				} else {
					return -1;
				}
			} else {
				if (this.getDay() == otherDate.getDay()) {
					return 0;
				} else if (this.getDay() > otherDate.getDay()) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Date)) {
			return false;
		} else {
			Date comp = (Date) o;
			return this.compareTo(comp) == 0;
		}

	}

	/**
	 * This function returns the following date after this date
	 * @return The next date
	 */
	public Date nextDate() {
		int day = getDay();
		int month = getMonth();
		int year = getYear();

		if (getDay() == DAYS_IN_MONTH[getMonth() - 1]) {
			day = 1;
			if (getMonth() == MONTHS_IN_YEAR) {
				month = 1;
				year++;
			} else {
				month++;
			}

		} else {
			day++;
		}

		return new Date(year, month, day);
	}

	/**
	 * This function returns the previous date, the date before this date
	 * @return The previous date
	 */
	public Date previousDate() {
		int day = getDay();
		int month = getMonth();
		int year = getYear();

		if (getDay() == 1) {
			day = DAYS_IN_MONTH[getMonth() - 1];
			if (getMonth() == 1) {
				month = MONTHS_IN_YEAR;
				year--;
			} else {
				month--;
			}

		} else {
			day--;
		}

		return new Date(year, month, day);
	}
}
