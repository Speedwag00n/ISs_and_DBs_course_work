package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.Offer;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Procedure(value = "GET_OFFERS_IN_DORMITORY")
    List<Offer> findAllInDormitory(Integer dormitoryId, Status offerStatus);
}
