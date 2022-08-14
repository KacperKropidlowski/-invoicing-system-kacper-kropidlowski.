package pl.futurecollars.invoicing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Company {

  private String name;
  private String taxIdentificationNumber;
  private String address;
}
