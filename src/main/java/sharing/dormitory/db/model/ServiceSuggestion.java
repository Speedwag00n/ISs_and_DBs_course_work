package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import sharing.dormitory.db.model.pk.ServiceSuggestionPk;

import javax.persistence.*;

@Data
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NamedStoredProcedureQuery(
        name = "getServiceSuggestionInDormitory",
        procedureName = "GET_SERVICE_SUGGESTIONS_IN_DORMITORY",
        resultClasses = {ServiceSuggestion.class},
        parameters = {
                @StoredProcedureParameter(name = "dormitoryId", mode = ParameterMode.IN, type = Integer.class)
        }
)
@Entity
public class ServiceSuggestion {
    @EmbeddedId
    private ServiceSuggestionPk id;
}
