package palash.watermelon.letsremind.pojo;

/*
 * Created by 849501 on 10/30/2017.
 */

public class WorkList {

    private String taskId;

    private String taskSubject;

    private String taskDescription;

    private String createdBy;

    private String createdTime;

    public WorkList(String taskId, String taskSubject, String taskDescription, String createdBy, String createdTime) {
        this.taskId = taskId;
        this.taskSubject = taskSubject;
        this.taskDescription = taskDescription;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
    }
}
