package sharing.dormitory.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Table(name = "USERS")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "TELEPHONE", length = 12, nullable = false)
    private String telephone;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DORMITORY", referencedColumnName="id")
    private Dormitory dormitory;

    @Column(name = "RATING", nullable = false)
    @Range(min = 0, max = 5)
    private Float rating = 0.0f;

    @Column(name = "ROLES")
    private String roles = "";

    @Column(name = "PERMISSIONS")
    private String permissions = "";

    @Column(name = "ACTIVE")
    @ColumnDefault("1")
    private int active = 1;

    public User(String username, String password, String name, String surname, String email, String telephone, Dormitory dormitory, Float rating){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.dormitory = dormitory;
        this.rating = rating;
    }

    public User() {

    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}