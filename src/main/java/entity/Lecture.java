package entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name of lecture may not be null.")
    @Size(min = 2, max = 25, message = "Minimum 2 characters, maximum 25 characters.")
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "lecture_schedule",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private List<Schedule> schedules;

    @Valid
    @OneToOne(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Teacher teacher;

    public Lecture() {
        schedules = new ArrayList<Schedule>();
    }

    public Lecture(String name) {
        this();
        this.name = name;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                '}';
    }
}
