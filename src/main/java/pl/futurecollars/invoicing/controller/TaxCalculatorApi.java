package pl.futurecollars.invoicing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.service.TaxCalculatorResult;

@Api(tags = {"tax-calculator-controller"})
@RequestMapping("tax")
public interface TaxCalculatorApi {

  @ApiOperation(value = "Get company income, expenses, VAT and taxes to pay")
  @PostMapping
  TaxCalculatorResult calculateTaxes(@RequestBody Company company);
}
