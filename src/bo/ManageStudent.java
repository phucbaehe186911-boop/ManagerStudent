package bo;

import entity.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudent {

private List<Student> list;

public ManageStudent() {
    list = new ArrayList<>();
}

public List<Student> getList() {
    return list;
}

public void setList(List<Student> list) {
    this.list = (list == null) ? new ArrayList<>() : list;
}

public boolean isExisted(Student student) {
    if (student == null) {
        return false;
    }

    for (Student students : list) {
        if (students.getId() != null
                && students.getSemester() != null
                && students.getCourseName() != null
                && students.getId().equalsIgnoreCase(student.getId())
                && students.getSemester().equalsIgnoreCase(student.getSemester())
                && students.getCourseName() == student.getCourseName()) {
            return true;
        }
    }
    return false;
}

public boolean add(Student student) throws Exception {
    if (student == null
            || student.getId() == null
            || student.getCourseName() == null) {
        throw new Exception("Invalid student data!");
    }

    if (isExisted(student)) {
        throw new Exception("This record already exists!");
    }

    // Keep the same name for every record belonging to one ID.
    for (Student existing : list) {
        if (existing.getId().equalsIgnoreCase(student.getId())) {
            if (!existing.getStudentName().equalsIgnoreCase(
                    student.getStudentName())) {
                throw new Exception(
                        "This ID already belongs to "
                        + existing.getStudentName() + "!");
            }
        }
    }

    return list.add(student);
}

public boolean delete(String id) throws Exception {
    if (list.isEmpty()) {
        throw new Exception("List is empty, cannot delete!");
    }

    for (int i = 0; i < list.size(); i++) {
        Student student = list.get(i);

        if (student.getId() != null
                && student.getId().equalsIgnoreCase(id)) {
            list.remove(i);
            return true;
        }
    }

    throw new Exception("Student with ID " + id + " not found!");
}

public boolean delete(Student selected) throws Exception {
    if (selected == null || !list.remove(selected)) {
        throw new Exception("Selected record cannot be found!");
    }
    return true;
}

private int getIndexRecord(Student student) {
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == student) {
            return i;
        }
    }
    return -1;
}

public boolean update(Student oldStudentRecord,
        Student newStudentRecord) throws Exception {

    if (list.isEmpty()) {
        throw new Exception("List is empty, cannot update!");
    }

    int index = getIndexRecord(oldStudentRecord);

    if (index == -1) {
        throw new Exception("This record cannot be found!");
    }

    // An ID must keep its original student name.
    for (Student existing : list) {
        if (existing != oldStudentRecord
                && existing.getId().equalsIgnoreCase(
                        newStudentRecord.getId())) {

            if (!existing.getStudentName().equalsIgnoreCase(
                    newStudentRecord.getStudentName())) {
                throw new Exception(
                        "This ID already belongs to "
                        + existing.getStudentName() + "!");
            }
        }
    }

    // Check duplicate record, excluding the record being updated.
    for (Student existing : list) {
        if (existing != oldStudentRecord
                && existing.getId().equalsIgnoreCase(
                        newStudentRecord.getId())
                && existing.getSemester().equalsIgnoreCase(
                        newStudentRecord.getSemester())
                && existing.getCourseName()
                        == newStudentRecord.getCourseName()) {
            throw new Exception("New record is duplicate!");
        }
    }

    list.set(index, newStudentRecord);
    return true;
}

public ArrayList<Student> getListStudentById(String id) {
    ArrayList<Student> result = new ArrayList<>();

    for (Student student : list) {
        if (student.getId() != null
                && student.getId().equalsIgnoreCase(id)) {
            result.add(student);
        }
    }

    return result;
}

public ArrayList<Student> getListStudentByName(String name) {
    ArrayList<Student> result = new ArrayList<>();

    for (Student student : list) {
        if (student.getStudentName() != null
                && student.getStudentName().toLowerCase()
                        .contains(name.toLowerCase())) {
            result.add(student);
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
        return "No student records found!";
    }

    StringBuilder str = new StringBuilder();
    str.append(String.format("|%5s|%15s|%10s|%15s|%n",
            "No.", "Student Name", "Semester", "Course Name"));

    for (int i = 0; i < list.size(); i++) {
        Student student = list.get(i);

        str.append(String.format("|%5s|%15s|%10s|%15s|%n",
                i + 1,
                student.getStudentName(),
                student.getSemester(),
                student.getCourseName().getLanguage()));
    }

    return str.toString();
}

private HashMap<String, Integer> generateReport() {
    HashMap<String, Integer> reports = new HashMap<>();

    for (Student student : list) {
        String key = student.getId() + "#"
                + student.getStudentName() + "#"
                + student.getCourseName().getLanguage();

        reports.put(key, reports.getOrDefault(key, 0) + 1);
    }

    return reports;
}

public String report() {
    if (list.isEmpty()) {
        return null;
    }

    HashMap<String, Integer> reports = generateReport();
    StringBuilder str = new StringBuilder();

    str.append(String.format("|%5s|%15s|%10s|%15s|%n",
            "No.", "Student Name", "Course", "Total of Course"));

    int count = 1;

    // Sort report keys for stable output.
    List<String> keys = new ArrayList<>(reports.keySet());
    Collections.sort(keys);

    for (String key : keys) {
        String[] parts = key.split("#", -1);

        str.append(String.format("|%5s|%15s|%10s|%15s|%n",
                count++,
                parts[1],
                parts[2],
                reports.get(key)));
    }

    return str.toString();
}

}
