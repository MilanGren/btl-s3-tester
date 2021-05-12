package cz.isfgroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fronta {

    private Long no;

    private String edid;
    private String davkaid;
    private String noderef;
    private String status;

}
