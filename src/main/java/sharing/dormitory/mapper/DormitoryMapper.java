package sharing.dormitory.mapper;

import org.springframework.stereotype.Component;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.User;
import sharing.dormitory.dto.DormitoryDTO;
import sharing.dormitory.dto.UserDTO;

@Component
public class DormitoryMapper {

    public Dormitory dtoToEntity(DormitoryDTO dto) {
        Dormitory entity = new Dormitory();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public DormitoryDTO entityToDto(Dormitory entity) {
        DormitoryDTO dto = new DormitoryDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setDescription(entity.getDescription());

        return dto;
    }

}
