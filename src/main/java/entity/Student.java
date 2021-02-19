package entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Last name may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "First name may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "university_name", referencedColumnName = "name")
    private University university;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", referencedColumnName = "id")
    private StudyGroup studyGroup;

    @Valid
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Student() {
    }

    public Student(String lastName, String firstName, Schedule schedule) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.schedule = schedule;
    }

    public Student(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", studyGroup=" + studyGroup +
                ", schedule=" + schedule +
                '}';
    }
}
