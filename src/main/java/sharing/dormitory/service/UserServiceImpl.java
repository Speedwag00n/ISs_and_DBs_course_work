package sharing.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.repository.DormitoryRepository;
import sharing.dormitory.db.repository.UserRepository;
import sharing.dormitory.dto.DormitoryDTO;
import sharing.dormitory.dto.UserDTO;
import sharing.dormitory.mapper.DormitoryMapper;
import sharing.dormitory.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl( UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        User entity = userMapper.dtoToEntity(userDTO);
        userRepository.save(entity);
    }

}
