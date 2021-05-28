package cz.isfgroup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ecg {

    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String edId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nodeRef;

    private String siteNodeRef;

    public Ecg(String edId, String nodeRef, String siteNodeRef) {
        this.edId = edId;
        this.nodeRef = nodeRef;
        this.siteNodeRef = siteNodeRef;
    }

}
