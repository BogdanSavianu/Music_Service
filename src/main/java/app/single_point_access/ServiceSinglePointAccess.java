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
import app.service.performance.AppPerformanceService;
import app.service.performance.SQLProcedurePerformanceService;

public class ServiceSinglePointAccess {

    private static UserService userService;
    private static PlaylistService playlistService;
    private static SongService songService;
    private static ArtistService artistService;
    private static AlbumService albumService;
    private static AppPerformanceService appPerformanceService;
    private static SQLProcedurePerformanceService sqlProcedurePerformanceService;

    static {
        userService = new UserServiceImpl();
        playlistService = new PlaylistServiceImpl();
        songService = new SongServiceImpl();
        artistService = new ArtistServiceImpl();
        albumService = new AlbumServiceImpl();
        appPerformanceService = new AppPerformanceService();
        sqlProcedurePerformanceService = new SQLProcedurePerformanceService();
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
    public static AppPerformanceService getAppPerformanceService() {
        return appPerformanceService;
    }

    public static SQLProcedurePerformanceService getSQLProcedurePerformanceService() {
        return sqlProcedurePerformanceService;
    }
}
