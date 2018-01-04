package palash.watermelon.letsremind.repository.model;

/*
 * Created by 849501 on 10/30/2017.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// Database or Network or both
@Entity(tableName = "worklist")
public class WorkListRepoModel {

    @PrimaryKey
    @NonNull
    private String taskId;

    private String taskSubject;

    private String taskDescription;

    private String createdBy;

    private String createdTime;

    public String getTaskId() {
        return taskId;
    }

    public String getTaskSubject() {
        return taskSubject;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setTaskSubject(String taskSubject) {
        this.taskSubject = taskSubject;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
