package app.model;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries(
        {
                @NamedQuery(name = "findUserByName", query = "from User pers where pers.name = :name"),
                @NamedQuery(name = "findUserByNameAndPassword", query = "from User pers where pers.name = :name and pers.password=:password"),
                @NamedQuery(name = "findUserById", query = "from User pers where pers.id = :id"),
                @NamedQuery(name = "findAllUsers", query = "from User")
        }
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Playlist> playlists;

    public void addPlaylist(Playlist playlist){
     //   playlist.setUser(this);
        playlists.add(playlist);
    }
}
