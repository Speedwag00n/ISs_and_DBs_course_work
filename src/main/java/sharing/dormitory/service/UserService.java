package sharing.dormitory.service;

import sharing.dormitory.dto.UserDTO;

public interface UserService {

    void addUser(UserDTO userDTO);

    UserDTO getUser(String username);

}
