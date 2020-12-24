package sharing.dormitory.db.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import sharing.dormitory.db.enm.ObjectState;

import javax.persistence.*;

@Data
@Entity
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Object {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private Byte[] image;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "OBJECT_STATE")
    private ObjectState state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
