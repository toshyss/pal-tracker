package io.pivotal.pal.tracker;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo) {
        this.timeEntriesRepo = timeEntriesRepo;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry created = timeEntriesRepo.create(timeEntry);

        ResponseEntity response = new ResponseEntity<>(created, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
        ResponseEntity response;
        TimeEntry readEntry = timeEntriesRepo.find(timeEntryId);

        if (readEntry == null) {
            response = new ResponseEntity<>(readEntry, HttpStatus.NOT_FOUND);
            return response;
        }

        response = new ResponseEntity<>(readEntry, HttpStatus.OK);
        return response;
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> listEntry = timeEntriesRepo.list();

        ResponseEntity response = new ResponseEntity(listEntry, HttpStatus.OK);
        return response;
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry timeEntry) {
        ResponseEntity response;
        TimeEntry updateEntry = timeEntriesRepo.update(timeEntryId, timeEntry);

        if(updateEntry == null) {
            response = new ResponseEntity<>(updateEntry, HttpStatus.NOT_FOUND);
            return response;
        }

        response = new ResponseEntity(updateEntry, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable("id") long timeEntryId) {
        timeEntriesRepo.delete(timeEntryId);

        ResponseEntity response = new ResponseEntity(null,HttpStatus.NO_CONTENT);
        return response;
    }
}
