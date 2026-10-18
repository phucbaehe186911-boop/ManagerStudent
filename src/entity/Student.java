package entity;

import utils.Validator;

public class Student implements Comparable<Student> {

private String id;
private String studentName;
private String semester;
private Course courseName;

public Student(String id, String studentName, String semester, Course courseName) {
    setId(id);
    setStudentName(studentName);
    setSemester(semester);
    setCourseName(courseName);
}

public Student() {
}

public String getId() {
    return id;
}

public void setId(String id) {
    if (id == null || !id.trim().matches("(?i)s\\d+")) {
        throw new IllegalArgumentException(
                "ID must follow the format S1, S2, S3...");
    }
    this.id = id.trim().toUpperCase();
}

public String getStudentName() {
    return studentName;
}

public void setStudentName(String studentName) {
    if (studentName == null
            || !studentName.trim().matches("[A-Za-z\\s]+")) {
        throw new IllegalArgumentException(
                "Student name must contain letters and spaces only!");
    }
    this.studentName = studentName.trim();
}

public String getSemester() {
    return semester;
}

public void setSemester(String semester) {
    if (semester == null
            || !semester.trim().matches(
                    "(?i)(Spring|Summer|Fall|Winter|[1-3])")) {
        throw new IllegalArgumentException(
                "Semester must be Spring, Summer, Fall, Winter or 1-3!");
    }
    this.semester = semester.trim();
}

public Course getCourseName() {
    return courseName;
}

public void setCourseName(Course courseName) {
    if (courseName == null) {
        throw new IllegalArgumentException("Course cannot be null!");
    }
    this.courseName = courseName;
}

public void Input() throws Exception {
    setId(Validator.getString(
            "Enter id: ", "Invalid! Use S followed by digits.",
            "(?i)S\\d+"));

    setStudentName(Validator.getString(
            "Enter student name: ", "Invalid student name!",
            "[A-Za-z\\s]+"));

    setSemester(Validator.getString(
            "Enter semester (Spring/Summer/Fall/Winter or 1-3): ",
            "Invalid semester!",
            "(?i)(Spring|Summer|Fall|Winter|[1-3])"));

    int choice = Validator.getInt(
            "Only three courses:\n"
            + "1 - Java\n"
            + "2 - .Net\n"
            + "3 - C/C++\n"
            + "Enter your choice: ",
            "Please enter number 1-3!",
            "Invalid number!",
            1, 3);

    setCourseName(Course.getCourse(choice));
}

@Override
public int compareTo(Student t) {
    return this.studentName.compareToIgnoreCase(t.studentName);
}

@Override
public String toString() {
    return "Student{" + id + ", " + studentName + ", "
            + semester + ", " + courseName.getLanguage() + '}';
}

}
