package palash.watermelon.letsremind.repository.model;

import palash.watermelon.letsremind.pojo.WorkList;
import palash.watermelon.letsremind.utility.Mapper;

/*
 * Created by 849501 on 10/30/2017.
 */

public class WorkListMapper implements Mapper<WorkListRepoModel, WorkList> {

    @Override
    public WorkList mapTo(WorkListRepoModel workListRepoModel) {
        return new WorkList(workListRepoModel.getTaskId(),
                workListRepoModel.getTaskSubject(),
                workListRepoModel.getTaskDescription(),
                workListRepoModel.getCreatedBy(),
                workListRepoModel.getCreatedTime());
    }

}
