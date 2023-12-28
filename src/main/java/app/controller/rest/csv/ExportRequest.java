package app.controller.rest.csv;

import lombok.Data;

@Data
public class ExportRequest {
    private String filename;
    private String header;
}
