package entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @Max(value = 500, message = "Minimum 0, maximum 500.")
    private long audience;

    @Column(name = "start_of_lecture")
    private LocalTime startOfLecture;

    @Column(name = "end_of_lecture")
    private LocalTime endOfLecture;

    @Valid
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "lecture_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private List<Lecture> lectures;

    @Valid
    @OneToOne(mappedBy = "schedule", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Student student;

    public Schedule() {
        lectures = new ArrayList<Lecture>();
    }

    public Schedule(LocalDate date, long audience, LocalTime startOfLecture, LocalTime endOfLecture) {
        this.date = date;
        this.audience = audience;
        this.startOfLecture = startOfLecture;
        this.endOfLecture = endOfLecture;
    }

    public long getAudience() {
        return audience;
    }

    public void setAudience(long audience) {
        this.audience = audience;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartOfLecture() {
        return startOfLecture;
    }

    public void setStartOfLecture(LocalTime startOfLecture) {
        this.startOfLecture = startOfLecture;
    }

    public LocalTime getEndOfLecture() {
        return endOfLecture;
    }

    public void setEndOfLecture(LocalTime endOfLecture) {
        this.endOfLecture = endOfLecture;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", audience=" + audience +
                ", startOfLecture=" + startOfLecture +
                ", endOfLecture=" + endOfLecture +
                ", department=" + department +
                '}';
    }
}
