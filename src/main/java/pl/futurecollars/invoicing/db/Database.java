package pl.futurecollars.invoicing.db;

import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  long save(Invoice invoice);

  Invoice getById(long id);

  void update(long id, Invoice updatedInvoice);

  void delete(long id);
}
