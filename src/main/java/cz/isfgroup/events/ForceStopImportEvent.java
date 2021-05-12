package cz.isfgroup.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForceStopImportEvent {
    private String actionId;
    private String username;
    private boolean deleteNodes;
}
