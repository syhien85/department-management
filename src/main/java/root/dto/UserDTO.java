package root.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank(message = "{not.blank}")
    private String name;

    @Min(value = 1, message = "{global.size.min}")
    private Integer age;

    @Size(min = 5, max = 30, message = "{global.size}")
    private String username;
    private String password;
    private String homeAddress;

    /**
     * map dữ liệu gửi lên từ form, convert to date
     */
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private String avatarUrl;

    private MultipartFile file;

    private DepartmentDTO department;
    /**
     * nếu chỉ cần departmentId
     */
    /*private int departmentId;*/
}
