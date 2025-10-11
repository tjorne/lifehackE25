package app.entities.gratitudeJournal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JournalLog {
    private long logId;
    private LocalDate logDate;
    private List<JournalItem> items = new ArrayList<>();

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public List<JournalItem> getItems() {
        return items;
    }

    public void setItems(List<JournalItem> items) {
        this.items = items;
    }
}