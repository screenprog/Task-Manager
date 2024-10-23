import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String url;
    private LocalDate startDate;
    private LocalDate endingDate;
    private Status taskStatus;
    private String description;

    public Task(String id, String name, String url, LocalDate startDate, LocalDate endingDate, Status taskStatus, String description) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.startDate = startDate;
        this.endingDate = endingDate;
        this.taskStatus = taskStatus;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %-30s | %-10s | %-10s | %-7s |\n Task Description: | %s |\n",id,
                name, url, startDate, endingDate, taskStatus, description);
//        return id + ", " + name +
//                ", " + url +
//                ", " + startDate +
//                ", " + endingDate +
//                ", " + taskStatus +
//                ", " + description ;
    }

    enum Status{
        PENDING, COMPLETED
    }

}
