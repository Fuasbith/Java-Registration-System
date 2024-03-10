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

import java.util.Objects;

public class Student {
	
	private String firstName;
	private String lastName;
	
	public Student(String first, String last) {
		firstName = first;
		lastName = last;
	}
	
	public boolean isFirstName(String name) {
		if (firstName.equals(name))
			return true;
		return false;
	}
	
	public boolean isLastName(String name) {
		if (lastName.equals(name))
			return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(firstName, other.firstName) && 
				Objects.equals(lastName, other.lastName);
	}
	
}
