package ru.magomedov.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.magomedov.rest.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
