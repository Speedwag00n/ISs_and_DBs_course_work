package sharing.dormitory.db.model.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.model.Request;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RequestPk implements Serializable {
    private static final long serialVersionUID = -1150006363008227267L;

    @ManyToOne
    @JoinColumn(name = "OFFER")
    private Offer offer;
    @ManyToOne
    @JoinColumn(name = "REQUEST")
    private Request request;
}
