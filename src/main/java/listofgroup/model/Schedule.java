package listofgroup.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_list_id")
    private ScheduleList scheduleList;

    @Column(name = "lesson_type_abbrev")
    private String lessonTypeAbbrev;

    @Column(name = "start_lesson_time")
    private String startLessonTime;

    @Column(name = "end_lesson_time")
    private String endLessonTime;

    @Column(name = "subject")
    private String subject;

    @Column(name = "subject_full_name")
    private String subjectFullName;



}
