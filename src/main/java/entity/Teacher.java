package entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Last name may not be null.")
    @Size(min = 3, max = 255, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "First name may not be null.")
    @Size(min = 2, max = 255, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "Academic degree may not be null.")
    @Size(min = 2, max = 255, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(name = "academic_degree", nullable = false)
    private String academicDegree;

    private int experience;

    private String email;

    @Valid
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public Teacher() {

    }

    public Teacher(String lastName, String firstName, String academicDegree, int experience, String email, Lecture lecture) {
        this();
        this.lastName = lastName;
        this.firstName = firstName;
        this.academicDegree = academicDegree;
        this.experience = experience;
        this.email = email;
        this.lecture = lecture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", academicDegree='" + academicDegree + '\'' +
                ", experience=" + experience +
                ", email='" + email + '\'' +
                ", faculty=" + faculty +
                ", lecture=" + lecture +
                '}';
    }
}
