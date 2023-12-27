package app.dto;


import app.model.Album;
import app.model.Artist;
import lombok.Data;

@Data
public class AlbumDTO {

    private String name;
    private Artist author;
}
