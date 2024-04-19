package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE d.name LIKE :s")
    Page<Department> searchName(@Param("s") String name, Pageable pageable);
}
