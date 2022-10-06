package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@AllArgsConstructor
public class FileRepository implements Database {

  private final Path databasePath;
  private final IdService idService;
  private final FilesService filesService;
  private final JsonService jsonService;

  @Override
  public long save(Invoice invoice) {
    try {
      invoice.setId(idService.getNextAndIncrement());
      filesService.appendLineToFile(databasePath, jsonService.convertToJson(invoice));

      return invoice.getId();
    } catch (IOException exception) {
      throw new RuntimeException("Failed to save invoice", exception);
    }
  }

  @Override
  public Optional<Invoice> getById(long id) {
    try {
      return filesService.readAllLines(databasePath)
          .stream()
          .filter(line -> containsId(line, id))
          .map(line -> jsonService.convertToObject(line, Invoice.class))
          .findFirst();
    } catch (IOException exception) {
      throw new RuntimeException("Failed to get invoice with id: " + id, exception);
    }
  }

  @Override
  public void update(long id, Invoice newInvoice) {
    try {
      List<String> allInvoices = filesService.readAllLines(databasePath);
      String toUpdate = allInvoices.stream()
          .filter(line -> containsId(line, id))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Id " + id + " does not exist"));

      allInvoices.remove(toUpdate);
      Invoice invoice = jsonService.convertToObject(toUpdate, Invoice.class);
      invoice.setDate(newInvoice.getDate());
      invoice.setSeller(newInvoice.getSeller());
      invoice.setBuyer(newInvoice.getBuyer());
      invoice.setInvoiceEntries(newInvoice.getInvoiceEntries());

      allInvoices.add(jsonService.convertToJson(invoice));

      filesService.writeLinesToFile(databasePath, allInvoices);
    } catch (IOException exception) {
      throw new RuntimeException("Failed to update invoice with id: " + id, exception);
    }
  }

  @Override
  public void delete(long id) {
    try {
      var updatedList = filesService.readAllLines(databasePath)
          .stream()
          .filter(line -> !containsId(line, id))
          .collect(Collectors.toList());

      filesService.writeLinesToFile(databasePath, updatedList);

    } catch (IOException exception) {
      throw new RuntimeException("Failed to delete invoice with id: " + id, exception);
    }
  }

  @Override
  public List<Invoice> getAllInvoices() {
    try {
      return filesService.readAllLines(databasePath)
          .stream()
          .map(line -> jsonService.convertToObject(line, Invoice.class))
          .collect(Collectors.toList());
    } catch (IOException exception) {
      throw new RuntimeException("Failed to get all ids", exception);
    }
  }

  private boolean containsId(String line, long id) {
    return line.contains("\"id\":" + id + ",");
  }
}
