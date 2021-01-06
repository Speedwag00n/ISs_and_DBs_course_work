package sharing.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.repository.UserRepository;
import sharing.dormitory.dto.UserDTO;
import sharing.dormitory.mapper.UserMapper;

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

    @Override
    public User getUser(String username) {
        User entity = userRepository.findByUsername(username);
        return entity;
    }

    @Override
    public User getUser(Integer id) {
        User entity = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return entity;
    }

}
