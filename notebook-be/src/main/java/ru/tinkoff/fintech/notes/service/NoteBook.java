package ru.tinkoff.fintech.notes.service;

import ru.tinkoff.fintech.notes.domain.Record;

public interface NoteBook {

    Record saveRecord(Record record);

    void removeRecord(Integer recordId);

    Record updateRecord(Record record);

    Record getRecord(Integer recordId);

    Iterable<Record> getAllRecords();

    
}
