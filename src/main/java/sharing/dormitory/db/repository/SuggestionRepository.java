package sharing.dormitory.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sharing.dormitory.db.model.Suggestion;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    List<Suggestion> findAllByUserId(Integer userId);
}
