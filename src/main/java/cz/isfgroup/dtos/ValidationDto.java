package cz.isfgroup.dtos;

import lombok.Getter;

@Getter
public class ValidationDto {

    private final boolean valid;
    private final String path;
    private final String message;

    public ValidationDto(boolean valid, String path, String message) {
        this.valid = valid;
        this.path = path;
        this.message = message;
    }
}
