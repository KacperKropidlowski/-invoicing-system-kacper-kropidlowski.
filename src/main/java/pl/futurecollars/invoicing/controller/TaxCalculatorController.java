package pl.futurecollars.invoicing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.service.TaxCalculatorResult;
import pl.futurecollars.invoicing.service.TaxCalculatorService;

@RestController
public class TaxCalculatorController implements TaxCalculatorApi {

  private final TaxCalculatorService taxCalculatorService;

  @Autowired
  public TaxCalculatorController(TaxCalculatorService taxCalculatorService) {
    this.taxCalculatorService = taxCalculatorService;
  }

  @Override
  public TaxCalculatorResult calculateTaxes(@PathVariable String taxIdentificationNumber) {
    return taxCalculatorService.calculateTaxes(taxIdentificationNumber);
  }
}
