package listofgroup.entity;

import jakarta.persistence.*;
import java.util.Map;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "general_info_group")
@Getter
@Setter
public class GeneralInfoGroupEntity {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private InfoAboutNameGroupEntity infoAboutNameGroup;

    @OneToMany(mappedBy = "generalInfoGroup", cascade = CascadeType.ALL)
    @MapKeyColumn(name = "day_of_week")
    Map<String, ScheduleListEntity> scheduleListMap;

    @Column(name="start_date")
    private String startDate;

    @Column(name="end_date")
    private String endDate;

    @Column(name="start_exams_date")
    private String startExamsDate;

    @Column(name="end_exams_date")
    private String endExamsDate;

    @Column(name = "current_term")
    private String currentTerm;

    @Column(name = "currentPeriod")
    private String currentPeriod;


}
