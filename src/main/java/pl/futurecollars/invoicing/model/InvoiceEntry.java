package pl.futurecollars.invoicing.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceEntry {

  private String description;
  private BigDecimal price;
  private BigDecimal vatValue;
  private Vat vatRate;
}
