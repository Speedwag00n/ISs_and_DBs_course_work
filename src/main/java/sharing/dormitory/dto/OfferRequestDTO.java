package sharing.dormitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OfferRequestDTO implements Serializable {
    private static final long serialVersionUID = -5277159414005078198L;

    private String description;
    private Integer offerId;
    private Integer serviceId;
    private Integer objectId;
    private Integer userId;
}
