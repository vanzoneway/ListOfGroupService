package listofgroup.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "schedule_list")
@Getter
@Setter
public class ScheduleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_info_group_id")
    GeneralInfoGroup generalInfoGroup;

    @OneToMany(mappedBy = "scheduleList", cascade = CascadeType.ALL)
    @OrderColumn(name = "schedule_index")
    private List<Schedule> schedules;



}
