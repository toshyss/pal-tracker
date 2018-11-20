package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    // no id in TimeEntry
    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long id);

    List<TimeEntry> list();

    TimeEntry update(long id, TimeEntry timeEntry);

    void delete(long id);
}
