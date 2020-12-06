package sharing.dormitory.db.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = "DORMITORY")
@Entity
public class Dormitory {
    @Id
    @Column(name = "ID")
    private long id;

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
