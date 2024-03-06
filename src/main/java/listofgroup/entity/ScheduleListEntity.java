package listofgroup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "schedule_list")
@Getter
@Setter
public class ScheduleListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "general_info_group_id")
    GeneralInfoGroupEntity generalInfoGroup;

    @OneToMany(mappedBy = "scheduleList", cascade = CascadeType.ALL)
    @OrderColumn(name = "schedule_index")
    private List<ScheduleEntity> scheduleList;



}
