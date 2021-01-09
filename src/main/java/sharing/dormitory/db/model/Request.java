package sharing.dormitory.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String content;
    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User user;
}
