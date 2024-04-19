package root.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class PageDTO<T> {
    private int totalPage;
    private long totalElements;
    private T data;
}
