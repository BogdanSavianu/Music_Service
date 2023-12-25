package app.single_point_access;

import app.repository.AlbumRepository;
import app.repository.ArtistRepository;
import app.repository.PlaylistRepository;
import app.repository.SongRepository;
import app.repository.implementation.AlbumRepositoryImpl;
import app.repository.implementation.ArtistRepositoryImpl;
import app.repository.implementation.SongRepositoryImpl;
import app.service.*;
import app.service.implementation.*;

public class ServiceSinglePointAccess {

    private static UserService userService;
    private static PlaylistService playlistService;
    private static SongService songService;
    private static ArtistService artistService;
    private static AlbumService albumService;

    static {
        userService = new UserServiceImpl();
        playlistService = new PlaylistServiceImpl();
        songService = new SongServiceImpl();
        artistService = new ArtistServiceImpl();
        albumService = new AlbumServiceImpl();
    }

    public static UserService getUserService() {
        return userService;
    }

    public static PlaylistService getPlaylistService() {
        return playlistService;
    }

    public static SongService getSongService() {
        return songService;
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static AlbumService getAlbumService() {
        return albumService;
    }
}
