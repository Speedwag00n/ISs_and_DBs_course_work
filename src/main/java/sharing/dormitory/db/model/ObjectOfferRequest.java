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
@Table(name="OBJECT_OFFER_REQUEST")
public class ObjectOfferRequest {
    @EmbeddedId
    private RequestPk id;
    @ManyToOne
    @JoinColumn(name = "OBJECT")
    private Object object;
}
