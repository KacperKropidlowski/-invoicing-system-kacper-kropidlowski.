package pl.futurecollars.invoicing.db.memory;

import org.springframework.context.annotation.Bean;
import pl.futurecollars.invoicing.db.Database;

public class MemoryRepositoryConfiguration {

  @Bean
  Database memoryRepository() {
    return new MemoryRepository();
  }
}
