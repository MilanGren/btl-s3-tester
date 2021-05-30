package cz.isfgroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FrontaMember {

    private Long no;
    private String noderef;
    private String edid;
    private String davkaid;
    private String status;
    private Date ts;

    public FrontaMember(String noderef, String edid, String davkaid, String status, Date ts) {
        this.noderef = noderef;
        this.edid = edid;
        this.davkaid = davkaid;
        this.status = status;
        this.ts = ts;
    }

}
