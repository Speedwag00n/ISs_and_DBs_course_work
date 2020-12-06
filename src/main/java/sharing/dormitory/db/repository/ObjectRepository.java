package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharing.dormitory.db.model.Object;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ObjectRepository extends JpaRepository<Object, Integer> {
    List<Object> findAllByUserId(Integer userId);
}
