package controller;

import bo.ManageStudent;
import entity.Course;
import entity.Student;
import java.util.ArrayList;
import utils.Validator;

public class Controller {

private final ManageStudent studentManager;

public Controller() {
    studentManager = new ManageStudent();
}

public void createStudent() throws Exception {
while (true) {
Student student = new Student();
boolean validStudent = false;

    
    // Step 1: Check ID first. Duplicate ID must be rejected immediately.
    while (!validStudent) {
        String id = Validator.getString(
                "Enter id: ",
                "Invalid ID! Use S followed by digits.",
                "(?i)S\\d+");

        ArrayList<Student> sameIdList =
                studentManager.getListStudentById(id);

        if (!sameIdList.isEmpty()) {
            System.out.println(
                    "Error: ID " + id.toUpperCase()
                    + " already exists!");
            System.out.println("Please enter another ID.");
            continue;
        }

        student.setId(id);

        String name = Validator.getString(
                "Enter student name: ",
                "Invalid student name!",
                "[A-Za-z\\s]+");

        student.setStudentName(name);
        validStudent = true;
    }


    // Step 2: Enter semester.
    student.setSemester(Validator.getString(
            "Enter semester (Spring/Summer/Fall/Winter or 1-3): ",
            "Invalid semester!",
            "(?i)(Spring|Summer|Fall|Winter|[1-3])"));

    // Step 3: Enter course.
    int choice = Validator.getInt(
            "Only three courses:\n"
            + "1 - Java\n"
            + "2 - .Net\n"
            + "3 - C/C++\n"
            + "Enter your choice: ",
            "Please enter number 1-3!",
            "Invalid number!",
            1, 3);

    student.setCourseName(Course.getCourse(choice));

    // Step 4: Check duplicate record.
    try {
        studentManager.add(student);
        System.out.println("Student created successfully!");
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
        System.out.println("Please enter the record again.");
        continue;
    }

    String answer = Validator.getString(
            "Do you continue (Y/N)? ",
            "Just Y or N!",
            "[YNyn]");

    if (answer.equalsIgnoreCase("N")) {
        break;
    }
}

}


public void findAndSort() throws Exception {
    String name = Validator.getString(
            "Enter name student: ",
            "Invalid!",
            "[A-Za-z\\s]+");

    ArrayList<Student> list =
            studentManager.getListStudentByName(name);

    if (list.isEmpty()) {
        throw new Exception("Cannot find student name!");
    }

    ManageStudent result = new ManageStudent();
    result.setList(list);
    result.sortStudentsByName();

    System.out.println(result);
}

public void updateOrDelete() throws Exception {
    ArrayList<Student> list;

    // Step 1: Find records by ID. If not found, ask again.
    while (true) {
        String id = Validator.getString(
                "Enter student ID: ",
                "Invalid ID! Use S followed by digits.",
                "(?i)S\\d+");

        list = studentManager.getListStudentById(id);

        if (list.isEmpty()) {
            System.out.println(
                    "No student found with ID: " + id);
            System.out.println("Please enter the ID again.");
            continue;
        }

        break;
    }

    ManageStudent found = new ManageStudent();
    found.setList(list);

    System.out.println("\n=== RECORDS FOUND ===");
    System.out.println(found);
    //sv co the co nhieu ban ghi
    int recordNumber = Validator.getInt(
            "Select record number to modify: ",
            "Invalid!",
            "Must be 1-" + list.size(),
            1, list.size());

    // The selected record is the original record to update/delete.
    Student selected = list.get(recordNumber - 1);

    String action = Validator.getString(
            "Do you want Update(U) or Delete(D)? ",
            "Just U or D!",
            "[UDud]");

    if (action.equalsIgnoreCase("U")) {
        boolean updated = false;

        while (!updated) {
            try {
                System.out.println("\n--- Enter new information ---");

                Student newStudent = new Student();

                // Step 2: Validate the new ID and name.
                while (true) {
                    String newId = Validator.getString(
                            "Enter id: ",
                            "Invalid ID! Use S followed by digits.",
                            "(?i)S\\d+");

                    String newName = Validator.getString(
                            "Enter student name: ",
                            "Invalid student name!",
                            "[A-Za-z\\s]+");

                    ArrayList<Student> sameIdList =
                            studentManager.getListStudentById(newId);

                    boolean nameMatched = true;

                    if (!sameIdList.isEmpty()) {
                        String existingName =
                                sameIdList.get(0).getStudentName();

                        if (!existingName.equalsIgnoreCase(newName)) {
                            System.out.println(
                                    "Error: ID " + newId
                                    + " already belongs to "
                                    + existingName + "!");
                            System.out.println(
                                    "Please enter the ID and name again.");

                            nameMatched = false;
                        }
                    }

                    if (nameMatched) {
                        newStudent.setId(newId);
                        newStudent.setStudentName(newName);
                        break;
                    }
                }

                // Step 3: Enter semester and course.
                newStudent.setSemester(Validator.getString(
                        "Enter semester (Spring/Summer/Fall/Winter or 1-3): ",
                        "Invalid semester!",
                        "(?i)(Spring|Summer|Fall|Winter|[1-3])"));

                int courseChoice = Validator.getInt(
                        "Only three courses:\n"
                        + "1 - Java\n"
                        + "2 - .Net\n"
                        + "3 - C/C++\n"
                        + "Enter your choice: ",
                        "Please enter number 1-3!",
                        "Invalid number!",
                        1, 3);

                newStudent.setCourseName(
                        Course.getCourse(courseChoice));

                // Step 4: Update the selected original record.
                studentManager.update(selected, newStudent);

                System.out.println("Student updated successfully!");
                updated = true;

            } catch (Exception e) {
                System.out.println(
                        "Update failed: " + e.getMessage());
                System.out.println(
                        "Please enter the information again.");
            }
        }

    } else {
        // Delete only the selected record.
        studentManager.delete(selected);
        System.out.println("Student record deleted successfully!");
    }
}

public void report() throws Exception {
    String result = studentManager.report();

    if (result == null) {
        throw new Exception("List is empty!");
    }

    System.out.println(result);
}

public void generateStudent() throws Exception {
    // Sample data for testing.
    studentManager.add(new Student(
            "S10", "Nguyen Quan", "Fall", Course.JAVA));

    studentManager.add(new Student(
            "S10", "Nguyen Quan", "Spring", Course.JAVA));

    studentManager.add(new Student(
            "S10", "Nguyen Quan", "Fall", Course.C_CPP));

    studentManager.add(new Student(
            "S10", "Nguyen Quan", "Fall", Course.DOT_NET));

    studentManager.add(new Student(
            "S11", "Tran Linh", "Summer", Course.JAVA));

    studentManager.add(new Student(
            "S11", "Tran Linh", "Summer", Course.DOT_NET));

    studentManager.add(new Student(
            "S12", "Le Thu Thao", "Summer", Course.JAVA));

    studentManager.add(new Student(
            "S13", "Le Phuong Minh", "Summer", Course.DOT_NET));

    studentManager.add(new Student(
            "S14", "Minh Vu", "Spring", Course.JAVA));

    studentManager.add(new Student(
            "S15", "Tuan Minh", "Spring", Course.C_CPP));
}

}
