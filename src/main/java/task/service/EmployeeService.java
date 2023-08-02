package task.service;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.domain.Project;
import task.manager.EmployeeManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

@Path("employee")
@Service
public class EmployeeService {

    @Autowired
    private EmployeeManager employeeManager;

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@FormDataParam("file") InputStream fileInputStream,
                     @FormDataParam("file") FormDataContentDisposition contentDisposition) throws Exception{
        EmployeeManager employeeManager = new EmployeeManager();
        return employeeManager.findLongestWorkingColleagues(fileInputStream);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> testUpload(@FormDataParam("file") InputStream fileInputStream,
                                    @FormDataParam("file") FormDataContentDisposition contentDisposition) throws Exception{
        EmployeeManager employeeManager = new EmployeeManager();
        return employeeManager.findColleaguesWorkingDays(fileInputStream);
    }

}
