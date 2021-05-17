package cz.isfgroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FrontaMember {

    private Long no;
    private String noderef;
    private String edid;
    private String davkaid;
    private String status;
    private Date ts;

}
