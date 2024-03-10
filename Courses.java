/* 
 * I pledge on my honor I have not given or received any 
 * unauthorized assistance on this assignment.
 * 
 * Name: 				Daniel Truong
 * UID: 				dtruong4
 * Directory ID Number: 117842166
 * Section Number: 0107
*/
/* This class was created so I could easily group together 
 * the data and variables from this project. It was also the 
 * way I saw how to most easily manipulate said data and variables.
 * */
package registrar;

public class Courses {
	private String courseName; 
	private int courseNumber;
	private int numberOfSeats;
	private int filledSeats = 0;
	

	// constructor
	public Courses() {
		courseName = "";
		courseNumber = 0;
		numberOfSeats = 0;
	}
	// constructor
	public Courses(String courseName, int courseNumber, int numberOfSeats) {
		this.courseName = courseName;
		this.courseNumber = courseNumber;
		this.numberOfSeats = numberOfSeats;
	}
	
	// everything below is a getter or setter
	public boolean isName(String p) {
		if (courseName.equals(p))
			return true;
		return false;
	}
	
	public boolean isNumber(int p) {
		if (courseNumber == p)
			return true;
		return false;
	}

	public boolean enoughSeats() {
		if (filledSeats < numberOfSeats)
			return true;
		return false;
	}
	
	public void incrementTakenSeats() {
		filledSeats++;
	}
	
	public void decrementTakenSeats() {
		filledSeats--;
	}
	
	public String toString() {
		return courseName + " " + courseNumber;
	}
	

}
