package sharing.dormitory.db.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import sharing.dormitory.db.enm.ObjectState;

import javax.persistence.*;

@Data
@Entity
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
    @Column(name = "OBJECT_STATE")
    private ObjectState state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
