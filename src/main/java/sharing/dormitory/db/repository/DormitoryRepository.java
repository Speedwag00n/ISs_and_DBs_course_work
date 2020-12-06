package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.Dormitory;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Integer> {

}