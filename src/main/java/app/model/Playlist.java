package app.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(
        {
                @NamedQuery(name = "findPlaylistByName", query = "from Playlist playlist where playlist.name = :name"),
                @NamedQuery(name = "findPlaylistById", query = "from Playlist playlist where playlist.id = :id"),
                @NamedQuery(name = "findAllPlaylists", query = "from Playlist ")
        }
)
public class Playlist {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song){
        songs.add(song);
    }
}
