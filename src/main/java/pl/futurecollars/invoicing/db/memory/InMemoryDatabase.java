package pl.futurecollars.invoicing.db.memory;

import java.util.HashMap;
import java.util.Map;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {

  private long currentId = 1;

  private final Map<Long, Invoice> invoices = new HashMap<>();

  @Override
  public long save(Invoice invoice) {
    invoice.setId(currentId);

    invoices.put(currentId, invoice);

    return currentId++;
  }

  @Override
  public Invoice getById(long id) {
    return invoices.get(id);
  }

  @Override
  public void update(long id, Invoice invoice) {
    Invoice actualInvoice = getById(id);

    if (actualInvoice == null) {
      throw new RuntimeException("Invoice with id " + id + " does not exist.");
    }

    invoice.setId(id);
    invoices.put(id, invoice);
  }

  @Override
  public void delete(long id) {
    invoices.remove(id);
  }
}
