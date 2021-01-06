package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

}