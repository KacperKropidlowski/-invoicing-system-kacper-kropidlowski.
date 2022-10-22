package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceEntry {

  @ApiModelProperty(value = "Product/service description", required = true, example = "Stumetrowy kebab")
  private String description;

  @ApiModelProperty(value = "Product/service price", required = true, example = "700")
  private BigDecimal netPrice;

  @ApiModelProperty(value = "Product/service quantity", required = true, example = "1")
  private BigDecimal quantity;

  @ApiModelProperty(value = "Product/service tax value", required = true, example = "161")
  private BigDecimal vatValue;

  @ApiModelProperty(value = "Tax rate", required = true, example = "23")
  private Vat vatRate;

  @ApiModelProperty(value = "Car this expense is related to, empty if expense is not related to car")
  private Car expenseRelatedToCar;
}
