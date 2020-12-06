package sharing.dormitory.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "DORMITORY")
@Entity
public class Dormitory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "dormitory", fetch = FetchType.LAZY)
    private List<User> userList;

    public Dormitory(String name, String address, String description){
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Dormitory() {

    }
}
