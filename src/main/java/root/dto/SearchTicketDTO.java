package root.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchTicketDTO extends SearchDTO {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date start;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date end;

    private Boolean status;

    private Integer departmentId;
}
