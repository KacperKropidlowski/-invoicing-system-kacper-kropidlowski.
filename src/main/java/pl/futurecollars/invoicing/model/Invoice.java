package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "Invoice identification number (generated by application)", required = true, example = "1")
  private long id;

  @ApiModelProperty(value = "Date invoice was created", required = true)
  private LocalDate date;

  @ApiModelProperty(value = "Company who is selling the product/service", required = true)
  private Company seller;

  @ApiModelProperty(value = "Company who bought the product/service", required = true)
  private Company buyer;

  @ApiModelProperty(value = "List of products/services", required = true)
  private List<InvoiceEntry> invoiceEntries;

  public Invoice(LocalDate date, Company seller, Company buyer, List<InvoiceEntry> invoiceEntries) {
    this.date = date;
    this.seller = seller;
    this.buyer = buyer;
    this.invoiceEntries = invoiceEntries;
  }
}
