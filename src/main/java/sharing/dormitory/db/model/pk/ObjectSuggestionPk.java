package sharing.dormitory.db.model.pk;

import lombok.Data;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.Suggestion;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class ObjectSuggestionPk implements Serializable {
    private static final long serialVersionUID = -8569404615053908429L;

    @OneToOne
    @JoinColumn(name = "SUGGESTION")
    private Suggestion suggestion;

    @OneToOne
    @JoinColumn(name = "OBJECT")
    private Object object;
}
