package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import sharing.dormitory.db.enm.Status;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NamedStoredProcedureQuery(
        name = "getOffersInDormitory",
        procedureName = "GET_OFFERS_IN_DORMITORY",
        resultClasses = {Offer.class},
        parameters = {
                @StoredProcedureParameter(name = "dormitoryId", mode = ParameterMode.IN, type = Integer.class)
        }
)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "STATUS")
    private Status offerStatus;
    @Column(name = "CREATION_DATE")
    private OffsetDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User user;
}
