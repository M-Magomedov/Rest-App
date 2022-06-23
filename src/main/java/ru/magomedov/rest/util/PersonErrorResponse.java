package ru.magomedov.rest.util;

//в этом классе будем обрабатывать все ошибки, которые могут возникнуть
public class PersonErrorResponse {
    private String message;  //сообщение ошибки
    private Long timestamp;  //время когда произошла ошибка

    public PersonErrorResponse(String message, Long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
