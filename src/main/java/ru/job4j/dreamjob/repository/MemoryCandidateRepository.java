package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryCandidateRepository implements  CandidateRepository {

    private static MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Vasilii Mitin", "Intern Java Developer",
                LocalDateTime.of(2022, 8, 11, 16, 55)));
        save(new Candidate(0, "Artem Kakoyto", "Junior Java Developer",
                LocalDateTime.of(2023, 1, 10, 15, 00)));
        save(new Candidate(0, "Kseniya Parshina", "Junior+ Java Developer",
                LocalDateTime.of(2023, 2, 1, 11, 23)));
        save(new Candidate(0, "Igor Gatman", "Middle Java Developer",
                LocalDateTime.of(2023, 2, 25, 19, 16)));
        save(new Candidate(0, "Bogdan Prokopenko", "Middle+ Java Developer",
                LocalDateTime.of(2019, 6, 22, 16, 15)));
        save(new Candidate(0, "Petr Arsentev", "Senior Java Developer",
                LocalDateTime.of(2020, 11, 22, 10, 00)));
    }

    public static MemoryCandidateRepository getINSTANCE() {
        return INSTANCE;
    }
    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
