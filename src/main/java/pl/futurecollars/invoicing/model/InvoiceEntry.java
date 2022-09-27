package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "Product/service description", required = true, example = "Stumetrowy kebab")
  private String description;

  @ApiModelProperty(value = "Product/service price", required = true, example = "700")
  private BigDecimal price;

  @ApiModelProperty(value = "Product/service tax value", required = true, example = "161")
  private BigDecimal vatValue;

  @ApiModelProperty(value = "Tax rate", required = true, example = "23")
  private Vat vatRate;
}
