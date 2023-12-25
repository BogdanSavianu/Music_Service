package app.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@NamedQueries(
        {
                @NamedQuery(name = "findArtistByName", query = "from Artist artist where artist.name = :name"),
                @NamedQuery(name = "findArtistById", query = "from Artist artist where artist.id = :id"),
                @NamedQuery(name = "findAllArtists", query = "from Artist ")
        }
)
public class Artist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Song> songs;
//
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Album> albums;
//
//    public void addSong(Song song){
//        songs.add(song);
//    }
//    public void addAlbum(Album album){
//        albums.add(album);
//    }
}
