package pl.futurecollars.invoicing.db.memory;

import java.util.HashMap;
import java.util.Map;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {

  private static long currentId = 1;

  private Map<Long, Invoice> invoices = new HashMap<>();

  @Override
  public long save(Invoice invoice) {
    long newId = currentId;
    invoice.setId(newId);
    invoices.put(newId, invoice);

    currentId++;
    return newId;
  }

  @Override
  public Invoice getById(long id) {
    return invoices.get(id);
  }

  @Override
  public void update(long id, Invoice updatedInvoice) {
    Invoice actualInvoice = getById(id);

    if (actualInvoice == null) {
      throw new RuntimeException("Invoice with id " + id + " does not exist.");
    }

    updatedInvoice.setId(id);
    invoices.put(id, updatedInvoice);
  }

  @Override
  public void delete(long id) {
    invoices.remove(id);
  }
}
