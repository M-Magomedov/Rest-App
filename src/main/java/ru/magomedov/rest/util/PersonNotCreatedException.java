package ru.magomedov.rest.util;

//Исключение, когда пользователь не был создан
public class PersonNotCreatedException extends RuntimeException {
    public PersonNotCreatedException(String msg) {
        super(msg);
    }
}
