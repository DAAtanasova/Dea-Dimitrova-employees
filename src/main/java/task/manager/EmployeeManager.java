package task.manager;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import task.domain.Colleague;
import task.domain.Employee;
import task.domain.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class EmployeeManager {

    public String findResult(InputStream inputStream) throws Exception {

        Map<Long, List<Employee>> projectEmployeeMap = getDataFromFile(inputStream);
        List<Project> projects = separateEmployeesInProjects(projectEmployeeMap);

        Colleague longestWorkingColleagues = findLongestWorkingPeriod(projects);
        return longestWorkingColleagues.getFirstEmployeeId() + "," + longestWorkingColleagues.getSecondEmployeeId() + "," + longestWorkingColleagues.getWorkingDays();
    }

    private Colleague findLongestWorkingPeriod(List<Project> projects) {
        Colleague longestWorkingColleagues = null;
        for (Project project : projects) {
            Colleague longestWorkingColleaguesForProject = project.getColleagues().stream()
                    .max(Comparator.comparing(Colleague::getWorkingDays))
                    .orElse(null);

            //This will be the case when there is only one employee specified for a project
            if (longestWorkingColleaguesForProject == null) {
                continue;
            }

            if (longestWorkingColleagues == null) {
                longestWorkingColleagues = longestWorkingColleaguesForProject;
                continue;
            }

            longestWorkingColleagues = longestWorkingColleagues.getWorkingDays() > longestWorkingColleaguesForProject.getWorkingDays() ?
                    longestWorkingColleagues : longestWorkingColleaguesForProject;
        }

        return longestWorkingColleagues;
    }

    private List<Project> separateEmployeesInProjects(Map<Long, List<Employee>> projectEmployeeMap) {
        List<Project> projectColleaguesMap = new ArrayList<>();

        for (Map.Entry<Long, List<Employee>> project : projectEmployeeMap.entrySet()) {
            List<String> proceededCombinations = new ArrayList<>();

            List<Colleague> colleagues = new ArrayList<>();
            for (Employee firstEmpl : project.getValue()) {
                for (Employee secondEmpl : project.getValue()) {

                    //We need to skip the combination of a same employee
                    if (firstEmpl.getEmployeeId() == secondEmpl.getEmployeeId()) {
                        continue;
                    }

                    //We will find the smaller and bigger Ids in order to keep tracking of a combinations in a similar way
                    //For example: Employee with id 1 and Employee with id 2
                    //The combination between them for the current project should be only one: 1_2
                    long smallerId = Math.min(firstEmpl.getEmployeeId(), secondEmpl.getEmployeeId());
                    long biggerId = Math.max(firstEmpl.getEmployeeId(), secondEmpl.getEmployeeId());
                    String emplCombination = smallerId + "_" + biggerId;

                    if (proceededCombinations.contains(emplCombination)) {
                        continue;
                    }

                    proceededCombinations.add(emplCombination);

                    DateTime commonDateFrom = firstEmpl.getDateFrom().isBefore(secondEmpl.getDateFrom()) ?
                            secondEmpl.getDateFrom() : firstEmpl.getDateFrom();
                    DateTime commonDateTo = firstEmpl.getDateTo().isBefore(secondEmpl.getDateTo()) ?
                            firstEmpl.getDateTo() : secondEmpl.getDateTo();

                    Colleague colleague = new Colleague(firstEmpl.getEmployeeId(), secondEmpl.getEmployeeId(), commonDateFrom, commonDateTo);
                    colleagues.add(colleague);
                }
            }
            projectColleaguesMap.add(new Project(project.getKey(), colleagues));
        }

        return projectColleaguesMap;
    }

    private Map<Long, List<Employee>> getDataFromFile(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = null;
        Map<Long, List<Employee>> projectEmployeesMap = new HashMap<>();

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String itemRow = null;
            int itemRowIndex = 0;
            while ((itemRow = bufferedReader.readLine()) != null) {
                if (++itemRowIndex <= 1) {
                    continue;
                }

                String[] elements = itemRow.split(",");
                Long emplId = Long.valueOf(elements[0]);
                Long projectID = Long.valueOf(elements[1]);
                DateTime dateFrom = parseDate(elements[2]);
                DateTime dateTo;
                if (elements.length >= 4) {
                    dateTo = parseDate(elements[3]);
                } else {
                    dateTo = DateTime.now();
                }

                final Employee employee = new Employee(emplId, dateFrom, dateTo);
                if (projectEmployeesMap.containsKey(projectID)) {
                    projectEmployeesMap.get(projectID).add(employee);
                } else {
                    projectEmployeesMap.put(projectID, new ArrayList<Employee>() {{
                        add(employee);
                    }});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Reading CSV failed.", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return projectEmployeesMap;
    }

    private DateTime parseDate(String date) throws Exception {
        if (!StringUtils.hasLength(date)) {
            return DateTime.now();
        }

        List<String> patternList = new ArrayList<String>() {{
            add("yyyy-MM-dd");
            add("MM/dd/yyyy");
            add("dd.MM.yyyy");
            add("dd-MM-yyyy");
            add("MM/DD/YY");
            add("DD/MM/YY");
            add("YY/MM/DD");
            add("Month D, Yr");
            add("M/D/YY");
            add("D/M/YY");
            add("YY/M/D");
            add("MMDDYY");
        }};

        for (String pattern : patternList) {
            try {
                return DateTimeFormat.forPattern(pattern).parseDateTime(date);
            } catch (IllegalFieldValueException e) {
                e.printStackTrace();
                throw new Exception("Unsupported date format");
            }
        }

        return null;
    }
}
