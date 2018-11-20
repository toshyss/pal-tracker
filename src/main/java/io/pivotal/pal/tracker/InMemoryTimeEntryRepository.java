package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//In memory DB
public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    HashMap<Long, TimeEntry> repo = new HashMap<>();
    long seq = 0;

    // no id in TimeEntry
    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        TimeEntry newEntry = new TimeEntry();
        newEntry.setUserId(timeEntry.getUserId());
        newEntry.setHours(timeEntry.getHours());
        newEntry.setProjectId(timeEntry.getProjectId());
        newEntry.setDate(timeEntry.getDate());

        seq = seq + 1;
        newEntry.setId(seq);

        repo.put(seq, newEntry);
        return newEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return repo.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<>(repo.values());
        return list;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (find(id) == null) {
            return null;
        }
        timeEntry.setId(id);
        repo.put(id, timeEntry);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        repo.remove(id);
    }
}
