package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findAllByUserId(Integer userId);
}
