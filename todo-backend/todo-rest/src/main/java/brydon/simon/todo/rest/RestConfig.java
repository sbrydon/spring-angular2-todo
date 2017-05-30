package brydon.simon.todo.rest;

import brydon.simon.todo.service.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Configuration
@Import(ServiceConfig.class)
public class RestConfig {
    private static final Logger logger = LoggerFactory.getLogger(RestConfig.class);

    public RestConfig() {
        // Implement ServletContextListener if this is set too late
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        logger.info("Time zone set to 'UTC'");
    }
}
