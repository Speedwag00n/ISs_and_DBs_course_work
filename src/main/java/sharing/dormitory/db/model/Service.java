package sharing.dormitory.db.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private Byte[] image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
