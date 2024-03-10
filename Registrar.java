/* 
 * I pledge on my honor I have not given or received any 
 * unauthorized assistance on this assignment.
 * 
 * Name: 				Daniel Truong
 * UID: 				dtruong4
 * Directory ID Number: 117842166
 * Section Number: 0107
*/
/* This class is the main class which holds all the methods. It creates a 
 * Registrar containing all the courses available for a school and all the
 * students that are enrolled in classes.
 * */
package registrar;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Registrar {
	
	private int maxCourses = 0;

	/**
	 * Registrar constructor
	 * The parameter sets the maximum amount of courses a student can take
	 * @param maxCoursesPerStudent
	 */
  public Registrar(int maxCoursesPerStudent) {
    if (maxCoursesPerStudent <= 0)
    	maxCourses = 1;
    else
    	maxCourses = maxCoursesPerStudent;
  }
  
  public Registrar() {
	  maxCourses = 5;
  }
  
  // holds the Courses as the key and a HashSet of Students as value
  private HashMap<Courses, HashSet<Student>> courseList = 
		  new HashMap<Courses, HashSet<Student>>();

	/**
	 * Adds a new course to courseList. No duplicate courses allowed
	 * @param department
	 * @param number
	 * @param numSeats
	 * @return the current Registrar after adding a new course with the info
	 * specified from the parameters
	 */

	public Registrar addNewCourse(String department, int number, int numSeats) {
		if (department.equals("") || number <= 0 || numSeats <= 0)
			throw new IllegalArgumentException();
		
		boolean duplicateClass = false;

		// creates a new Course to put into the array list
		Courses newCourse = new Courses(department, number, numSeats);
		
		HashSet<Student> studentSet = new HashSet<Student>();
		
		// figures out if there is a duplicate class
		for (Courses c : courseList.keySet()) {
			if (c.isName(department) && c.isNumber(number)) {
				duplicateClass = true;
			}
		}

		// if there is a duplicate then finish without adding class
		// if there isn't then add new class
		if (duplicateClass) {
			return this;
		} else {
			courseList.put(newCourse, studentSet);
			return this;
		}
		
	}
  

	/**
	 * 
	 * @param department
	 * @param number
	 * @return true if the course was successfully removed from courseList.
	 * False if the course didn't exist.
	 */
	public boolean cancelCourse(String department, int number) {
		if (department.equals("") || number <= 0)
			throw new IllegalArgumentException();
		
		// looks through the map keyset to see if the course is in courseList
		// if it is remove it and return true
		for (Courses clas : courseList.keySet()) {
			if (clas.isName(department) && clas.isNumber(number)) {
				courseList.remove(clas);
				return true;
			}
		}
		
		// returns false if it doesn't return true
		return false;
	}

	/**
	 * 
	 * @return the number of courses in courseList
	 */
	public int numCourses() {
		return courseList.size();
	}

	/**
	 * Adds a student to the course as directed by the parameters. The student
	 * can't be added if the class is full, if it doesn't exist, or the student
	 * has already taken their maximum allowed number of courses.
	 * @param department
	 * @param number
	 * @param firstName
	 * @param lastName
	 * @return True if the student is successfully added to the class. False if
	 * they couldn't be added to the class
	 */
	public boolean addToCourse(String department, int number, String firstName, 
			String lastName) {
		
		if (department.equals("") || number <= 0 || 
				firstName.equals("") || lastName.equals(""))
			throw new IllegalArgumentException();
		
		// find out how many courses the student is taking
		int amount = howManyCoursesTaking(firstName, lastName);
		Student newStudent = new Student(firstName, lastName);

		// adds the student to the course
		// loops through
		if (amount < maxCourses) {
			for (Courses clas : courseList.keySet()) {
				// when the correct course is found
				if (clas.isName(department) && clas.isNumber(number)) {
					// makes sure there are open seats
					if (clas.enoughSeats()) {
						if (courseList.get(clas) != null) {
							// checks if they're already in the course
							if (isInCourse(department, number, 
									firstName, lastName)) {
								return false;
							} else { // if not in the course add to course
								courseList.get(clas).add(newStudent);
								clas.incrementTakenSeats();
								return true;
							}
						}
					}
				}
			}
		}

		// if the course is full or it doesn't exist
		// or student has maxed out on courses or the course was out of seats
		return false;
	}
	

	/**
	 * Checks a specified course to check how many students it has currently
	 * @param department
	 * @param number
	 * @return the number of students currently in the course
	 */
	public int numStudentsInCourse(String department, int number) {
		if (department.equals("") || number <= 0)
			throw new IllegalArgumentException();
		
		// looks through the map keyset to find the course
		// if it's found return the value of the course
		for (Courses clas : courseList.keySet()) {
			if (clas.isName(department) && clas.isNumber(number)) {
				// returns the number of students in this course
				return courseList.get(clas).size();
			}
		}
		
		// if the course isn't found return -1
		return -1;
	}

	/**
	 * Finds and returns the number of students with a specified last name
	 * @param department
	 * @param number
	 * @param lastName
	 * @return the number of students in the course who possesses a matching 
	 * last name to the one given by the parameter
	 */
	public int numStudentsInCourseWithLastName(String department, int number, 
			String lastName) {
		if (department.equals("") || number <= 0 || lastName.equals(""))
			throw new IllegalArgumentException();
		
		if (courseList.size() == 0)
			return -1;

		int amount = 0;
		boolean exists = false;
		
		// looks through the map keyset to see if the course is in courseList
		for (Courses clas : courseList.keySet()) {
			if (clas.isName(department) && clas.isNumber(number)) {
				for (Student s : courseList.get(clas)) {
					if (s.isLastName(lastName)) {
						amount++;
						exists = true;
					}
				}
			}
		}
		
		// if the last name was found return amount else return -1
		if (exists)
			return amount;
		else
			return -1;
	}

	/**
	 * Finds and returns true or false whether a student is in a course
	 * @param department
	 * @param number
	 * @param firstName
	 * @param lastName
	 * @return True if the student is in the course. False if they aren't
	 */
	public boolean isInCourse(String department, int number, 
			String firstName, String lastName) {
		if (department.equals("") || number <= 0 || firstName.equals("") ||
				lastName.equals(""))
			throw new IllegalArgumentException();
		
		if (courseList.size() == 0)
			return false;
		
		// for each loops through courseList keyset to access the nameMap
		// if name is found return true
		for (Courses clas : courseList.keySet()) {
			// if the course is found
			if (clas.isName(department) && clas.isNumber(number)) {
		//		if (courseList.get(clas) != null) {
				// loops through the set of students to find matching student
				for (Student s : courseList.get(clas)) {
					// if student is found return true
					if (s.isLastName(lastName) && s.isFirstName(firstName))
						return true;
				}
			//}
		}
		}
		
		return false;
	}

	/**
	 * Finds out how many courses a student is taking
	 * @param firstName
	 * @param lastName
	 * @return the amount of courses taken by student firstName, lastName
	 */
	public int howManyCoursesTaking(String firstName, String lastName) {
		if (firstName.equals("") || lastName.equals(""))
			throw new IllegalArgumentException();
		
		if (courseList.size() == 0)
			return 0;
		
		int amount = 0;

		// looks through the map keyset to see if the course is in courseList
		for (Courses clas : courseList.keySet()) {
			// loops through the set of Students
			if (courseList.get(clas) != null) {
				for (Student s : courseList.get(clas)) {
					// if found the matching student
					if (s.isFirstName(firstName) && s.isLastName(lastName))
						amount++;
				}
			}
		}

		return amount;
	}

	/**
	 * Removes the student from the specified course
	 * @param department
	 * @param number
	 * @param firstName
	 * @param lastName
	 * @return True if the course is dropped. False if it isn't because 
	 * the student wasn't in the course in the first place.
	 */
	public boolean dropCourse(String department, int number, 
			String firstName, String lastName) {
		if (department.equals("") || number <= 0 || firstName.equals("") ||
				lastName.equals(""))
			throw new IllegalArgumentException();

		// if no classes have been added yet return false
		if (courseList.size() == 0) {
			return false;
		}
		
		// looks through the map keyset to see if the course is in courseList
		// if the course is found remove the student from it
		for (Courses clas : courseList.keySet()) {
			if (clas.isName(department) && clas.isNumber(number)) {
				// loops through the set of Students
				for (Student s : courseList.get(clas)) {
					// if found the student then remove them
					if (s.isLastName(lastName) && s.isFirstName(firstName)) {
						courseList.get(clas).remove(s);
						clas.decrementTakenSeats();
						return true;
					}
				}
			}
		}
		
		// if the course or student wasn't found return false
		return false;
	}

	/**
	 * Go through the courseList and remove the student from every class
	 * @param firstName
	 * @param lastName
	 * @return True if the student's registration was canceled. 
	 * False if they couldn't be
	 * removed because there was no registration to cancel.
	 */
	public boolean cancelRegistration(String firstName, String lastName) {
		if (firstName.equals("") || lastName.equals(""))
			throw new IllegalArgumentException();

		boolean found = false;
		
		// looks through all Courses
		// if the course is found remove the student from it
		for (Courses clas : courseList.keySet()) {
			// loops through the set of Students in each Course
			for (Student s : courseList.get(clas)) {
				// if found the student then remove them
				if (s.isLastName(lastName) && s.isFirstName(firstName)) {
					courseList.get(clas).remove(s);
					clas.decrementTakenSeats();
					found = true;
				}
			}
		}

		return found;
	}

	// creates and runs a bunch of threads which calls upon code
	// that reads the files and outputs the information in them
  public void doRegistrations(Collection<String> filenames) {
	  
	  // creates an array of threads big enough to run all files
	  Thread[] allThreads = new Thread[filenames.size()];
	  int i = 0;
	  
	  // for each loop that creates a new thread for each file
	  for (String fileName : filenames) {
		 allThreads[i] = new Thread(new MyThread(this, fileName));
		 i++;
	  }
	  
	  // runs through allThread array and starts all the threads
	  for (Thread t : allThreads) {
		  t.start();
	  }
	  // forces the threads to finish before doing anything else
	  for (Thread t : allThreads) {
		  try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	
  }

}
