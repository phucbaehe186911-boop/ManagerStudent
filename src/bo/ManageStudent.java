   
package bo;

import entity.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ManageStudent {

    private List<Student> list;

    public ManageStudent() {
        list = new ArrayList<>();
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public boolean isExisted(Student student) {
        for (Student students : list) {
            if (students.getId().equals(student.getId())
                    && students.getSemester().equals(student.getSemester())
                    && students.getCourseName().equals(student.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    public boolean add(Student student) throws Exception {
        if (isExisted(student)) {
            throw new Exception("This record is existed!");
        }
        return list.add(student);
    }

//    public boolean delete(String id) throws Exception {
//        if (list.isEmpty()) {
//            throw new Exception("List is empty, can not delete");
//        }
//        if (!isExisted(student)) {
//            throw new Exception("This record can not found!");
//        }
//        return list.remove(Student);
//    }
    public boolean delete(String id) throws Exception {
    if (list.isEmpty()) {
        throw new Exception("List is empty, cannot delete!");
    }

    for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getId().equalsIgnoreCase(id)) {
            list.remove(i);
            return true; // Xóa thành công
        }
    }

    throw new Exception("Student with ID " + id + " not found!");
}

    private int getIndexRecord(Student student) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(student)) {
                return i;
            }
        }
        return -1;
    }

    public boolean update(Student oldStudentRecord, Student newStudentRecord) throws Exception {
    if (list.isEmpty()) {
        throw new Exception("List is empty, can not update");
    }

    for (Student student : list) {
        if (student.getId().equals(oldStudentRecord.getId())
                && student.getSemester().equals(oldStudentRecord.getSemester())
                && student.getCourseName().equals(oldStudentRecord.getCourseName())) {

            if (isExisted(newStudentRecord) && !oldStudentRecord.equals(newStudentRecord)) {
                throw new Exception("New record is duplicate!");
            }

            // Cập nhật thông tin
            student.setId(newStudentRecord.getId());
            student.setStudentName(newStudentRecord.getStudentName());
            student.setSemester(newStudentRecord.getSemester());
            student.setCourseName(newStudentRecord.getCourseName());
            return true;
        }
    }

    throw new Exception("This record cannot be found!");
}


    public ArrayList<Student> getListStudentById(String id) {
        ArrayList<Student> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equalsIgnoreCase(id)) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    public ArrayList<Student> getListStudentByName(String name) {
        ArrayList<Student> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStudentName().toLowerCase().contains(name.toLowerCase())) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    public void sortStudentsByName() {
        Collections.sort(list);
    }

    @Override
    public String toString() {
        if (list.isEmpty()) {
            return null;
        }
        String str = String.format("|%5s|%15s|%10s|%15s|\n", "No.", "Student Name",
                "Semester", "Course Name");
        for (int i = 0; i < list.size(); i++) {
            str += String.format("|%5s|%15s|%10s|%15s|\n", i + 1,
                    list.get(i).getStudentName(),
                    list.get(i).getSemester(),
                    list.get(i).getCourseName().getLanguage());
        }
        return str;
    }

    private HashMap<String, Integer> generateReport() {
        HashMap<String, Integer> reports = new HashMap<>();
        for (Student student : list) {
            String key = student.getId() + "#" + student.getStudentName() + "#" + student.getCourseName().getLanguage();
            reports.put(key, reports.getOrDefault(key, 0) + 1);
        }
        return reports;
    }

    public String report() {
        if (list.isEmpty()) {
            return null;
        }
        HashMap<String, Integer> reports = generateReport();
        String str = String.format("|%5s|%15s|%10s|%15s|\n", "No.", "Student Name", "Course", "Total of Course");
        int count = 1;
        for (String key : reports.keySet()) {
            String[] parts = key.split("#");
            String studentName = parts[1];
            String course = parts[2];
            int total = reports.get(key);
            str += String.format("|%5s|%15s|%10s|%15s|\n", count++, studentName, course, total);
        }
        return str;
    }
}
