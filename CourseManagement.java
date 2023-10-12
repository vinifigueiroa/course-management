/* This program provides functionality to enroll students in courses,
assign grades to students, and calculate overall course grades for each student.
*/

import java.util.*;

public class CourseManagement {

    public static void main (String[] args) {

        // Initialize Scanner
        Scanner scanner = new Scanner(System.in);

        //Load Default Courses
        {
            new Course("CS101", "Introduction to Programming", 30);
            new Course("MATH202", "Calculus II", 40);
            new Course("ENG301", "Advanced English Composition", 25);
            new Course("BIO201", "Biology Concepts", 35);
            new Course("PHYS101", "Physics Fundamentals", 28);
            new Course("CHEM202", "Chemistry Lab", 45);
            new Course("HIST101", "World History", 30);
            new Course("ART203", "Art Appreciation", 20);
            new Course("PSYCH202", "Psychology 202", 40);
            new Course("ECON301", "Economic Principles", 50);
        }

        // Load Default Students
        {
            new Student("John Smith");
            new Student("Alice Johnson");
            new Student("David Lee");
            new Student("Emily Davis");
            new Student("Michael Wilson");
            new Student("Sophia Brown");
            new Student("James Anderson");
            new Student("Olivia Garcia");
            new Student("William Martinez");
            new Student("Emma Rodriguez");
        }

        // Intro
        System.out.println("COURSE MANAGEMENT SYSTEM. \n\nWELCOME.");

        // Menu
        boolean loop = true;
        while (loop) {

            System.out.println("\n\nMENU \n\nA - Add a New Course \nB - Enroll a Student \nC - Assign Grade \nD - Consult Overall Grades \nE - Exit \n \nTYPE THE CORRESPONDING LETTER\n");
            String choice = scanner.nextLine().toLowerCase();
            System.out.println("\n");

            switch (choice) {
                case "a":
                    addNewCourse(scanner);
                    break;

                case "b":
                    enrollStudent(scanner);
                    break;

                case "c":
                    assignGrade(scanner);
                    break;

                case "d":
                    consultAllGrades(scanner);
                    break;

                case "e":
                    loop = false;
                    break;

                default:
                    break;
            }
        }

    }

    public static void addNewCourse(Scanner scanner) {

        // Creates a new course

        String code = null;
        boolean condition = true;

        while(condition) {

            System.out.println("\n \nType the code of the course:");
            code = scanner.nextLine();

            if ((Course.getAllCoursesCodes()).contains(code)) {

                System.err.println("ERROR: Code already in use. Please enter a differente value");
                continue;
            }

            condition = false;
        }

        System.out.println("\n \nType the name of the course:");
        String name = scanner.nextLine();

        System.out.println("\n \nType the maximum capacity of the course:");
        int max = positiveIntegerInput(scanner);

        new Course(code, name, max);

        System.out.println("\n \nCourse added!");
        pressEnter(scanner);
    }

    public static void enrollStudent(Scanner scanner) {
        // Enrolls a student in a course with available seats.

        Student.listAllStudents();

        System.out.println("\n \nWhich student would you like to enroll? Type their ID Number.");

        // Get Student ID
        Student student = Student.getByID(IDInput(scanner));
        System.out.println("\n \nStudent found: " + student.getName());

        // Select Course
        System.out.println("\n \nIn which course would you like to enroll the student? Type the course code:\n \n");
        Course.listAllCourses();

        Course course = Course.getByCode(CourseCodeInput(scanner));

        student.enrollInCourse(course);

        System.out.println("\n \nStudent Successfully enrolled!\n \n" + student + course);
        pressEnter(scanner);

    }

    public static void consultAllGrades(Scanner scanner) {
        //Lists the overall grades of all students

        Student.getAllGrades();
        pressEnter(scanner);
    }

    public static void assignGrade(Scanner scanner) {

        // Assigns a grade to a course the student is currently enrolled

        Student.listAllStudents();

        // Get Student
        System.out.println("\n \nTo which student would you like to assign a grade to? Type their ID Number.");
        Student student = Student.getByID(IDInput(scanner));
        System.out.println("\n \nStudent found: " + student.getName());

        if (student.listEnrolledCourses() == 0) {
            System.out.println("\n \nStudent is not enrolled in any courses.");
            pressEnter(scanner);
            return;
        }

        // Get Course

        boolean condition = true;
        Course course = null;

        while (condition) {
            System.out.println("\n \nCurrently enrolled Courses:");
            student.listEnrolledCourses();
            System.out.println("\n \nTo which course would you like to assign a grade? Type the code code.");
            course = Course.getByCode(CourseCodeInput(scanner));

            if(!student.IsEnrolled(course)) {
                System.out.println("\n \nStudent is not enrolled in this course. Try again.");
                continue;
            }

            condition = false;
        }

        System.out.println(course);

        // Get Grade
        System.out.println("\n \nType the value for the grade:");
        int grade = positiveIntegerInput(scanner);

        student.assignGrade(course, grade);
        student.listGrades();
        System.out.println("\n \nGrade assigned!");

        pressEnter(scanner);
    }


    // Helper Functions

    public static int positiveIntegerInput(Scanner scanner) {

        //Evaluates if a string input is a valid positive integer and returns the integer value.

        int integer = 0;
        boolean condition = true;

        while(condition) {

            String input = scanner.nextLine();

            try {
                integer = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.err.println("ERROR: Please enter a number.");
                continue;
            }

            if (integer < 0) {
                System.err.println("ERROR: Please enter a positive amount.");
                continue;

            } else if (integer == 0) {
                return 0;

            }

            condition = false;


        }
        System.out.println("\n");
        return integer;

    }

    public static void pressEnter(Scanner scanner) {

        // Prompts the user for confirmation to continue.

        System.out.println("\nPRESS ENTER TO CONTINUE \n");
        scanner.nextLine();
    }

    public static int IDInput(Scanner scanner) {

        // Evaluates if the input value is a valid student ID number. Returns the ID number;

        System.out.println("Type the corresponding ID number for the student you would like to consult:");
        int ID = 0;
        boolean condition = true;

        while (condition) {

            ID = positiveIntegerInput(scanner);

            try {
                Student.getByID(ID);
            } catch (IllegalArgumentException e) {

                System.out.println("ID not found. Try again.");
                continue;
            }

            condition = false;
        }

        return ID;
    }

    public static String CourseCodeInput(Scanner scanner) {

        // Evaluates if the input value is a valid Course Code. Returns the code number;

        System.out.println("Type the corresponding code for the course:");
        String code = null;
        boolean condition = true;

        while (condition) {

            code = scanner.nextLine().toUpperCase();

            try {
                Course.getByCode(code);
            } catch (IllegalArgumentException e) {

                System.out.println("Course code not found. Try again.");
                continue;
            }

            condition = false;
        }

        return code;
    }

}

public class Student {

    // Class Variables
    private static Map<Integer, Student> allStudents = new HashMap<>();
    private static int studentsCount = 0;


    // Instance Variables
    private String name;
    private int ID;
    private Map<Course, Integer> grades = new HashMap<>();


    // Constructor
    public Student(String name) {

        this.name = name;
        this.ID = studentsCount + 101;

        allStudents.put(ID, this);
        studentsCount++;
    }


    // Getter Methods
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String toString() {
        return "ID: " + ID +" || Name: "+ name + "\n";
    }

    public int getOverallGrade(){

        int gradeSum = 0;
        int courseCount = 0;

        for (int grade : (this.grades).values()) {
            gradeSum += grade;
            courseCount++;
        }

        if (courseCount == 0) {
            return 0;
        }

        return gradeSum / courseCount;

    }

    public int listEnrolledCourses() {
        // List all courses a student is currently enrolled.

        int count = 0;

        for (Course course : grades.keySet()) {
            System.out.println(course);
            count++;
        }

        return count;
    }

    public void listGrades() {
        // Lists the student's grades

        for (Map.Entry<Course, Integer> entry : grades.entrySet()) {
        System.out.println(entry.getKey() + "|| Grade: " + entry.getValue());
        }
    }

    public boolean IsEnrolled(Course course) {
        // Checks if a student is enrolled in a particular course

        if (this.grades.get(course) == null) {
            return false;
        }

        return true;
    }

    // Setter Methods
    public void setName(String newname) {
        name = newname;
    }

    // Class Interface

    public static Student getByID(int ID) {
        // Returns a student by its ID

        Student student = allStudents.get(ID);

        if (student == null) {
            throw new IllegalArgumentException("ID Not Found.");
        }

        return student;

    }

    public void enrollInCourse(Course course) {
        // Enrolls a student in a course;

        Map<Integer, Student> courseStudents = course.getEnrolledStudents();

        if (courseStudents.size() >= course.getCourseMaxCapacity()) {

            throw new IllegalArgumentException("This course is full.");
        }


        grades.put(course, 0);
        courseStudents.put(ID, this);

    }

    public void assignGrade(Course course, int grade) {
        // Assigns a grade to course the student is enrolled.

        if (!(course.getEnrolledStudents()).containsKey(this.ID)) {
            throw new IllegalArgumentException("Student is not enrolled.");
        }

        grades.put(course, grade);
    }

    public static void getAllGrades() {
        // Displays all average grades from all students.

        for (Student student : allStudents.values()) {
            System.out.println(student.name + ": " + student.getOverallGrade());
        }
    }

    public static void listAllStudents() {

        for (Student student : allStudents.values()) {
            System.out.println(student.toString());
        }
    }
}


public class Course {

    // Class Variables
    private static int totalEnrolledStudents = 0;
    private static Map<String, Course> allCourses = new HashMap<>();


    // Instance Variables
    private String courseCode;
    private String courseName;
    private int courseMaximumCapacity;
    private Map<Integer, Student> enrolledStudents = new HashMap<>();


    // Constructor
    public Course(String courseCode, String courseName, int courseMaximumCapacity) {

        if (allCourses.containsKey(courseCode)) {
            throw new IllegalArgumentException("This code already exists");
        }

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseMaximumCapacity = courseMaximumCapacity;

        allCourses.put(courseCode, this);

    }

    // Class Methods
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public static Set<String> getAllCoursesCodes() {
        return allCourses.keySet();
    }

    public static void listAllCourses() {

        for (Course course : allCourses.values()) {
            System.out.println(course.toString());
        }
    }

    public static Course getByCode(String code) {
        // Retrieves a course by its course code.

        Course course = allCourses.get(code);

        if (course == null) {
            throw new IllegalArgumentException("Code Not Found.");
        }

        return course;
    }


    // Instance Getter Methods
    public String getCourseCode() {
        return this.courseCode;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getCourseMaxCapacity() {
        return this.courseMaximumCapacity;
    }

    public Map<Integer, Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    public String toString() {
        return "Code: " + this.courseCode + " || Name: " + this.courseName + " || Students Enrolled: " + (this.enrolledStudents).size() + "/" + this.courseMaximumCapacity;
    }


    // Instance Setter Methods
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseMaxCapacity(int courseMaxCapacity) {
        this.courseMaximumCapacity = courseMaxCapacity;
    }


}
