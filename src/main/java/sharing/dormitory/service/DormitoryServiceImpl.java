package sharing.dormitory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.repository.DormitoryRepository;
import sharing.dormitory.dto.DormitoryDTO;
import sharing.dormitory.mapper.DormitoryMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {

    private DormitoryRepository dormitoryRepository;
    private DormitoryMapper dormitoryMapper;

    @Autowired
    public DormitoryServiceImpl(DormitoryRepository dormitoryRepository, DormitoryMapper dormitoryMapper) {
        this.dormitoryRepository = dormitoryRepository;
        this.dormitoryMapper = dormitoryMapper;
    }

    @Override
    public List<DormitoryDTO> getDormitories() {
        List<Dormitory> entities = dormitoryRepository.findAll();
        List<DormitoryDTO> dtos = new ArrayList<>();
        for (Dormitory entity : entities) {
            dtos.add(dormitoryMapper.entityToDto(entity));
        }
        return dtos;
    }

}
