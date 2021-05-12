package cz.isfgroup.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateXmlFileEvent {
    private String targetDirectory;
    private String fileAlfresco;
}
