package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@NamedQueries(
        {
                @NamedQuery(name = "findAlbumByName", query = "from Album album where album.name = :name"),
                @NamedQuery(name = "findAlbumById", query = "from Album album where album.id = :id"),
                @NamedQuery(name = "findAllAlbums", query = "from Album")
        }
)
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Artist author;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song){
        song.setAlbum(this);
        //songs.add(song);
    }
}
