package pl.futurecollars.invoicing.db;

import java.util.Optional;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  long save(Invoice invoice);

  Optional<Invoice> getById(long id);

  void update(long id, Invoice updatedInvoice);

  void delete(long id);
}
