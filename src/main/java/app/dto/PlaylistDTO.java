package app.dto;

import app.model.Song;
import app.model.User;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistDTO {
    private String name;
    private User user;
    private List<Song> songs;
}
