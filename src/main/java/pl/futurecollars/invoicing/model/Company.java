package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {

  @ApiModelProperty(value = "Company name", required = true, example = "Relax Kebab")
  private String name;

  @ApiModelProperty(value = "Company tax identification number", required = true, example = "5212205778")
  private String taxIdentificationNumber;

  @ApiModelProperty(value = "Company address", required = true, example = "aleja Jana Pawla II 40A, 05-250 Radzymin")
  private String address;
}
