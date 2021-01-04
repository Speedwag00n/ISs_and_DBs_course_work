package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharing.dormitory.db.model.Offer;

import javax.annotation.Resource;

@Resource
public interface OfferRepository extends JpaRepository<Offer, Integer> {

}
