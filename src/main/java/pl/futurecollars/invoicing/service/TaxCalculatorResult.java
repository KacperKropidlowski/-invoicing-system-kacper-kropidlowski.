package pl.futurecollars.invoicing.service;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TaxCalculatorResult {

  @ApiModelProperty (value = "Company income", required = true, example = "20000")
  private final BigDecimal income;
  @ApiModelProperty (value = "Company costs", required = true, example = "5000")
  private final BigDecimal costs;
  @ApiModelProperty (value = "Company earnings", required = true, example = "15000")
  private final BigDecimal earnings;
  @ApiModelProperty (value = "Incoming VAT tax", required = true, example = "4600")
  private final BigDecimal incomingVat;
  @ApiModelProperty (value = "Outgoing VAT tax", required = true, example = "1150")
  private final BigDecimal outgoingVat;
  @ApiModelProperty (value = "VAT tax to return", required = true, example = "3450")
  private final BigDecimal vatToReturn;
}
