package ru.tinkoff.fintech.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.fintech.notes.domain.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {

}
