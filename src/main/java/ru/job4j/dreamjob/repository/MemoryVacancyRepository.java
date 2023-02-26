package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Intern Java Developer - 500$",
                LocalDateTime.now()));
        save(new Vacancy(0, "Junior Java Developer", "Junior Java Developer - 750$",
                LocalDateTime.of(2023, 01, 23, 16, 55)));
        save(new Vacancy(0, "Junior+ Java Developer", "Junior+ Java Developer - 1000$",
                LocalDateTime.of(2023, 02, 25, 18, 15)));
        save(new Vacancy(0, "Middle Java Developer", "Middle Java Developer - 2000$",
                LocalDateTime.of(2022, 12, 30, 22, 59)));
        save(new Vacancy(0, "Middle+ Java Developer", "Middle+ Java Developer - 2500$",
                LocalDateTime.of(2023, 02, 24, 8, 11)));
        save(new Vacancy(0, "Senior Java Developer", "Senior Java Developer - 5000$",
                LocalDateTime.of(2023, 01, 01, 15, 00)));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}