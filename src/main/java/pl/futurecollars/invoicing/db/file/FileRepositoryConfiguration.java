package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "database", havingValue = "file")
class FileRepositoryConfiguration {

  @Bean
  IdService idService(FilesService filesService,
                      @Value("${database.path}") String databasePath,
                      @Value("${database.id.file}") String idFile
  ) throws IOException {
    Path idFilePath = Files.createTempFile(databasePath, idFile);
    return new IdService(idFilePath, filesService);
  }

  @Bean
  Database fileRepository(IdService idService,
                          FilesService filesService,
                          JsonService jsonService,
                          @Value("${database.path}") String databasePath,
                          @Value("${database.invoices.file}") String invoicesFile
  ) throws IOException {

    log.info("Running on file repository");
    log.debug(databasePath + "/" + invoicesFile);

    Path inFileRepositoryPath = Files.createTempFile(databasePath, invoicesFile);
    return new FileRepository(inFileRepositoryPath, idService, filesService, jsonService);
  }

}
