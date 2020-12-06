package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.repository.ObjectRepository;
import sharing.dormitory.db.repository.ServiceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ThingService {
    private final ObjectRepository objectRepository;
    private final ServiceRepository serviceRepository;

    public List<Object> getUserObject(Integer id) {
        return objectRepository.findAllByUserId(id);
    }

    public List<sharing.dormitory.db.model.Service> getUserService(Integer id) {
        return serviceRepository.findAllByUserId(id);
    }
}
