package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;

@Configuration
class FileRepositoryConfiguration {

  private static final String DATABASE_LOCATION = "db";
  private static final String INVOICES_FILE_NAME = "invoices.json";
  private static final String ID_FILE_NAME = "id.txt";

  @Bean
  IdService idService(FilesService filesService) throws IOException {
    Path idFilePath = Files.createTempFile(DATABASE_LOCATION, ID_FILE_NAME);
    return new IdService(idFilePath, filesService);
  }

  @Bean
  Database inFileRepository(IdService idService, FilesService filesService, JsonService jsonService) throws IOException {
    Path inFileRepositoryPath = Files.createTempFile(DATABASE_LOCATION, INVOICES_FILE_NAME);
    return new FileRepository(inFileRepositoryPath, idService, filesService, jsonService);
  }

}
