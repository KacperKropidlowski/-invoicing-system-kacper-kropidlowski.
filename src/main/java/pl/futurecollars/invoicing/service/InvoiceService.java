package pl.futurecollars.invoicing.service;

import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InvoiceService {

  private final Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public long saveInvoice(Invoice invoice) {
    return database.save(invoice);
  }

  public void updateInvoice(long id, Invoice invoice) {
    database.update(id, invoice);
  }

  public Optional<Invoice> getInvoice(long id) {
    return database.getById(id);
  }

  public void deleteInvoice(long id) {
    database.delete(id);
  }
}
