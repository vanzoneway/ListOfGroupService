package listofgroup.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "info_about_name_employee")
@Getter
@Setter
public class InfoAboutNameEmployeeEntity {

    @Id
    private int id;

    @ManyToMany
    @JoinTable(name="groups_employees",
            joinColumns = @JoinColumn(name = "info_about_name_employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "info_about_name_group_id", referencedColumnName = "id") )
    List<InfoAboutNameGroupEntity> infoAboutNameGroupList = new ArrayList<>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "degree_abbrev")
    private String degreeAbbrev;

    @Column(name = "email")
    private String email;

    @Column(name = "photo_link")
    private String photoLink;

    @Column(name = "calendar_id")
    private String calendarId;

    @Column(name = "url_id")
    private String urlId;


}
