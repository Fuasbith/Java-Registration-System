package tests;

import org.junit.*;

import registrar.Registrar;
import registrar.Student;

import static org.junit.Assert.*;

import java.util.HashSet;

public class StudentTests {

  /* Remove this comment and write your JUnit student tests in this class.
   *
   * Note that your student tests must be your OWN WORK.  Copying the public
   * tests and turning them in as your student tests (with minimal or no
   * changes) would be plagiarism, and sharing student tests, or working
   * together to write them, is prohibited.
   */


	  // Tests calling numCourses() on a newly-instantiated Registrar with no
	  // courses, and after one course has been added using addNewCourse().
	  @Test public void testPublic1() {
	    Registrar registrar= new Registrar();

	    assertEquals(0, registrar.numCourses());

	    registrar.addNewCourse("CMSC", 132, 10);
	    assertEquals(1, registrar.numCourses());
	  }

	  // Tests that addNewCourse() can add different courses to different
	  // departments in a Registrar, by calling numCourses() after several
	  // courses, in different departments, have been added to a Registrar.
	  @Test public void testPublic2() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 131, 10);
	    registrar.addNewCourse("MATH", 132, 10);
	    registrar.addNewCourse("BIOE", 250, 10);
	    registrar.addNewCourse("ENGL", 101, 10);
	    registrar.addNewCourse("PSYC", 100, 8);
	    registrar.addNewCourse("COMM", 184, 7);
	    registrar.addNewCourse("ENEE", 115, 80);
	    registrar.addNewCourse("KNES", 217, 30);

	    assertEquals(8, registrar.numCourses());
	  }

	  // Tests that addNewCourse() properly returns a reference to its current
	  // object, by making chained calls to it.
	  @Test public void testPublic3() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 131, 10).addNewCourse("MATH", 132, 20);
	    registrar.addNewCourse("BIOE", 250, 15).addNewCourse("ENGL", 101, 30);
	    registrar.addNewCourse("PSYC", 100, 5);

	    assertEquals(5, registrar.numCourses());
	  }

	  // Tests that trying to call addNewCourse() to add an already-existing
	  // course does not have an effect (does not change the number of courses
	  // that are in the Registrar).
	  @Test public void testPublic4() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("CMSC", 132, 11);
	    assertEquals(1, registrar.numCourses());
	  }

	  // Tests that cancelCourse() removes a course from a Registrar (using
	  // numCourses()), and returns true.
	  @Test public void testPublic5() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 140, 20);
	    assertEquals(2, registrar.numCourses());

	    assertTrue(registrar.cancelCourse("MATH", 140));
	    assertEquals(1, registrar.numCourses());
	  }

	  // Tests that addToCourse() returns true when a student is successfully
	  // added to a course, but false when trying to add a student more than
	  // once to the same course.
	  @Test public void testPublic6() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);

	    assertTrue(registrar.addToCourse("PSYC", 100, "June", "Junior"));
	    assertFalse(registrar.addToCourse("PSYC", 100, "June", "Junior"));
	    assertEquals(1, registrar.numStudentsInCourse("PSYC", 100));
	  }

	  // Tests calling addToCourse() to try to add a student to too many
	  // courses; it should return false, and not add the student to more than
	  // five courses.
	  @Test public void testPublic7() {
	    Registrar registrar= new Registrar();
	    
	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);
	    registrar.addNewCourse("HIST", 101, 10);
	    registrar.addNewCourse("ECON", 105, 10);
	    registrar.addNewCourse("SOCY", 102, 10);
	    
	    registrar.addToCourse("CMSC", 132, "Freddy", "Freshman");
	    registrar.addToCourse("MATH", 141, "Freddy", "Freshman");
	    registrar.addToCourse("PSYC", 100, "Freddy", "Freshman");
	    registrar.addToCourse("HIST", 101, "Freddy", "Freshman");
	    registrar.addToCourse("ECON", 105, "Freddy", "Freshman");

	    assertFalse(registrar.addToCourse("SOCY", 102, "Freddy", "Freshman"));

	    // Hua did not get added to SOCY 102
	    assertEquals(0, registrar.numStudentsInCourse("SOCY", 102));
	  }

	  // Tests calling addToCourse() to try to add more students to a course
	  // than its capacity; it should return false, and not add students beyond
	  // the capacity.
	  @Test public void testPublic8() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("KNES", 321, 3);

	    registrar.addToCourse("KNES", 321, "Ellie", "Elephant");
	    registrar.addToCourse("KNES", 321, "Hermione", "Heron");
	    // after this call the course will be at capacity
	    registrar.addToCourse("KNES", 321, "Sally", "Salamander");
	    // this would put the course over capacity
	    registrar.addToCourse("KNES", 321, "Peggy", "Penguin");

	    assertEquals(3, registrar.numStudentsInCourse("KNES", 321));
	  }

	  // Tests that addNewCourse() will not create courses with invalid numbers
	  // or seat counts.
	  @Test public void testPublic9() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("BIOE", 222, 0);
	    registrar.addNewCourse("PHIL", 333, -4);

	    assertEquals(0, registrar.numCourses());

	    assertFalse(registrar.addToCourse("BIOE", 222, "Dolly", "Dolphin"));
	    assertFalse(registrar.addToCourse("PHIL", 333, "Kori", "Koala"));

	    assertEquals(-1, registrar.numStudentsInCourse("BIOE", 222));
	    assertEquals(-1, registrar.numStudentsInCourse("PHIL", 333));
	  }

	  // Tests isInCourse() in a few cases where it should return true.
	  @Test public void testPublic10() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("GEOG", 174, 2);
	    registrar.addNewCourse("JAPN", 276, 4);
	    registrar.addNewCourse("GEMS", 435, 5);

	    registrar.addToCourse("GEOG", 174, "Sheena", "Sheep");

	    registrar.addToCourse("GEOG", 174, "Ryan", "Lion");
	    registrar.addToCourse("JAPN", 276, "Ryan", "Lion");
	    registrar.addToCourse("GEMS", 435, "Ryan", "Lion");

	    registrar.addToCourse("GEMS", 435, "Robyn", "Robin");
	    registrar.addToCourse("GEMS", 435, "Horace", "Horse");
	    registrar.addToCourse("GEMS", 435, "Cammy", "Camel");

	    assertTrue(registrar.isInCourse("GEOG", 174, "Sheena", "Sheep"));
	    assertTrue(registrar.isInCourse("GEOG", 174, "Ryan", "Lion"));
	    assertTrue(registrar.isInCourse("JAPN", 276, "Ryan", "Lion"));
	    assertTrue(registrar.isInCourse("GEMS", 435, "Ryan", "Lion"));
	    assertTrue(registrar.isInCourse("GEMS", 435, "Robyn", "Robin"));
	    assertTrue(registrar.isInCourse("GEMS", 435, "Cammy", "Camel"));
	    assertTrue(registrar.isInCourse("GEMS", 435, "Horace", "Horse"));
	  }

	  // Tests howManyCoursesTaking() in a few cases.
	  @Test public void testPublic11() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("GEOG", 174, 2);
	    registrar.addNewCourse("JAPN", 276, 4);
	    registrar.addNewCourse("GEMS", 435, 5);

	    registrar.addToCourse("GEOG", 174, "Ryan", "Lion");
	    registrar.addToCourse("JAPN", 276, "Ryan", "Lion");
	    registrar.addToCourse("GEMS", 435, "Ryan", "Lion");
	    registrar.addToCourse("GEMS", 435, "Robyn", "Robin");
	    registrar.addToCourse("JAPN", 276, "Robyn", "Robin");
	    registrar.addToCourse("GEMS", 435, "Horace", "Horse");

	    assertEquals(1, registrar.howManyCoursesTaking("Horace", "Horse"));
	    assertEquals(2, registrar.howManyCoursesTaking("Robyn", "Robin"));
	    assertEquals(3, registrar.howManyCoursesTaking("Ryan", "Lion"));
	  }

	  // Tests calling dropCourse() on a student in a course, verifies that it
	  // returns true, and ensures that the number of students in the course
	  // decreases by 1.
	  @Test public void testPublic12() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);
	    registrar.addNewCourse("HIST", 101, 10);
	    registrar.addNewCourse("ECON", 105, 10);

	    registrar.addToCourse("CMSC", 132, "Pooja", "Poodle");
	    registrar.addToCourse("MATH", 141, "Pooja", "Poodle");
	    registrar.addToCourse("PSYC", 100, "Pooja", "Poodle");
	    registrar.addToCourse("HIST", 101, "Pooja", "Poodle");
	    registrar.addToCourse("ECON", 105, "Pooja", "Poodle");
	    registrar.addToCourse("CMSC", 132, "Lemuel", "Lemur");
	    registrar.addToCourse("MATH", 141, "Lemuel", "Lemur");
	    registrar.addToCourse("PSYC", 100, "Lemuel", "Lemur");

	    registrar.dropCourse("MATH", 141, "Pooja", "Poodle");

	    assertEquals(1, registrar.numStudentsInCourse("MATH", 141));
	  }

	  // Tests calling dropCourse() on a student who is registered for five
	  // courses, and checks that they are able to be added to a new course
	  // after that.
	  @Test public void testPublic13() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);
	    registrar.addNewCourse("HIST", 101, 10);
	    registrar.addNewCourse("ECON", 105, 10);
	    registrar.addNewCourse("SOCY", 102, 10);

	    registrar.addToCourse("CMSC", 132, "Amy", "Amoeba");
	    registrar.addToCourse("MATH", 141, "Amy", "Amoeba");
	    registrar.addToCourse("PSYC", 100, "Amy", "Amoeba");
	    registrar.addToCourse("HIST", 101, "Amy", "Amoeba");
	    registrar.addToCourse("ECON", 105, "Amy", "Amoeba");

	    registrar.dropCourse("HIST", 101, "Amy", "Amoeba");

	    assertTrue(registrar.addToCourse("SOCY", 102, "Amy", "Amoeba"));
	    assertEquals(1, registrar.numStudentsInCourse("SOCY", 102));
	  }

	  // Tests cancelRegistration() by verifying that the number of students in
	  // all of the student's courses is reduced by 1 after it's called.
	  @Test public void testPublic14() {
	    Registrar registrar= new Registrar();

	    registrar.cancelRegistration("Polly", "Possum");
	    
	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);
	    registrar.addNewCourse("HIST", 101, 10);
	    registrar.addNewCourse("ECON", 105, 10);
	    registrar.addNewCourse("SOCY", 102, 10);

	    registrar.addToCourse("CMSC", 132, "Polly", "Possum");
	    registrar.addToCourse("MATH", 141, "Polly", "Possum");
	    registrar.addToCourse("PSYC", 100, "Polly", "Possum");
	    registrar.addToCourse("HIST", 101, "Polly", "Possum");
	    registrar.addToCourse("ECON", 105, "Polly", "Possum");

	    assertTrue(registrar.cancelRegistration("Polly", "Possum"));

	    assertEquals(0, registrar.numStudentsInCourse("CMSC", 132));
	    assertEquals(0, registrar.numStudentsInCourse("MATH", 141));
	    assertEquals(0, registrar.numStudentsInCourse("PSYC", 100));
	    assertEquals(0, registrar.numStudentsInCourse("HIST", 101));
	    assertEquals(0, registrar.numStudentsInCourse("ECON", 105));
	    assertEquals(0, registrar.numStudentsInCourse("SOCY", 102));
	  }

	  // Tests calling cancelRegistration() twice on a student- the first call
	  // should remove them, while the second call should not have any effect.
	  @Test public void testPublic15() {
	    Registrar registrar= new Registrar();

	    registrar.addNewCourse("CMSC", 132, 10);
	    registrar.addNewCourse("MATH", 141, 10);
	    registrar.addNewCourse("PSYC", 100, 10);
	    registrar.addNewCourse("HIST", 101, 10);
	    registrar.addNewCourse("ECON", 105, 10);

	    registrar.addToCourse("CMSC", 132, "Polly", "Possum");
	    registrar.addToCourse("MATH", 141, "Polly", "Possum");
	    registrar.addToCourse("PSYC", 100, "Polly", "Possum");
	    registrar.addToCourse("HIST", 101, "Polly", "Possum");
	    registrar.addToCourse("ECON", 105, "Polly", "Possum");    
	    registrar.addToCourse("MATH", 141, "Manny", "Manatee");
	    registrar.addToCourse("PSYC", 100, "Manny", "Manatee");
	    registrar.addToCourse("HIST", 101, "Manny", "Manatee");

	    registrar.cancelRegistration("Polly", "Possum");
	    registrar.cancelRegistration("Polly", "Possum");

	    assertEquals(0, registrar.numStudentsInCourse("CMSC", 132));
	    assertEquals(1, registrar.numStudentsInCourse("MATH", 141));
	    assertEquals(1, registrar.numStudentsInCourse("PSYC", 100));
	    assertEquals(1, registrar.numStudentsInCourse("HIST", 101));
	    assertEquals(0, registrar.numStudentsInCourse("ECON", 105));
	  }

}
