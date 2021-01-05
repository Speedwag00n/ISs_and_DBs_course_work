package sharing.dormitory.service;

import sharing.dormitory.db.model.User;
import sharing.dormitory.dto.UserDTO;

public interface UserService {

    void addUser(UserDTO userDTO);

    User getUser(String username);

}
