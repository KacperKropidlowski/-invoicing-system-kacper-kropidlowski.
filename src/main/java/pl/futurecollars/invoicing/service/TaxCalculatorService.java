package pl.futurecollars.invoicing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Company;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.model.InvoiceEntry;

@AllArgsConstructor
@Service
public class TaxCalculatorService {

  private final Database database;

  public TaxCalculatorResult getTaxCalculatorResult(Company company) {

    String taxIdentificationNumber = company.getTaxIdentificationNumber();

    BigDecimal income = calculateIncome(taxIdentificationNumber).setScale(2);
    BigDecimal costs = calculateCosts(taxIdentificationNumber).setScale(2);
    BigDecimal pensionInsurance = company.getPensionInsurance().setScale(2);
    BigDecimal totalHealthInsurance = company.getHealthInsurance().setScale(2);

    BigDecimal incomingVat = calculateIncomingVat(taxIdentificationNumber).setScale(2);
    BigDecimal outgoingVat = calculateOutgoingVat(taxIdentificationNumber).setScale(2);

    BigDecimal incomeMinusCosts = income.subtract(costs).setScale(2);
    BigDecimal vatToReturn = incomingVat.subtract(outgoingVat).setScale(2);
    BigDecimal incomeMinusCostsAndPensionInsurance = incomeMinusCosts.subtract(pensionInsurance).setScale(2);
    BigDecimal taxCalculationBase = incomeMinusCostsAndPensionInsurance.setScale(0, RoundingMode.UP);
    BigDecimal incomeTax = taxCalculationBase.multiply(BigDecimal.valueOf(0.19)).setScale(2);
    BigDecimal healthInsurance = totalHealthInsurance.multiply(BigDecimal.valueOf(0.09)).setScale(2);
    BigDecimal healthInsuranceDeductible = totalHealthInsurance.multiply(BigDecimal.valueOf(0.0775)).setScale(2);
    BigDecimal incomeTaxMinusHealthInsurance = incomeTax.subtract(healthInsuranceDeductible).setScale(2);
    BigDecimal finalIncomeTax = incomeTaxMinusHealthInsurance.setScale(0, RoundingMode.HALF_EVEN);

    return TaxCalculatorResult.builder()
        .income(income)
        .costs(costs)
        .incomeMinusCosts(incomeMinusCosts)
        .pensionInsurance(pensionInsurance)
        .incomeMinusCostsAndPensionInsurance(incomeMinusCostsAndPensionInsurance)
        .taxCalculationBase(taxCalculationBase)
        .incomeTax(incomeTax)
        .healthInsurance(healthInsurance)
        .healthInsuranceDeductible(healthInsuranceDeductible)
        .incomeTaxMinusHealthInsurance(incomeTaxMinusHealthInsurance)
        .finalIncomeTax(finalIncomeTax)
        .incomingVat(incomingVat)
        .outgoingVat(outgoingVat)
        .vatToReturn(vatToReturn)
        .build();
  }

  private BigDecimal calculateIncome(String taxIdentificationNumber) {
    return database.visit(sellerPredicate(taxIdentificationNumber), this::getTotalPrice);
  }

  private BigDecimal getTotalPrice(InvoiceEntry entry) {
    return entry.getNetPrice().multiply(entry.getQuantity());
  }

  private BigDecimal calculateCosts(String taxIdentificationNumber) {
    return database.visit(buyerPredicate(taxIdentificationNumber), calculateTotalCosts());
  }

  private Function<InvoiceEntry, BigDecimal> calculateTotalCosts() {
    return entry -> {
      if (entry.getExpenseRelatedToCar() != null && entry.getExpenseRelatedToCar().isPersonalUsage()) {
        return calculateTotalNetPrice(entry);
      }
      return getTotalPrice(entry);
    };
  }

  private BigDecimal calculateTotalNetPrice(InvoiceEntry entry) {
    return (entry.getNetPrice().add(entry.getVatValue().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING)))
        .multiply(entry.getQuantity());
  }

  private BigDecimal calculateIncomingVat(String taxIdentificationNumber) {
    return database.visit(sellerPredicate(taxIdentificationNumber), this::getTotalVat);
  }

  private BigDecimal getTotalVat(InvoiceEntry entry) {
    return entry.getVatValue().multiply(entry.getQuantity());
  }

  private BigDecimal calculateOutgoingVat(String taxIdentificationNumber) {
    return database.visit(buyerPredicate(taxIdentificationNumber), vatEntryToAmount());
  }

  private Function<InvoiceEntry, BigDecimal> vatEntryToAmount() {
    return entry -> {
      if (entry.getExpenseRelatedToCar() != null && entry.getExpenseRelatedToCar().isPersonalUsage()) {
        return calculateTotalVatValue(entry);
      }
      return getTotalVat(entry);
    };
  }

  private BigDecimal calculateTotalVatValue(InvoiceEntry entry) {
    return (entry.getVatValue().divide(BigDecimal.valueOf(2), 2, RoundingMode.FLOOR))
        .multiply(entry.getQuantity());
  }

  private Predicate<Invoice> sellerPredicate(String taxIdentificationNumber) {
    return invoice -> invoice.getSeller().getTaxIdentificationNumber().equals(taxIdentificationNumber);
  }

  private Predicate<Invoice> buyerPredicate(String taxIdentificationNumber) {
    return invoice -> invoice.getBuyer().getTaxIdentificationNumber().equals(taxIdentificationNumber);
  }

}
