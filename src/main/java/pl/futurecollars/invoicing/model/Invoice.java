package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

  private long id;
  private LocalDate date;
  private Company seller;
  private Company buyer;
  private List<InvoiceEntry> invoiceEntries;

  public Invoice(LocalDate date, Company seller, Company buyer, List<InvoiceEntry> invoiceEntries) {
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.invoiceEntries = invoiceEntries;
  }
}
