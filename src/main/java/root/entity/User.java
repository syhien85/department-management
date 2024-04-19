package root.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;

    @Column(unique = true)
    private String username;
    private String password;
    private String homeAddress;

    // luu file path name
    private String avatarUrl;

    /**
     * @ ManyToOne: many user to one department
     * @ JoinColumn(name = "department_id") // auto gen
     * @ JoinColumn: chỉ áp dụng mới ManyToOne và OneOne
     */
    @ManyToOne
    private Department department;

    /**
     * trên giao diện là String, trên entity là Date
     */
    @Temporal(TemporalType.DATE)
    private Date birthdate;
}
