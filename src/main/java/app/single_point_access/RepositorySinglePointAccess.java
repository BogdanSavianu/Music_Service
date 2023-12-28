package app.single_point_access;

import app.repository.*;
import app.repository.implementation.*;
import app.service.performance.SQLProcedurePerformanceService;

public class RepositorySinglePointAccess {

    private static UserRepository userRepository;
    private static SongRepository songRepository;
    private static PlaylistRepository playlistRepository;
    private static ArtistRepository artistRepository;
    private static AlbumRepository albumRepository;


    static {
        userRepository = new UserRepositoryImpl();
        songRepository = new SongRepositoryImpl();
        playlistRepository = new PlaylistRepositoryImpl();
        artistRepository = new ArtistRepositoryImpl();
        albumRepository = new AlbumRepositoryImpl();
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static SongRepository getSongRepository() {
        return songRepository;
    }

    public static PlaylistRepository getPlaylistRepository() {
        return playlistRepository;
    }

    public static ArtistRepository getArtistRepository() {
        return artistRepository;
    }

    public static AlbumRepository getAlbumRepository() {
        return albumRepository;
    }
}
