package task;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class EmployeeApplication extends ResourceConfig {

    public EmployeeApplication() {
        packages(getClass().getPackage().getName());

        register(MultiPartFeature.class);
    }
}
