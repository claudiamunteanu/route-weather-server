package main.persistence;

import main.domain.DrivingTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrivingTipRepository extends JpaRepository<DrivingTip, Long> {
    int deleteDrivingTipById(Long id);

    @Query("SELECT d FROM DrivingTip AS d WHERE d.user.username=?1 ORDER BY d.id")
    List<DrivingTip> getAllByUsername(String username);

    @Query("SELECT MAX(d.id) FROM DrivingTip AS d")
    Long getLastId();
}
