package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Service
public class InvoiceService {

  private final Database database;

  public InvoiceService(@Qualifier("memoryRepository") Database database) {
    this.database = database;
  }

  public long saveInvoice(Invoice invoice) {
    return database.save(invoice);
  }

  public boolean updateInvoice(long id, Invoice invoice) {
    if (database.getById(id).isPresent()) {
      database.update(id, invoice);
      return true;
    } else {
      return false;
    }

  }

  public Optional<Invoice> getInvoice(long id) {
    return database.getById(id);
  }

  public boolean deleteInvoice(long id) {
    if (database.getById(id).isPresent()) {
      database.delete(id);
      return true;
    } else {
      return false;
    }
  }

  public List<Invoice> getAllInvoices() {
    return database.getAllInvoices();
  }

}
