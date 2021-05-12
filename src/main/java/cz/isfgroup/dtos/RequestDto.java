package cz.isfgroup.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {

    private String database = "";

    private String targetNodeRefID;

    private String prefix;

    private String xsdFilePath;

    private String sourceDirectory;

    private String batchMode = "YES";

    private String replace = "NO";

    private int batches = -1;

}
