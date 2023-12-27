package app.dto;


import app.model.Album;
import app.model.Artist;
import app.model.Playlist;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private Set<Playlist> playlists;
}