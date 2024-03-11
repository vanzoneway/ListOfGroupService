package listofgroup.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralInfoGroupDto {

    private int id;

    private String startDate;

    private String endDate;

    private String startExamsDate;

    private String endExamsDate;

    private String currentTerm;

    private String currentPeriod;

}
