package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharing.dormitory.db.model.Service;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findAllByUserId(Integer userId);
}
