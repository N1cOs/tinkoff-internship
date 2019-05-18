package ru.tinkoff.fintech.notes.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.fintech.notes.domain.Record;
import ru.tinkoff.fintech.notes.exception.ApiException;
import ru.tinkoff.fintech.notes.service.NoteBook;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/record")
public class RecordController {

    private final NoteBook noteBook;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Record getRecord(@PathVariable Integer id) {
        return noteBook.getRecord(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Record> getAllRecords() {
        return noteBook.getAllRecords();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveRecord(@Valid @RequestBody Record record) {
        record = noteBook.saveRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRecord(@Valid @RequestBody Record record) {
        if (record.getId() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.record.noId");
        }
        return ResponseEntity.ok().body(noteBook.updateRecord(record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecord(@PathVariable Integer id) {
        noteBook.removeRecord(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
