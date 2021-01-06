package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class ServiceSuggestionPk implements Serializable {
    private static final long serialVersionUID = 7363213543416182307L;

    @OneToOne
    @JoinColumn(name = "SUGGESTION")
    private Suggestion suggestion;

    @OneToOne
    @JoinColumn(name = "SERVICE")
    private Service service;
}
