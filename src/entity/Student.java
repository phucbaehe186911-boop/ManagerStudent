package entity;

import utils.Validator;

public class Student implements Comparable<Student> {

    private String id;
    private String studentName;
    private String semester;
    private Course courseName;

    public Student(String id, String studentName, String semester, Course courseName) {
        setId(id);
        setCourseName(courseName);
        setSemester(semester);
        setCourseName(courseName);
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

     public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ ID không được để trống hoặc null.");
        }
        if (!id.matches("[Ss0-9]+")) {
            throw new IllegalArgumentException("❌ ID chỉ được chứa ký tự chữ hoặc số (A-Z, 0-9).");
        }
        this.id = id.trim().toUpperCase();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Tên sinh viên không được để trống hoặc null.");
        }
        if (!studentName.matches("[A-Za-z\\s]+")) {
            throw new IllegalArgumentException("❌ Tên sinh viên chỉ được chứa chữ cái và khoảng trắng.");
        }
        this.studentName = studentName.trim();
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        if (semester == null || semester.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Học kỳ không được để trống hoặc null.");
        }
        // Ví dụ: cho phép "Spring", "Summer", "Fall", "Winter", hoặc số 1–3
        if (!semester.matches("(?i)(Spring|Summer|Fall|Winter|[1-3])")) {
            throw new IllegalArgumentException("❌ Học kỳ phải là Spring, Summer, Fall, Winter hoặc số 1–3.");
        }
        this.semester = semester.trim();
    }

    public Course getCourseName() {
        return courseName;
    }

    public void setCourseName(Course courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("❌ Khóa học không được null.");
        }
        this.courseName = courseName;
    }
    
     public void Input() throws Exception {
        Student student = new Student();

        student.setId(Validator.getString("Enter id: ", "Invalid!", "[Ss]\\d+"));
        student.setStudentName(Validator.getString("Enter student name: ", "Invalid!", "[A-Za-z\\s]+"));
        student.setSemester(Validator.getString("Enter semester (Spring/Summer/Fall/Winter or 1–3): ", 
                                                "Invalid!", "(?i)(Spring|Summer|Fall|Winter|[1-3])"));

        int choice = Validator.getInt(
                "Only three courses:\n"
                + "1 - Java\n"
                + "2 - .Net\n"
                + "3 - C/C++\n"
                + "Enter your choice: ",
                "Please enter number 1–3!",
                "Invalid!", 1, 3
        );
        student.setCourseName(Course.getCourse(choice));

    }

    @Override
    public int compareTo(Student t) {
        return this.studentName.compareTo(t.studentName);
    }

    @Override
    public String toString() {
        return "Student{"+id + ", " + studentName + ", " + semester + ", " + courseName.getLanguage() + '}';
    }

}
