package root.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Ticket extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clientName;
    private String clientPhone;
    private String content;
    private Boolean status;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date processDate;

    @ManyToOne
    private Department department;

}
