package app.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@ToString
@NamedQueries(
        {
                @NamedQuery(name = "findSongByName", query = "from Song song where song.name = :name"),
                @NamedQuery(name = "findSongById", query = "from Song song where song.id = :id"),
                @NamedQuery(name = "findAllSongs", query = "from Song ")
        }
)
public class Song implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer duration;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Artist author;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Album album;
}
