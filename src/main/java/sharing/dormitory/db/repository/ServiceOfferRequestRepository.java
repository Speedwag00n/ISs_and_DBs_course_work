package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.ObjectOfferRequest;
import sharing.dormitory.db.model.pk.RequestPk;

@Repository
public interface ServiceOfferRequestRepository extends JpaRepository<ObjectOfferRequest, RequestPk> {

}