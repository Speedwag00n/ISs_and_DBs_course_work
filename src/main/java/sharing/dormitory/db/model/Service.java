package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String description;
    @Lob
    private Byte[] image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
