/* 
 * I pledge on my honor I have not given or received any 
 * unauthorized assistance on this assignment.
 * 
 * Name: 				Daniel Truong
 * UID: 				dtruong4
 * Directory ID Number: 117842166
 * Section Number: 0107
*/
/* This class was made so I could create and run threads.
 * */
package registrar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyThread implements Runnable {
	
	Registrar newRegistrar;
	String filename;
	private static Object lock = new Object();
	
	public MyThread(Registrar r, String s) {
		this.newRegistrar = r;
		this.filename = s;
	}

	// The run method reads the files it's given and calls 2 possible methods
	// based on their content.
	@Override
	public void run() {
		File myFile = new File(filename);
		Scanner reader;
		try {
			reader = new Scanner(myFile);
			// reads the file
			while (reader.hasNextLine()) {
				
				// reads each line in the file
				String b = reader.nextLine();
				// parses the line my removing the white space
				String[] array = b.split(" ");
				// if the line just read call addNewCourse()
				if (array[0].equals("addcourse")) {
					synchronized(lock) {
					String department = array[1];
					int classNum = Integer.valueOf(array[2]);
					int seats = Integer.valueOf(array[3]);
					newRegistrar.addNewCourse(department, classNum, seats);
					}
				} // if the line calls to add a student to the course
				else if (array[0].equals("addregistration")) {
					synchronized(lock) {
					String department = array[1];
					int classNum = Integer.valueOf(array[2]);
					String firstName = array[3];
					String lastName = array[4];
					newRegistrar.addToCourse(department, classNum, 
							firstName, lastName);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
