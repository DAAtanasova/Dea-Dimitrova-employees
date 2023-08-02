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

    //    @GET
//    @Path("two")
//    @Produces(MediaType.APPLICATION_JSON)
//    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
////        // Must set the content type first
////        res.setContentType("text/html");
////        // Now obtain a PrintWriter to insert HTML into
////        PrintWriter out = res.getWriter();
////        out.println("<html><head><title>" +
////                "Hello World!</title></head>");
////        out.println("<body><h1>Hello World!</h1></body></html>");
//
//        System.out.println("Testing here ");
//
//
//
//    }

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@FormDataParam("file") InputStream fileInputStream,
                     @FormDataParam("file") FormDataContentDisposition contentDisposition) throws Exception{
        System.out.println("Testingggggg");
        System.out.println("input stream: " + fileInputStream);
        System.out.println("test manager: " + this.employeeManager);
        System.out.println("test manager2: " + new EmployeeManager());

        EmployeeManager employeeManager = new EmployeeManager();
        return employeeManager.findResult(fileInputStream);
    }

}
