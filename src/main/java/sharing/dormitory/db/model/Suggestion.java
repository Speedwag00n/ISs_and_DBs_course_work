package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import sharing.dormitory.db.enm.Status;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedStoredProcedureQuery(
        name = "insertSuggestionRequest",
        procedureName = "INSERT_SUGGESTION_REQUEST",
        parameters = {
                @StoredProcedureParameter(name = "name", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "content", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "author", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "suggestion", mode = ParameterMode.IN, type = Integer.class),
        }
)
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "STATUS")
    private Status offerStatus;
    @Column(name = "CREATION_DATE")
    private OffsetDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User user;
    @OneToMany(mappedBy = "suggestion")
    private List<SuggestionRequest> requests;
}
