package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

class IdService {

  private final Path idFilePath;
  private final FilesService filesService;
  private long nextId = 1;

  IdService(Path idFilePath, FilesService filesService) {
    this.idFilePath = idFilePath;
    this.filesService = filesService;

    try {
      Iterator<String> lines = this.filesService.readAllLines(idFilePath).iterator();
      if (!lines.hasNext()) {
        this.filesService.writeToFile(idFilePath, String.valueOf(nextId));
      } else {
        nextId = Integer.parseInt(lines.next());
      }
    } catch (IOException exception) {
      throw new RuntimeException("Failed to initialize id database", exception);
    }
  }

  long getNextAndIncrement() throws IOException {
    this.filesService.writeToFile(idFilePath, String.valueOf(nextId + 1));
    return nextId++;
  }
}
