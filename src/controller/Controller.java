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
            Student student = new Student(); // Nhập sinh viên
            student.Input();
            ArrayList<Student> sameIdList = studentManager.getListStudentById(student.getId());
            if (!sameIdList.isEmpty()) {
                String name = sameIdList.get(0).getStudentName();
                student.setStudentName(name);
                System.out.println("🔁 Existing ID found, using name: " + name);
            }

            if (!studentManager.add(student)) {
                throw new Exception("❌ Cannot create student (possibly duplicate record).");
            }

            System.out.println("✅ Student created successfully!");

            // ❗ Luôn hỏi người dùng sau mỗi lần nhập
            String choice = Validator.getString("Do you continue (Y/N)? ", "Just Y or N", "[YNyn]");
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public void findAndSort() throws Exception {
        String name = Validator.getString("Enter name student: ", "Invalid!", "[A-Za-z\\s]+");
        ArrayList<Student> list = studentManager.getListStudentByName(name);
        if (list.isEmpty()) {
            throw new Exception("Can not found name!");
        }
        ManageStudent result = new ManageStudent();
        result.setList(list);
        result.sortStudentsByName();
        System.out.println(result.toString());
    }

    public void updateOrDelete() throws Exception {
        String id = Validator.getString("Enter student ID: ", "Invalid!", "[Ss]\\d+");
        ArrayList<Student> list = studentManager.getListStudentById(id);

        if (list.isEmpty()) {
            throw new Exception("❌ No student found with ID: " + id);
        }

        ManageStudent found = new ManageStudent();
        found.setList(list);

        System.out.println("\n=== RECORDS FOUND ===");
        System.out.println(found);

        int choice = Validator.getInt("Select record number to modify: ",
                "Invalid!", "Must be 1–" + list.size(), 1, list.size());
        Student selected = list.get(choice - 1);

        String action = Validator.getString("Do you want Update(U) or Delete(D)? ",
                "Just U or D", "[UDud]");

        if (action.equalsIgnoreCase("U")) {
            System.out.println("\n--- Enter new information ---");
            
            Student newStudent = new Student();
            newStudent.Input();

            // Nếu ID mới đã tồn tại → dùng lại tên cũ
            ArrayList<Student> sameIdList = studentManager.getListStudentById(newStudent.getId());
            if (!sameIdList.isEmpty()) {
                String name = sameIdList.get(0).getStudentName();
                newStudent.setStudentName(name);
                System.out.println("🔁 Existing ID found, using name: " + name);
            }

            studentManager.update(selected, newStudent);
            System.out.println("✅ Student updated successfully!");
        } else {
            studentManager.delete(id);
            System.out.println("🗑️ Student deleted successfully!");
        }
    }

    public void report() throws Exception {
        String result = studentManager.report();
        if (result == null) {
            throw new Exception("List is empty!");
        }
        System.out.println(result);
    }

//    public void generateStudent() throws Exception {
//        studentManager.add(new Student("s1", "Nguyen Quan", "Fall2023", Course.JAVA));
//        studentManager.add(new Student("s1", "Nguyen Quan", "Fall2024", Course.JAVA));
//        studentManager.add(new Student("s1", "Nguyen Quan", "Fall2023", Course.C_CPP));
//        studentManager.add(new Student("s1", "Nguyen Quan", "Fall2023", Course.DOT_NET));
//        studentManager.add(new Student("s2", "Tran Linh", "Sum2024", Course.JAVA));
//        studentManager.add(new Student("s2", "Tran Linh", "Sum2024", Course.DOT_NET));
//        studentManager.add(new Student("s3", "Le Thu Thao", "Sum2024", Course.JAVA));
//        studentManager.add(new Student("s4", "Le Phuong Minh", "Sum2024", Course.DOT_NET));
//        studentManager.add(new Student("s5", "Minh Vu", "Spring2023", Course.JAVA));
//        studentManager.add(new Student("s6", "Tuan Minh", "Spring2023", Course.C_CPP));
//    }
}
