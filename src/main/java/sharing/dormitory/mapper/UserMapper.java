package sharing.dormitory.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.User;
import sharing.dormitory.db.repository.DormitoryRepository;
import sharing.dormitory.dto.UserDTO;

@Component
public class UserMapper {

    private DormitoryRepository dormitoryRepository;

    @Autowired
    public UserMapper(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    public User dtoToEntity(UserDTO dto) {
        User entity = new User();

        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setPassword(dto.getPassword());
        Dormitory dormitoryEntity = dormitoryRepository.findById(dto.getDormitory()).get();
        entity.setDormitory(dormitoryEntity);
        entity.setRating(dto.getRating());

        return entity;
    }

    public UserDTO entityToDto(User entity) {
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setTelephone(entity.getTelephone());
        dto.setDormitory(entity.getDormitory().getId());
        dto.setRating(entity.getRating());

        return dto;
    }

}
