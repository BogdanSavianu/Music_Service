package app;

import app.controller.gui.LoginController;
//import app.model.Address;
import app.model.*;
import app.repository.SongRepository;
import app.repository.implementation.UserRepositoryImpl;
import app.service.*;
import app.single_point_access.RepositorySinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Properties;



@SpringBootApplication
public class Main {

    private static final String APPLICATION_CONFIGURATION_FILE = "app.configuration.properties";

    public static void main(String[] args) {

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(APPLICATION_CONFIGURATION_FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            // Decide app mode from file
            String mode = properties.getProperty("mode");

            if (mode.equals("web")) {
                SpringApplication.run(Main.class, args);
            } else {
                LoginController loginController = new LoginController();
                loginController.startLogic();
            }
        } catch (Exception ex) {
            System.out.println("Error at starting application...");
            ex.printStackTrace();
        }

         //Test implementation
//        User user = new User();
//        user.setName("Bogdan");
//        user.setPassword("macmac");
//        user.setEmail("bogdan.2.savianu@gmail.com");
//        user.setPlaylists(null);



        //TODO de aici

//        UserService userService = ServiceSinglePointAccess.getUserService();
////        User savedUser = userService.save(user);
////        userService.deleteByID(2);
////        userService.deleteByID(3);
////        userService.deleteByID(4);
////        userService.deleteByID(5);
//        PlaylistService playlistService = ServiceSinglePointAccess.getPlaylistService();
////        playlistService.deleteByID(6);
////        User savedUser = userService.save(user);
////        userService.addPlaylistToUser(7,"Rock");

//        AlbumService albumService = ServiceSinglePointAccess.getAlbumService();
//        ArtistService artistService = ServiceSinglePointAccess.getArtistService();
////        Artist artist = new Artist();
////        artist.setName("The Offspring");
//        Song song = new Song();
//        song.setName("The Unforgiven II");
//        song.setDuration(396);
//        songService.save(song);
//        Album album = new Album();
//        album.setName("Reload");
//        album.addSong(song);
//        song.setAlbum(album);
//        artistService.addAlbumToArtist("Metallica",album);
//        artistService.addSongToArtist("Metallica",song);
//
//        playlistService.addSongToPlaylist(13,song);


        //TODO pana aici

//        User user = new User();
//        user.setName("Test");
//        user.setPassword("1234");
//        user.setSalary(22);
//        Address address = new Address();
//        address.setCity("Cluj-Napoca");
//        address.setStreet("Dorobantilor");
//        user.setAddress(address);
//
//        UserService userService = ServiceSinglePointAccess.getUserService();
//        User savedUser = userService.save(user);
//
//        Movie movie = new Movie();
//        movie.setName("Infinity War");
//        movie.setPrice(34.0);
//
//        MovieRepository movieRepository = RepositorySinglePointAccess.getMovieRepository();
//        Movie savedMovie = movieRepository.save(movie);

//        userService.createReservation(savedUser, savedMovie, new Date());
//        System.out.println(userService.findById(savedUser.getId()));
//        UserService userService = ServiceSinglePointAccess.getUserService();
//        System.out.println(userService.findById(1));
    }
}
