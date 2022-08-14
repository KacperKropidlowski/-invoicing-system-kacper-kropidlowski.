package pl.futurecollars.invoicing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;
import pl.futurecollars.invoicing.model.Vat;
import pl.futurecollars.invoicing.service.InvoiceService;

public class App {

  public static void main(String[] args) {

    Database database = new InMemoryDatabase();

    InvoiceService invoiceService = new InvoiceService(database);

    Company seller = new Company("Relax Kebab", "5212205778", "aleja Jana Pawła II 40A, 05-250 Radzymin");
    Company buyer = new Company("AGENCJA MIENIA WOJSKOWEGO", "5261038122", "ul. Nowowiejska 26A, 00-911 Warszawa");

    InvoiceEntry firstEntry = new InvoiceEntry("Stumetrowy kebab", new BigDecimal(700), new BigDecimal(161), Vat.VAT_23);

    Invoice invoice = Invoice.builder()
        .date(LocalDate.now())
        .seller(seller)
        .buyer(buyer)
        .invoiceEntries(List.of(firstEntry))
        .build();

    Invoice updatedInvoice = Invoice.builder()
        .date(LocalDate.now())
        .seller(buyer)
        .buyer(seller)
        .invoiceEntries(List.of(firstEntry))
        .build();

    long invoiceId = invoiceService.saveInvoice(invoice);

    System.out.println(invoiceService.getInvoice(invoiceId));

    invoiceService.updateInvoice(invoiceId, updatedInvoice);

    System.out.println(invoiceService.getInvoice(invoiceId));

    System.out.println(invoiceService.getInvoice(88));
  }
}
