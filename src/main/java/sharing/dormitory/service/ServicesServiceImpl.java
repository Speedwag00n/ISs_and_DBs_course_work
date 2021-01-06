package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.db.repository.ServiceRepository;
import sharing.dormitory.db.repository.UserRepository;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @Override
    public List<sharing.dormitory.db.model.Service> getUserService(Integer id) {
        return serviceRepository.findAllByUserId(id);
    }

    @Override
    public void createService(Service service, Integer id) {
        service.setUser(userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        serviceRepository.save(service);
    }

    @Override
    public void deleteService(Integer id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public Service getService(Integer id) {
        return serviceRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
