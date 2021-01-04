package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import sharing.dormitory.db.enm.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import java.time.OffsetDateTime;

@Data
@Entity
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NamedStoredProcedureQuery(
        name = "getServiceSuggestionInDormitory",
        procedureName = "GET_SERVICE_SUGGESTIONS_IN_DORMITORY",
        resultClasses = {Suggestion.class},
        parameters = {
                @StoredProcedureParameter(name = "dormitoryId", mode = ParameterMode.IN, type = Integer.class)
        }
)
@NamedStoredProcedureQuery(
        name = "getObjectSuggestionInDormitory",
        procedureName = "GET_OBJECT_SUGGESTIONS_IN_DORMITORY",
        resultClasses = {Suggestion.class},
        parameters = {
                @StoredProcedureParameter(name = "dormitoryId", mode = ParameterMode.IN, type = Integer.class)
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
}
