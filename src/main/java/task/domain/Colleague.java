package task.domain;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Colleague {

    private Long firstEmployeeId;

    private Long secondEmployeeId;

    private int workingDays;

    public Colleague(Long firstEmployeeId, Long secondEmployeeId, DateTime commonDateFrom, DateTime commonDateTo) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.workingDays = Days.daysBetween(commonDateFrom,commonDateTo).getDays();
    }

    public Long getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(Long firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public Long getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(Long secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }
}
