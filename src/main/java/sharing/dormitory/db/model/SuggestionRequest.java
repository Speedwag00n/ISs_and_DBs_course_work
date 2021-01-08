package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name="SUGGESTION_REQUEST")
public class SuggestionRequest extends Request {
    @ManyToOne
    @JoinColumn(name = "SUGGESTION")
    private Suggestion suggestion;
}
