package root.dto;

import jakarta.persistence.Column;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;

@Data
public class DepartmentDTO {
    private int id;

    @NotBlank(message = "{not.blank}")
    private String name;

    private Date createdAt;
}
