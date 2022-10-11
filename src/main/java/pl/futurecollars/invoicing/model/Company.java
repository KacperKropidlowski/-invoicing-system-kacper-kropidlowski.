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
public class Company {

  @ApiModelProperty(value = "Company name", required = true, example = "Relax Kebab")
  private String name;

  @ApiModelProperty(value = "Company tax identification number", required = true, example = "5212205778")
  private String taxIdentificationNumber;

  @ApiModelProperty(value = "Company address", required = true, example = "aleja Jana Pawla II 40A, 05-250 Radzymin")
  private String address;

  @ApiModelProperty(value = "Health insurance cost", required = true, example = "500")
  private BigDecimal healthInsurance;

  @ApiModelProperty(value = "Pension insurance cost", required = true, example = "1400")
  private BigDecimal pensionInsurance;

}
