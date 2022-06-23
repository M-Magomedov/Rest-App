package ru.magomedov.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.magomedov.rest.models.Person;
import ru.magomedov.rest.services.PeopleService;
import ru.magomedov.rest.util.PersonErrorResponse;
import ru.magomedov.rest.util.PersonNotCreatedException;
import ru.magomedov.rest.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    //метод для создания человека
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {  //если есть ошибка при валидации
            throw new PersonNotCreatedException(errorMsg(bindingResult)); //выбрасываем исключение
        }
        peopleService.save(person);  //сохраняем человека
        return ResponseEntity.ok(HttpStatus.OK); //возвращаем пользователю статус 200
    }

    //метод для изменения данных человека
    @PutMapping
    public ResponseEntity<HttpStatus> updatePerson(@RequestBody @Valid Person person,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {  //если есть ошибка при валидации
            throw new PersonNotCreatedException(errorMsg(bindingResult)); //выбрасываем исключение
        }
        peopleService.save(person); //сохраняем человека
        return ResponseEntity.ok(HttpStatus.OK); //возвращаем пользователю статус 200
    }

    //метод для удаления человека
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable int id) {
        Person person = peopleService.findOne(id);
        if (person == null) {
            throw new PersonNotFoundException();
        }
        peopleService.delete(id); //удаляем человека
        return ResponseEntity.ok(HttpStatus.OK); //возвращаем пользователю статус 200
    }

    //метод для сохранения всех ошибок в одну строку
    public String errorMsg(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors(); //собираем все ошибки в список
        //проходимся по каждой ошибке и собираем все ошибки в одну строку
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" _ ").append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }

    @ExceptionHandler
    //если пользователь не найден, выбрасываем данное исключение
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "По данному id пользователь не найден",  //сообщение
                System.currentTimeMillis()      //время
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //HttpStatus.NOT_FOUND - 404 ошибка
    }

    @ExceptionHandler
    //Если пользователь не был создан, выбрасываем данное исключение
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),  //сообщение
                System.currentTimeMillis()      //время
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        //HttpStatus.BAD_REQUEST - 400 ошибка(что-то произошло на сервере)
    }
}
