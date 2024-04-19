package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Ticket;

import java.util.Date;
import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    /*Page<Ticket> findByClientName(String keyword, Pageable pageable);*/

    /*@Query(
        "SELECT t " +
            "FROM Ticket t " +
            "WHERE t.clientName LIKE :x "
    )
    Page<Ticket> searchByName(@Param("x") String s, Pageable pageable);*/

    /*@Query(
        "SELECT t " +
            "FROM Ticket t " +
            "WHERE t.createdAt >= :start AND  t.createdAt <= :end"
    )
    Page<Ticket> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);*/

    /*@Query(
        "SELECT t " +
            "FROM Ticket t " +
            "JOIN t.department d " +
            "WHERE d.id = :x"
    )*/
    /*@Query(
        "SELECT t " +
            "FROM Ticket t " +
            "WHERE t.department.id = :x"
    )
    Page<Ticket> searchByDepartmentId(@Param("x") int dId, Pageable pageable);*/

    /*@Query(
        "SELECT t " +
            "FROM Ticket t " +
            "JOIN t.department d " +
            "WHERE d.name = :x"
    )
    Page<Ticket> searchByDepartmentName(@Param("x") String dName, Pageable pageable);*/

    Page<Ticket> findByStatus(boolean status, Pageable pageable);

    String queryKeyword = "(t.department.name LIKE :s OR t.clientPhone LIKE :s OR t.clientName LIKE :s OR t.content LIKE :s)";

    @Query("SELECT t FROM Ticket t " +
        "WHERE " + queryKeyword)
    Page<Ticket> searchByDefault(@Param("s") String s, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.department.id = :c1 AND " + queryKeyword)
    Page<Ticket> searchByDepartmentId(@Param("s") String s, @Param("c1") int c1, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.createdAt >= :c2 AND " + queryKeyword)
    Page<Ticket> searchByStart(@Param("s") String s, @Param("c2") Date c2, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.createdAt <= :c3 AND " + queryKeyword)
    Page<Ticket> searchByEnd(@Param("s") String s, @Param("c3") Date c3, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.department.id = :c1 AND t.createdAt >= :c2 AND " + queryKeyword)
    Page<Ticket> searchByDepartmentIdAndStart(@Param("s") String s, @Param("c1") int c1, @Param("c2") Date c2, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.department.id = :c1 AND t.createdAt <= :c3 AND " + queryKeyword)
    Page<Ticket> searchByDepartmentIdAndEnd(@Param("s") String s, @Param("c1") int c1, @Param("c3") Date c3, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.createdAt >= :c2 AND t.createdAt <= :c3 AND " + queryKeyword)
    Page<Ticket> searchByStartAndEnd(@Param("s") String s, @Param("c2") Date c2, @Param("c3") Date c3, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
        "WHERE t.department.id = :c1 AND t.createdAt >= :c2 AND t.createdAt <= :c3 AND " + queryKeyword)
    Page<Ticket> searchByDepartmentIdAndStartAndEnd(@Param("s") String s, @Param("c1") int c1, @Param("c2") Date c2, @Param("c3") Date c3, Pageable pageable);

}
