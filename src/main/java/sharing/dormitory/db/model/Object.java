package sharing.dormitory.db.model;

import lombok.Data;
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
    @Lob
    private Byte[] image;
    @Enumerated(EnumType.STRING)
    @Column(name = "OBJECT_STATE")
    private ObjectState state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
