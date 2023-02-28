package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Intern Java Developer - 500$",
                LocalDateTime.now(), true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Junior Java Developer - 750$",
                LocalDateTime.of(2023, 01, 23, 16, 55), true, 2, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Junior+ Java Developer - 1000$",
                LocalDateTime.of(2023, 02, 25, 18, 15), true, 1, 0));
        save(new Vacancy(0, "Middle Java Developer", "Middle Java Developer - 2000$",
                LocalDateTime.of(2022, 12, 30, 22, 59), true, 3, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Middle+ Java Developer - 2500$",
                LocalDateTime.of(2023, 02, 24, 8, 11), true, 1, 0));
        save(new Vacancy(0, "Senior Java Developer", "Senior Java Developer - 5000$",
                LocalDateTime.of(2023, 01, 01, 15, 00), true, 2, 0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.incrementAndGet());
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
                vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate(), vacancy.getVisible(),
                vacancy.getCityId(), vacancy.getFileId())) != null;
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