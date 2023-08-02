package task.service;


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.EmployeeManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

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
        return employeeManager.findResult(fileInputStream);
    }

}
