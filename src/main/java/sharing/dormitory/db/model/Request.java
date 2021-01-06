package sharing.dormitory.db.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String content;
    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User user;
    @Column(name = "AGREED_TIME")
    @CreationTimestamp
    private LocalDateTime agreedTime;
}
