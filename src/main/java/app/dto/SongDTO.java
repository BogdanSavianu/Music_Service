package app.dto;


import app.model.Album;
import app.model.Artist;
import lombok.Data;

@Data
public class SongDTO {

    private String name;
    private Artist author;
    private Album album;
    private Integer duration;
}
