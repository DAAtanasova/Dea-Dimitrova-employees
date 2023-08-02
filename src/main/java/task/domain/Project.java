package task.domain;

import java.util.List;

public class Project {

    private long projectId;

    private List<Colleague> colleagues;

    public Project(long projectId, List<Colleague> colleagues) {
        this.projectId = projectId;
        this.colleagues = colleagues;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<Colleague> getColleagues() {
        return colleagues;
    }

    public void setColleagues(List<Colleague> colleagues) {
        this.colleagues = colleagues;
    }
}
