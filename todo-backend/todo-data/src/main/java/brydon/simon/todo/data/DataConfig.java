package brydon.simon.todo.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { DataConfig.class })
@EntityScan(basePackageClasses = { DataConfig.class, Jsr310JpaConverters.class })
@ComponentScan
public class DataConfig {
}
