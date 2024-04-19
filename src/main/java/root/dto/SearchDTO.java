package root.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SearchDTO {

//    @NotBlank(message = "{not.blank}")
//    @Size(min = 1, max = 20, message = "{size.msg}")
    private String keyword;

    private Integer currentPage;
    private Integer size;
    private String sortedField;

}
