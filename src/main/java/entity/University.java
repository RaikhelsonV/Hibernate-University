package entity;

import javax.validation.Valid;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "university")
public class University {
    @Id
    private String name;

    @Valid
    @OneToOne(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Administration administration;

    @OneToMany(mappedBy = "university", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Faculty> faculties;

    @OneToMany(mappedBy = "university", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Student> students;

    public University() {
        faculties = new ArrayList<>();
        students = new ArrayList<>();
    }

    public University(String name) {
        this();
        this.name = name;
    }

    public void addFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new NullPointerException("Can't add null Faculty.");
        }
        if (faculty.getUniversity() != null) {
            throw new IllegalStateException("Faculty is already assigned to an University.");
        }
        faculty.setUniversity(this);
        faculties.add(faculty);
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new NullPointerException("Can't add null Student.");
        }
        if (student.getUniversity() != null) {
            throw new IllegalStateException("Student is already assigned to an University.");
        }
        student.setUniversity(this);
        students.add(student);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administration getAdministration() {
        return administration;
    }

    public void setAdministration(Administration administration) {
        this.administration = administration;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "University{" +
                "name='" + name + '\'' +
                '}';
    }
}
