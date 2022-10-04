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

  @ApiModelProperty (required = true, example = "20000")
  private final BigDecimal income;
  @ApiModelProperty (required = true, example = "5000")
  private final BigDecimal costs;
  @ApiModelProperty (required = true, example = "15000")
  private final BigDecimal earnings;
  @ApiModelProperty (required = true, example = "4600")
  private final BigDecimal incomingVat;
  @ApiModelProperty (required = true, example = "1150")
  private final BigDecimal outgoingVat;
  @ApiModelProperty (required = true, example = "3450")
  private final BigDecimal vatToReturn;
}
