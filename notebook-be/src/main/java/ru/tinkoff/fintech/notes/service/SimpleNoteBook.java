package ru.tinkoff.fintech.notes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.fintech.notes.domain.Record;
import ru.tinkoff.fintech.notes.exception.ApiException;
import ru.tinkoff.fintech.notes.repository.RecordRepository;

@Service
@RequiredArgsConstructor
public class SimpleNoteBook implements NoteBook {

    private final RecordRepository recordRepository;

    @Override
    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public void removeRecord(Integer recordId) {
        try{
            recordRepository.deleteById(recordId);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.record.notFound", recordId);
        }
    }

    @Override
    @Transactional
    public Record updateRecord(Record record) {
        Record savedRecord = recordRepository.findById(record.getId()).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.record.notFound", record.getId()));

        savedRecord.setDate(record.getDate());
        savedRecord.setTitle(record.getTitle());
        savedRecord.setText(record.getText());
        return savedRecord;
    }

    @Override
    public Record getRecord(Integer recordId) {
        return recordRepository.findById(recordId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.record.notFound", recordId));
    }

    @Override
    public Iterable<Record> getAllRecords() {
        return recordRepository.findAll();
    }
}
