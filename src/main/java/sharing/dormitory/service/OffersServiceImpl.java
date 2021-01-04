package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.repository.OfferRepository;
import sharing.dormitory.db.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class OffersServiceImpl implements OffersService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public List<Offer> getOffers(Integer userId) {
        Dormitory dormitory = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new).getDormitory();
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("getOffersInDormitory");
        query.setParameter("dormitoryId", dormitory.getId());
        query.execute();
        return (List<Offer>) query.getResultList();
    }

    @Override
    public List<Offer> getUserOffers(Integer userId) {
        return offerRepository.findAllByUserId(userId);
    }

    @Override
    public void createOffer(Offer offer, Integer userId) {
        offer.setUser(userRepository.findById(userId).orElseThrow(IllegalArgumentException::new));
        offerRepository.save(offer);
    }

    @Override
    public void deleteOffer(Integer id) {
        offerRepository.deleteById(id);
    }
}
