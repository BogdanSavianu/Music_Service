package app.controller.rest.csv;

import app.model.Artist;
import app.model.Song;
import app.model.User;
import app.service.AlbumService;
import app.service.ArtistService;
import app.service.SongService;
import app.service.UserService;
import app.single_point_access.ServiceSinglePointAccess;
import app.util.FileUtil;
import com.google.common.io.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


// All imported/exported files are taken form resources directory ONLY
@RestController
@RequestMapping("/csv")
public class CSVController {

    private UserService userService = ServiceSinglePointAccess.getUserService();
    private ArtistService artistService = ServiceSinglePointAccess.getArtistService();

    // TO DO
    // For project take in consideration that a csv file could have different order of columns
    // Do it for at least 2 entities - import and export
    // Take in consideration data validation or if some data already exists
    // Extract duplicate logic and improve it based on template below
    //
    // For demo - import at least 25 entities and export all entities
    //
    @PostMapping("/import_user")
    public ResponseEntity<Boolean> importUserFromCSV(@RequestBody String filename) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);

            // Read data in a buffer
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<User> users = new ArrayList<>();
            String line;
            boolean firstLine = true;
            String[] headers = null;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    // Assume the first line contains headers in order
                    firstLine = false;
                    headers = values;
                    continue;
                }

                User user = new User();

                // Dynamically map attributes based on the order in the CSV file
                for (int i = 0; i < values.length; i++) {
                    if (i < headers.length) {
                        switch (headers[i].toLowerCase()) {
                            case "name":
                                user.setName(values[i]);
                                break;
                            case "password":
                                user.setPassword(values[i]);
                                break;
                            case "email":
                                user.setEmail(values[i]);
                                break;
                            default:
                                break;
                        }
                    }
                }

                users.add(user);
            }

            for (User userIterator : users) {
                userService.save(userIterator);
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }




    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_user")
    public ResponseEntity<Boolean> exportUserToCSV(@RequestBody ExportRequest exportRequest) {

        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(exportRequest.getFilename());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(exportRequest.getHeader());

            List<User> users = userService.findAll();
            for (User userIterator : users) {
                StringBuilder userDetails = new StringBuilder();
                String[] fields = exportRequest.getHeader().split(",");
                for (String field : fields) {
                    switch (field.trim()) {
                        case "name":
                            userDetails.append(userIterator.getName()).append(",");
                            break;
                        case "email":
                            userDetails.append(userIterator.getEmail()).append(",");
                            break;
                        case "password":
                            userDetails.append(userIterator.getPassword()).append(",");
                            break;
                    }
                }
                userDetails.deleteCharAt(userDetails.length() - 1); // Remove trailing comma
                userDetails.append("\n");
                fileWriter.write(userDetails.toString());
            }

            fileWriter.close();

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (IOException ex) {

            // TODO - treat exception case

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {

            // TODO - treat exception case

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }





    @PostMapping("/import_artist")
    public ResponseEntity<Boolean> importArtistFromCSV(@RequestBody String filename) {
        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);

            // Read data in a buffer
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<Artist> artists = new ArrayList<>();
            String line;
            boolean firstLine = true;
            String[] headers = null;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    // Assume the first line contains headers in order
                    firstLine = false;
                    headers = values;
                    continue;
                }

                Artist artist = new Artist();

                // Dynamically map attributes based on the order in the CSV file
                for (int i = 0; i < values.length; i++) {
                    if (i < headers.length) {
                        switch (headers[i].toLowerCase()) {
                            case "name":
                                artist.setName(values[i]);
                                break;
                            default:
                                break;
                        }
                    }
                }

                artists.add(artist);
            }

            for (Artist artistIterator : artists) {
                artistService.save(artistIterator);
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_artist")
    public ResponseEntity<Boolean> exportArtistToCSV(@RequestBody ExportRequest exportRequest) {

        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(exportRequest.getFilename());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(exportRequest.getHeader());

            List<Artist> artists = artistService.findAll();
            for (Artist artistIterator : artists) {
                StringBuilder artistDetails = new StringBuilder();
                String[] fields = exportRequest.getHeader().split(",");
                for (String field : fields) {
                    switch (field) {
                        case "name":
                            artistDetails.append(artistIterator.getName()).append(",");
                            break;
                    }
                }
                if (artistDetails.length() > 0) {
                    artistDetails.deleteCharAt(artistDetails.length() - 1); // Remove trailing comma
                } // Remove trailing comma
                artistDetails.append("\n");
                fileWriter.write(artistDetails.toString());
            }

            fileWriter.close();

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (IOException ex) {

            // TODO - treat exception case

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {

            // TODO - treat exception case

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}

