package pl.futurecollars.invoicing.db.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;

@Configuration
@Slf4j
public class MemoryRepositoryConfiguration {

  @Bean
  @ConditionalOnProperty(value = "database", havingValue = "memory")
  Database memoryRepository() {

    log.info("Running on memory repository");

    return new MemoryRepository();
  }
}
