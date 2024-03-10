package tests;

// (c) Larry Herman, 2022.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import registrar.Registrar;
import org.junit.*;
import static org.junit.Assert.*;

public class Project1SecretTests {

  // Tests calling numStudentsInCourse() on courses whose departments and
  // numbers don't both match.
  @Test public void testSecret1() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 40);
    registrar.addNewCourse("MATH", 510, 20);
    registrar.addNewCourse("BIOE", 250, 30);
    registrar.addNewCourse("ENGL", 420, 20);

    assertTrue(registrar.addToCourse("ENGL", 420, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("MATH", 510, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("ENGL", 420, "Horace", "Horse"));

    assertEquals(-1, registrar.numStudentsInCourse("ENGL", 501));
    assertEquals(-1, registrar.numStudentsInCourse("CMSC", 420));
    assertEquals(-1, registrar.numStudentsInCourse("ENGL", 510));
    assertEquals(2, registrar.numStudentsInCourse("ENGL", 420));
  }

  // Tests isInCourse() by calling it on a nonexistent course.
  @Test public void testSecret2() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 40);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 101, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("MATH", 510, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("CMSC", 101, "Horace", "Horse"));

    assertFalse(registrar.isInCourse("ENGL", 250, "Cammy", "Camel"));
    assertFalse(registrar.isInCourse("ENGL", 250, "Sheila", "Sheep"));
  }

  // Tests isInCourse() after a student has been dropped from all of their
  // courses using cancelRegistration().
  @Test public void testSecret3() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 10);
    registrar.addNewCourse("MATH", 510, 20);
    registrar.addNewCourse("ENGL", 250, 30);

    assertTrue(registrar.addToCourse("CMSC", 101, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 101, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("MATH", 510, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("ENGL", 250, "Ellen", "Elephant"));

    assertTrue(registrar.cancelRegistration("Ellen", "Elephant"));

    assertFalse(registrar.isInCourse("CMSC", 101, "Ellen", "Elephant"));
    assertFalse(registrar.isInCourse("MATH", 510, "Ellen", "Elephant"));
    assertFalse(registrar.isInCourse("ENGL", 250, "Ellen", "Elephant"));
  }

  // Tests that students can be added to full courses after a student is
  // removed from them using cancelRegistration().
  @Test public void testSecret4() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 2);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 101, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 101, "Sheila", "Sheep"));

    assertTrue(registrar.cancelRegistration("Ellen", "Elephant"));

    assertTrue(registrar.addToCourse("CMSC", 101, "Cammy", "Camel"));
  }

  // Tests cancelRegistration() by calling it on a nonexistent student.
  @Test public void testSecret5() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 132, 3);

    assertTrue(registrar.addToCourse("CMSC", 132, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 132, "Sheila", "Sheep"));

    assertFalse(registrar.cancelRegistration("Cammy", "Camel"));

    assertEquals(2, registrar.numStudentsInCourse("CMSC", 132));
  }

  // Tests that all the students can be removed from a course (using both
  // dropCourse() and cancelRegistration()), that the course has zero
  // students then, and that new students can be added back afterwards.
  @Test public void testSecret6() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 131, 10);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 131, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("MATH", 510, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 131, "Horace", "Horse"));

    assertTrue(registrar.cancelRegistration("Sheila", "Sheep"));
    assertTrue(registrar.dropCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar.cancelRegistration("Horace", "Horse"));

    assertEquals(0, registrar.numStudentsInCourse("CMSC", 131));

    assertTrue(registrar.addToCourse("CMSC", 131, "Cammy", "Camel"));
  }

  // Tests calling numStudentsInCourseWithLastName().
  @Test public void testSecret7() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 132, 10);
    registrar.addNewCourse("MATH", 141, 10);

    registrar.addToCourse("CMSC", 132, "Ellie", "Elephant");
    registrar.addToCourse("CMSC", 132, "Elwood", "Elephant");
    registrar.addToCourse("MATH", 141, "Cristina", "Cricket");
    registrar.addToCourse("MATH", 141, "Laura", "Llama");
    registrar.addToCourse("CMSC", 132, "Elena", "Elephant");
    registrar.addToCourse("CMSC", 132, "Elisa", "Elephant");
    registrar.addToCourse("CMSC", 132, "Elmore", "Elephant");

    assertEquals(5, registrar.numStudentsInCourseWithLastName("CMSC", 132,
                                                              "Elephant"));
  }

  // Tests calling dropCourse() on a student for a nonexistent course, and
  // verifies that it returns false.
  @Test public void testSecret8() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 40);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 101, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("MATH", 510, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("CMSC", 101, "Horace", "Horse"));

    assertFalse(registrar.dropCourse("ENGL", 250, "Cammy", "Camel"));
    assertFalse(registrar.dropCourse("ENGL", 250, "Ellen", "Elephant"));
  }

  // Tests calling dropCourse() twice on a student in a course- the first
  // call should remove them, while the second call should not have any
  // effect, and should return false.
  @Test public void testSecret9() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 132, 3);

    assertTrue(registrar.addToCourse("CMSC", 132, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 132, "Sheila", "Sheep"));

    assertTrue(registrar.dropCourse("CMSC", 132, "Ellen", "Elephant"));
    assertFalse(registrar.dropCourse("CMSC", 132, "Ellen", "Elephant"));

    assertEquals(1, registrar.numStudentsInCourse("CMSC", 132));
  }

  // Tests calling dropCourse() on a student who is not registered for an
  // existing course, verifies that it returns false, and ensures that the
  // number of students is unchanged afterwards.
  @Test public void testSecret10() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 101, 40);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 101, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("MATH", 510, "Sheila", "Sheep"));
    assertTrue(registrar.addToCourse("CMSC", 101, "Horace", "Horse"));

    assertFalse(registrar.dropCourse("CMSC", 101, "Cammy", "Camel"));
    assertFalse(registrar.dropCourse("CMSC", 101, "Sheila", "Sheep"));
    assertFalse(registrar.dropCourse("MATH", 510, "Ellen", "Elephant"));

    assertEquals(2, registrar.numStudentsInCourse("CMSC", 101));
    assertEquals(1, registrar.numStudentsInCourse("MATH", 510));
  }

  // Tests calling dropCourse() on a course that is full (at capacity), and
  // checks that a new student is able to be added after that.
  @Test public void testSecret11() {
    Registrar registrar= new Registrar();
    int i, capacity= 10;

    registrar.addNewCourse("CMSC", 132, capacity);

    // add 10 students, so course will be full
    for (i= 0; i < capacity; i++)
      assertTrue(registrar.addToCourse("CMSC", 132, "Ellen" + i,
                                       "Elephant" + i));

    assertTrue(registrar.dropCourse("CMSC", 132, "Ellen0", "Elephant0"));
    assertTrue(registrar.addToCourse("CMSC", 132, "Sheila", "Sheep"));
  }

  // Tests that names are being properly stored and compared.  Just
  // concatenating first and last names and storing them in a single string
  // is probably not going to work, if different students in a course have
  // names like the ones in this test.  (Nothing in the project assignment
  // says that a student can't have a two-word first or last name.)  To
  // properly compare two students' names it is necessary to compare their
  // first names for equality and their last names for equality separately.
  @Test public void testSecret12() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 132, 10);

    registrar.addToCourse("CMSC", 132, "Kristin", "Scott Thomas");
    registrar.addToCourse("CMSC", 132, "Kristin Scott", "Thomas");
    registrar.addToCourse("CMSC", 132, "Helena", "Bonham Carter");
    registrar.addToCourse("CMSC", 132, "Helena Bonham", "Carter");

    assertEquals(4, registrar.numStudentsInCourse("CMSC", 132));
  }
  
  // Tests that two different Registrar objects can be created and their
  // data is independent and doesn't conflict.
  @Test public void testSecret13() {
    // Registrar # 1
    Registrar registrar1= new Registrar();

    registrar1.addNewCourse("CMSC", 131, 10);
    registrar1.addNewCourse("MATH", 510, 20);

    assertTrue(registrar1.addToCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar1.addToCourse("CMSC", 131, "Sheila", "Sheep"));
    assertTrue(registrar1.addToCourse("MATH", 510, "Ellen", "Elephant"));

    // Registrar # 2
    Registrar registrar2= new Registrar();

    registrar2.addNewCourse("CMSC", 131, 10);
    registrar2.addNewCourse("ENGL", 510, 20);
    registrar2.addNewCourse("BIOE", 250, 10);

    assertTrue(registrar2.addToCourse("CMSC", 131, "Horace", "Horse"));
    assertTrue(registrar2.addToCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar2.addToCourse("CMSC", 131, "Polly", "Possum"));
    assertTrue(registrar2.addToCourse("ENGL", 510, "Sheila", "Sheep"));

    // tests that each Registrar's data is independent
    assertEquals(2, registrar1.numCourses());
    assertEquals(3, registrar2.numCourses());
    assertEquals(2, registrar1.numStudentsInCourse("CMSC", 131));
    assertEquals(3, registrar2.numStudentsInCourse("CMSC", 131));
    assertEquals(1, registrar1.numStudentsInCourse("MATH", 510));
    assertEquals(1, registrar2.numStudentsInCourse("ENGL", 510));

    assertTrue(registrar1.dropCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar2.isInCourse("CMSC", 131, "Ellen", "Elephant"));

    assertTrue(registrar1.cancelCourse("CMSC", 131));
    assertEquals(3, registrar2.numCourses());
  }

  // Tests that some methods are properly enforcing case sensitivity of
  // department and student names.  (The project assignment says that names
  // that have different case are not considered to be the same name.)
  @Test public void testSecret14() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 131, 10);

    assertTrue(registrar.addToCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 131, "Sheila", "Sheep"));

    assertEquals(-1, registrar.numStudentsInCourse("cmsc", 131));

    registrar.addNewCourse("cmsc", 131, 10);
    assertTrue(registrar.addToCourse("cmsc", 131, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("cmsc", 131, "Horace", "Horse"));
    assertTrue(registrar.addToCourse("cmsc", 131, "Polly", "Possum"));

    assertEquals(2, registrar.numCourses());
    assertFalse(registrar.cancelRegistration("sheila", "sheep"));
    assertEquals(2, registrar.numStudentsInCourse("CMSC", 131));
    assertEquals(3, registrar.numStudentsInCourse("cmsc", 131));

    // ellent elphant is not the same student as Ellen Elephant
    assertFalse(registrar.isInCourse("CMSC", 131, "ellen", "elephant"));
    // and HoRaCe HoRsE is not the same student as Horace Horse
    assertTrue(registrar.addToCourse("cmsc", 131, "HoRaCe", "HoRsE"));
  }

  // Tests that some methods are properly comparing names.
  @Test public void testSecret15() {
    Registrar registrar= new Registrar();

    registrar.addNewCourse("CMSC", 131, 10);
    registrar.addNewCourse("MATH", 510, 20);

    assertTrue(registrar.addToCourse("CMSC", 131, "Ellen", "Elephant"));
    assertTrue(registrar.addToCourse("CMSC", 131, "Sheila", "Sheep"));

    assertFalse(registrar.cancelCourse("MAT", 131));
    assertFalse(registrar.isInCourse("CMSC", 131, "Sheil", "Shee"));
    assertFalse(registrar.isInCourse("CMSC", 131, "Sheilaa", "Sheepp"));
    assertFalse(registrar.dropCourse("CMSC", 131, "Sheil", "Shee"));
    assertFalse(registrar.cancelRegistration("Sheilaa", "Sheepp"));
  }

}
