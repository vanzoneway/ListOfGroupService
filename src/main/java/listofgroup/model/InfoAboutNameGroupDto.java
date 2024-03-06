package listofgroup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoAboutNameGroupDto {

    private int id;

    private String name;

    private int facultyId;

    private String facultyName;

    private int specialityDepartmentEducationFormId;

    private String specialityName;

    private int course;

    private String calendarId;

    private String facultyAbbrev;

}
