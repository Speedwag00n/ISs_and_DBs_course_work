package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.ObjectOfferRequest;

@Repository
public interface ObjectOfferRequestRepository extends JpaRepository<ObjectOfferRequest, Integer> {

}