package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.*;

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
public class ServiceOfferRequest extends Request {
    @ManyToOne
    @JoinColumn(name = "OFFER")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "SERVICE")
    private Service service;
}
