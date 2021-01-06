package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.ObjectOfferRequest;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.model.Request;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.db.model.ServiceOfferRequest;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.model.pk.RequestPk;
import sharing.dormitory.db.repository.ObjectOfferRequestRepository;
import sharing.dormitory.db.repository.RequestRepository;
import sharing.dormitory.db.repository.ServiceOfferRequestRepository;
import sharing.dormitory.dto.OfferRequestDTO;

import java.util.Objects;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final OffersService offersService;
    private final UserService userService;
    private final ObjectsService objectsService;
    private final ServicesService servicesService;
    private final ObjectOfferRequestRepository objectOfferRequestRepository;
    private final ServiceOfferRequestRepository serviceOfferRequestRepository;
    private final RequestRepository requestRepository;
    public void create(OfferRequestDTO offerRequest) {

        // Get Objects from DTO
        Offer offer = offersService.getOffer(offerRequest.getOfferId());
        User user = userService.getUser(offerRequest.getUserId());

        // Creating request
        Request request = new Request();
        request.setName(offer.getName());
        request.setContent(offerRequest.getDescription());
        request.setUser(user);
        requestRepository.save(request);

        if (Objects.nonNull(offerRequest.getObjectId())) {
            Object object = objectsService.getObject(offerRequest.getObjectId());

            ObjectOfferRequest objectOfferRequest = new ObjectOfferRequest();
            objectOfferRequest.setId(new RequestPk(offer, request));
            objectOfferRequest.setObject(object);
            objectOfferRequestRepository.save(objectOfferRequest);
        }
        else if (Objects.nonNull(offerRequest.getServiceId())) {
            Service service = servicesService.getService(offerRequest.getServiceId());

            ServiceOfferRequest serviceOfferRequest = new ServiceOfferRequest();
            serviceOfferRequest.setId(new RequestPk(offer, request));
            serviceOfferRequest.setService(service);
            serviceOfferRequestRepository.save(serviceOfferRequest);
        } else throw new IllegalArgumentException("Wrong value of OfferRequestDTO");
    }
}
