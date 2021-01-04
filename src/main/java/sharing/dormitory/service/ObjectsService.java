package sharing.dormitory.service;

import sharing.dormitory.db.model.Object;

import java.util.List;

public interface ObjectsService {

    List<Object> getUserObject(Integer id);

    void createObject(Object object, Integer id);

    void deleteObject(Integer id);

}
