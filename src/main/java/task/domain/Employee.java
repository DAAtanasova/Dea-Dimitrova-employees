package task.domain;

import org.joda.time.DateTime;

public class Employee {

    private long employeeId;

    private DateTime dateFrom;

    private DateTime dateTo;

//    private List<Colleague> colleagueList = new ArrayList<>();

    public Employee(long employeeId, DateTime dateFrom, DateTime dateTo) {
        this.employeeId = employeeId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public DateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo) {
        this.dateTo = dateTo;
    }

//    private void addColleague(long employeeId, DateTime dateFrom, DateTime dateTo){
//        DateTime commonDateFrom = this.dateFrom.isBefore(dateFrom) ? dateFrom : this.dateFrom;
//        DateTime commonDateTo = this.dateTo.isBefore(dateTo) ? dateTo : this.dateTo;
//
//        Colleague colleague = new Colleague(employeeId,commonDateFrom,commonDateTo);
//        this.colleagueList.add(colleague);
//    }
}
