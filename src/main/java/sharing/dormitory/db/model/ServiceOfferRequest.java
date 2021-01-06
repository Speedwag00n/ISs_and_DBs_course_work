package sharing.dormitory.db.model;

import lombok.Data;
import sharing.dormitory.db.model.pk.RequestPk;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name="SERVICE_OFFER_REQUEST")
public class ServiceOfferRequest {
    @EmbeddedId
    private RequestPk id;
    @ManyToOne
    @JoinColumn(name = "SERVICE")
    private Object object;
}
