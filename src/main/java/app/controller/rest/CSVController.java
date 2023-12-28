package app.controller.rest;

import app.model.User;
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

            while ((line = br.readLine ()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                User user = new User();

                // The order in csv could be changed
                // This implementation is only for template purpose
                user.setName(values[0]);
                user.setPassword(values[1]);
                user.setEmail(values[2]);
                user.setPassword(values[3]);

                users.add(user);
            }

            for (User userIterator : users) {
                userService.save(userIterator);
            }

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);

            // TODO - treat exception case
        } catch (IOException e) {

            // TODO - treat exception case
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        } catch (URISyntaxException e) {

            // TODO - treat exception case
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    // You can send the order of fields that must appear in csv
    // Add a new parameter for header
    @PostMapping("/export_user")
    public ResponseEntity<Boolean> exportUserToCSV(@RequestBody String filename) {

        try {
            File file = FileUtil.getAndCreateFileFromResourcesDirectory(filename);
            FileWriter fileWriter = new FileWriter(file);
            String header = "name,password\n";
            fileWriter.write(header);


            List<User> users = userService.findAll();
            for (User userIterator : users) {
                String userDetails = "" + userIterator.getName() + "," + userIterator.getPassword() + "\n";
                fileWriter.write(userDetails);
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

