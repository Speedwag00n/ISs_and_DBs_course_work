package sharing.dormitory.db.model.pk;

import lombok.Data;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.model.Request;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class RequestPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "OFFER")
    private Offer offer;
    @ManyToOne
    @JoinColumn(name = "REQUEST")
    private Request request;
}
