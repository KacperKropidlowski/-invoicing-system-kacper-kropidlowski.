package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.context.SpringBootTest

import static pl.futurecollars.invoicing.DataForTesting.firstCompany
import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondCompany
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice
import static pl.futurecollars.invoicing.DataForTesting.thirdInvoice

@SpringBootTest
class TaxCalculatorControllerRestApiTest extends AbstractControllerRestApiTest {

    def "should return status 200 and response with values calculated only based on health and pension insurance when database do not contain invoices"() {
        when:
        def taxCalculatorResult = getTaxCalculatorResult(firstCompany)
        then:
        taxCalculatorResult.income == 0.00
        taxCalculatorResult.costs == 0.00
        taxCalculatorResult.incomeMinusCosts == 0.00
        taxCalculatorResult.pensionInsurance == 1000.00
        taxCalculatorResult.incomeMinusCostsAndPensionInsurance == -1000.00
        taxCalculatorResult.taxCalculationBase == -1000.00
        taxCalculatorResult.incomeTax == -190.00
        taxCalculatorResult.healthInsurance == 90.00
        taxCalculatorResult.healthInsuranceDeductible == 77.50
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -267.50
        taxCalculatorResult.finalIncomeTax == -268
        taxCalculatorResult.incomingVat == 0.00
        taxCalculatorResult.outgoingVat == 0.00
        taxCalculatorResult.vatToReturn == 0.00
    }

    def "should return correct tax values when there is invoice with car related expenses and car is used personal"() {
        given:
        def id = postInvoice(firstInvoice)
        when:
        def taxCalculatorResult = getTaxCalculatorResult(secondCompany)
        then:
        taxCalculatorResult.income == 0.00
        taxCalculatorResult.costs == 1040.00
        taxCalculatorResult.incomeMinusCosts == -1040.00
        taxCalculatorResult.pensionInsurance == 5000.00
        taxCalculatorResult.incomeMinusCostsAndPensionInsurance == -6040.00
        taxCalculatorResult.taxCalculationBase == -6040
        taxCalculatorResult.incomeTax == -1147.60
        taxCalculatorResult.healthInsurance == 450.00
        taxCalculatorResult.healthInsuranceDeductible == 387.50
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -1535.10
        taxCalculatorResult.finalIncomeTax == -1535
        taxCalculatorResult.incomingVat == 0.00
        taxCalculatorResult.outgoingVat == 40.00
        taxCalculatorResult.vatToReturn == -40.00
        cleanup:
        deleteInvoice(id)
    }

    def "should return correct tax values when there is invoice with car related expenses and car is not used personal"() {
        given:
        def id = postInvoice(secondInvoice)
        when:
        def taxCalculatorResult = getTaxCalculatorResult(secondCompany)
        then:
        taxCalculatorResult.income == 7000.00
        taxCalculatorResult.costs == 0.00
        taxCalculatorResult.incomeMinusCosts == 7000.00
        taxCalculatorResult.pensionInsurance == 5000.00
        taxCalculatorResult.incomeMinusCostsAndPensionInsurance == 2000.00
        taxCalculatorResult.taxCalculationBase == 2000
        taxCalculatorResult.incomeTax == 380.00
        taxCalculatorResult.healthInsurance == 450.00
        taxCalculatorResult.healthInsuranceDeductible == 387.50
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -7.50
        taxCalculatorResult.finalIncomeTax == -8
        taxCalculatorResult.incomingVat == 1610.00
        taxCalculatorResult.outgoingVat == 0.00
        taxCalculatorResult.vatToReturn == 1610.00
        cleanup:
        deleteInvoice(id)
    }

    def "should return correct tax values when there are multiple invoices in database"() {
        given:
        def id1 = postInvoice(firstInvoice)
        def id2 = postInvoice(secondInvoice)
        def id3 = postInvoice(thirdInvoice)
        when:
        def taxCalculatorResult = getTaxCalculatorResult(firstCompany)
        then:
        taxCalculatorResult.income == 1000.00
        taxCalculatorResult.costs == 16500.00
        taxCalculatorResult.incomeMinusCosts == -15500.00
        taxCalculatorResult.pensionInsurance == 1000.00
        taxCalculatorResult.incomeMinusCostsAndPensionInsurance == -16500.00
        taxCalculatorResult.taxCalculationBase == -16500
        taxCalculatorResult.incomeTax == -3135.00
        taxCalculatorResult.healthInsurance == 90.00
        taxCalculatorResult.healthInsuranceDeductible == 77.50
        taxCalculatorResult.incomeTaxMinusHealthInsurance == -3212.50
        taxCalculatorResult.finalIncomeTax == -3212
        taxCalculatorResult.incomingVat == 80.00
        taxCalculatorResult.outgoingVat == 31970.00
        taxCalculatorResult.vatToReturn == -31890.00

        cleanup:
        deleteInvoice(id1)
        deleteInvoice(id2)
        deleteInvoice(id3)
    }
}
