package me.marquez.upbit.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpbitAPIException extends RuntimeException {

    private String name;
    private String message;

    public UpbitAPIException(String name, String message) {
        super(name + ": " + message);
        this.name = name;
        this.message = message;
    }

}
