package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "administration")
public class Administration {
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

    @Column(name = "academic_degree")
    private String academicDegree;

    @NotNull(message = "Speciality may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(nullable = false)
    private String speciality;

    @OneToOne()
    @JoinColumn(name = "university_name")
    private University university;

    public Administration() {
    }

    public Administration(@NotNull @Size(min = 2, max = 255, message = "Name is required, maximum 255 characters.") String lastName, @NotNull @Size(min = 2, max = 255, message = "Name is required, maximum 255 characters.") String firstName, String academicDegree, @NotNull String speciality, University university) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.academicDegree = academicDegree;
        this.speciality = speciality;
        this.university = university;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Administration{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", academicDegree='" + academicDegree + '\'' +
                ", speciality='" + speciality + '\'' +
                ", university=" + university +
                '}';
    }


}
