package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.Object;

import java.util.List;

@Repository
public interface ObjectRepository extends JpaRepository<Object, Integer> {
    List<Object> findAllByUserId(Integer userId);
}
