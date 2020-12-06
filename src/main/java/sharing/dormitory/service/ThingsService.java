package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.enm.ObjectState;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.repository.ObjectRepository;
import sharing.dormitory.db.repository.ServiceRepository;
import sharing.dormitory.db.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ThingsService {
    private final ObjectRepository objectRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public List<Object> getUserObject(Integer id) {
        return objectRepository.findAllByUserId(id);
    }

    public List<sharing.dormitory.db.model.Service> getUserService(Integer id) {
        return serviceRepository.findAllByUserId(id);
    }

    public void createObject(Object object, Integer id) {
        object.setUser(userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        object.setState(ObjectState.IN_STOCK);
        objectRepository.save(object);
    }

    public void createService(sharing.dormitory.db.model.Service service) {
        serviceRepository.save(service);
    }
}
