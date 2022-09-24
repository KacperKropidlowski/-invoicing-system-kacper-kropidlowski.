package pl.futurecollars.invoicing.db.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class MemoryRepository implements Database {

  private long currentId = 1;

  private final Map<Long, Invoice> invoices = new HashMap<>();

  @Override
  public long save(Invoice invoice) {
    invoice.setId(currentId);

    invoices.put(currentId, invoice);

    return currentId++;
  }

  @Override
  public Optional<Invoice> getById(long id) {
    return Optional.ofNullable(invoices.get(id));
  }

  @Override
  public void update(long id, Invoice invoice) {
    Optional<Invoice> actualInvoice = getById(id);

    if (actualInvoice.isEmpty()) {
      throw new RuntimeException("Invoice with id " + id + " does not exist.");
    }

    invoice.setId(id);
    invoices.put(id, invoice);
  }

  @Override
  public void delete(long id) {
    invoices.remove(id);
  }

  @Override
  public List<Invoice> getAllInvoices() {
    return new ArrayList<>(invoices.values());
  }
}
