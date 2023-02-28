package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements  CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Vasilii Mitin", "Intern Java Developer",
                LocalDateTime.of(2022, 8, 11, 16, 55), 3, 0));
        save(new Candidate(0, "Artem Kakoyto", "Junior Java Developer",
                LocalDateTime.of(2023, 1, 10, 15, 00), 2, 0));
        save(new Candidate(0, "Kseniya Parshina", "Junior+ Java Developer",
                LocalDateTime.of(2023, 2, 1, 11, 23), 3, 0));
        save(new Candidate(0, "Igor Gatman", "Middle Java Developer",
                LocalDateTime.of(2023, 2, 25, 19, 16), 3, 0));
        save(new Candidate(0, "Bogdan Prokopenko", "Middle+ Java Developer",
                LocalDateTime.of(2019, 6, 22, 16, 15), 2, 0));
        save(new Candidate(0, "Petr Arsentev", "Senior Java Developer",
                LocalDateTime.of(2020, 11, 22, 10, 00), 1, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.incrementAndGet());
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
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate(),
                candidate.getCityId(), candidate.getFileId())) != null;
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
