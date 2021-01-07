package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NamedStoredProcedureQuery(
        name = "getObjectSuggestionInDormitory",
        procedureName = "GET_OBJECT_SUGGESTIONS_IN_DORMITORY",
        resultClasses = {ObjectSuggestion.class},
        parameters = {
                @StoredProcedureParameter(name = "dormitoryId", mode = ParameterMode.IN, type = Integer.class)
        }
)
@Entity
public class ObjectSuggestion extends Suggestion {
    @OneToOne
    @JoinColumn(name = "OBJECT")
    private Object object;
}
