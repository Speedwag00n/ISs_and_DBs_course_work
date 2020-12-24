package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.enm.ObjectState;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.repository.ObjectRepository;
import sharing.dormitory.db.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ObjectsServiceImpl implements ObjectsService {
    private final ObjectRepository objectRepository;
    private final UserRepository userRepository;

    @Override
    public List<Object> getUserObject(Integer id) {
        return objectRepository.findAllByUserId(id);
    }

    @Override
    public void createObject(Object object, Integer id) {
        object.setUser(userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        object.setState(ObjectState.IN_STOCK);
        objectRepository.save(object);
    }
}
