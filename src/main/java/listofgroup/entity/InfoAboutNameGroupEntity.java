package listofgroup.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "info_about_name_group")
@Getter
@Setter
public class InfoAboutNameGroupEntity {
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "general_info_group")
    private GeneralInfoGroupEntity generalInfoGroup;

    @ManyToMany
    @JoinTable(name="groups_employees",
            joinColumns = @JoinColumn(name = "info_about_name_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "info_about_name_employee_id", referencedColumnName = "id") )
    List<InfoAboutNameEmployeeEntity> infoAboutNameEmployeeList = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "faculty_id")
    private int facultyId;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "speciality_departament_education_form_id")
    private int specialityDepartmentEducationFormId;

    @Column(name = "speciality_name")
    private String specialityName;

    @Column(name = "course")
    private int course;

    @Column(name = "calendar_id")
    private String calendarId;

    @Column(name = "faculty_abbrev")
    private String facultyAbbrev;

}
