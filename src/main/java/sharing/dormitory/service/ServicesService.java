package sharing.dormitory.service;

import sharing.dormitory.db.model.Service;

import java.util.List;

public interface ServicesService {

    List<Service> getUserService(Integer id);

    void createService(Service service, Integer id);

    void deleteObject(Integer id);

}
