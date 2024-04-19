package root.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TicketDTO {
    private int id;

    private String clientName;
    private String clientPhone;
    private String content;
    private boolean status;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date processDate;

    private DepartmentDTO department;
}
