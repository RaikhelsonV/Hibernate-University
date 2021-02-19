package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name of faculty may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "university_name", referencedColumnName = "name")
    private University university;

    @OneToMany(mappedBy = "faculty", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Department> departments;

    @OneToMany(mappedBy = "faculty", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Teacher> teachers;

    public Faculty() {
        departments = new ArrayList<Department>();
        teachers = new ArrayList<Teacher>();
    }

    public Faculty(String name) {
        this();
        this.name = name;
    }

    public void addDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException("Can't add null Department.");
        }
        if (department.getFaculty() != null) {
            throw new IllegalStateException("Department is already assigned to an Faculty.");
        }
        department.setFaculty(this);
        departments.add(department);
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new NullPointerException("Can't add null Teacher.");
        }
        if (teacher.getFaculty() != null) {
            throw new IllegalStateException("Teacher is already assigned to an Faculty.");
        }
        teacher.setFaculty(this);
        teachers.add(teacher);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", university=" + university +
                ", departments=" + departments +
                '}';
    }
}
