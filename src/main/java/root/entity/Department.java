package root.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Department extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    /**
     * - mặc định ko cần use @ OneToMany (ko bắt buộc)
     * - dùng @ OneToMany trong trường hợp lấy Department, xong lấy
     * luôn User liên quan (khi cần join bảng)
     * - @OneToMany : one Department to many User
     * - mappedBy = "department", map với tên biến bên entity User (department)
     * - muốn sử dụng @OneToMany, bắt buộc phải có @ManyToOne ở entity tương ứng
     */
    /*@OneToMany(mappedBy = "department")
    private List<User> users;*/
}
