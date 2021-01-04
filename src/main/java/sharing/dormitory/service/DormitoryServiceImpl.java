package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.repository.DormitoryRepository;
import sharing.dormitory.dto.DormitoryDTO;
import sharing.dormitory.mapper.DormitoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DormitoryServiceImpl implements DormitoryService {
    private final DormitoryRepository dormitoryRepository;
    private final DormitoryMapper dormitoryMapper;

    @Override
    public List<DormitoryDTO> getDormitories() {
        List<Dormitory> entities = dormitoryRepository.findAll();
        return entities.stream().map(dormitoryMapper::entityToDto).collect(Collectors.toList());
    }
}
