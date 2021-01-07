package sharing.dormitory.db.model;

import lombok.Data;
import sharing.dormitory.db.model.pk.RequestPk;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Data
@Entity
@Table(name="SERVICE_OFFER_REQUEST")
@NamedStoredProcedureQuery(
        name = "insertServiceOfferRequest",
        procedureName = "INSERT_SERVICE_OFFER_REQUEST",
        parameters = {
                @StoredProcedureParameter(name = "name", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "content", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "author", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "offer", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "service", mode = ParameterMode.IN, type = Integer.class)
        }
)
public class ServiceOfferRequest {
    @EmbeddedId
    private RequestPk id;
    @ManyToOne
    @JoinColumn(name = "SERVICE")
    private Service service;
}
