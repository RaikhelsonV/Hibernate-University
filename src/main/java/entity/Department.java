package entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name of department may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(unique = true, nullable = false)
    private String name;

    @Valid
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StudyGroup> groups;

    @Valid
    @OneToOne(mappedBy = "department", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Schedule schedule;

    public Department() {
        groups = new ArrayList<StudyGroup>();
    }

    public Department(String name) {
        this();
        this.name = name;
    }

    public void addGroup(StudyGroup studyGroup) {
        if (studyGroup == null) {
            throw new NullPointerException("Can't add null StudyGroup.");
        }
        if (studyGroup.getDepartment() != null) {
            throw new IllegalStateException("StudyGroup is already assigned to an Department.");
        }
        studyGroup.setDepartment(this);
        groups.add(studyGroup);
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<StudyGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<StudyGroup> groups) {
        this.groups = groups;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
