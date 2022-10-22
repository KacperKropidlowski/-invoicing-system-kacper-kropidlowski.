package pl.futurecollars.invoicing.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

  @ApiModelProperty(value = "Car registration number", required = true, example = "DW123AB")
  private String registrationNumber;

  @ApiModelProperty(value = "Specify if car is used also for personal reasons", required = true, example = "true")
  private boolean personalUsage;

}
