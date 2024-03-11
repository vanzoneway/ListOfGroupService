package listofgroup.model;

import jakarta.persistence.*;
import java.util.Map;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "general_info_group")
@Getter
@Setter
public class GeneralInfoGroup {

    @Id
    private int id;

    @OneToOne(mappedBy = "generalInfoGroup", cascade = CascadeType.ALL)
    private InfoAboutNameGroup infoAboutNameGroup;

    @OneToMany(mappedBy = "generalInfoGroup", cascade = CascadeType.ALL)
    @MapKeyColumn(name = "day_of_week")
    Map<String, ScheduleList> scheduleListMap;

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
