package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="OBJECT_OFFER_REQUEST")
@NamedStoredProcedureQuery(
        name = "insertObjectOfferRequest",
        procedureName = "INSERT_OBJECT_OFFER_REQUEST",
        parameters = {
                @StoredProcedureParameter(name = "name", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "content", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "author", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "offer", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "object_id", mode = ParameterMode.IN, type = Integer.class)
        }
)
public class ObjectOfferRequest extends Request {
    @ManyToOne
    @JoinColumn(name = "OFFER")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "OBJECT")
    private Object object;
}
