package listofgroup.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleDto {

    private int id;

    private String lessonTypeAbbrev;

    private String startLessonTime;

    private String endLessonTime;

    private String subject;

    private String subjectFullName;

}
