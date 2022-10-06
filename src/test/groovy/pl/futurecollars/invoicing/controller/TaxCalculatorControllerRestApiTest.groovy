package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.context.SpringBootTest

import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice
import static pl.futurecollars.invoicing.DataForTesting.thirdInvoice

@SpringBootTest
class TaxCalculatorControllerRestApiTest extends AbstractControllerRestApiTest {

    def "should return status 200 and response with zeros as values when database is empty"() {
        when:
        def taxCalculatorResult = getTaxCalculatorResult("0")
        then:
        compareTaxCalculatorResults(taxCalculatorResult,
                0 as BigDecimal,
                0 as BigDecimal,
                0 as BigDecimal,
                0 as BigDecimal,
                0 as BigDecimal,
                0 as BigDecimal)
    }

    def "should return correct tax values when there is invoice in database"() {
        given:
        def id = postInvoice(firstInvoice)
        when:
        def taxCalculatorResult = getTaxCalculatorResult("5212205778")
        then:
        compareTaxCalculatorResults(taxCalculatorResult,
                700 as BigDecimal,
                0 as BigDecimal,
                700 as BigDecimal,
                56 as BigDecimal,
                0 as BigDecimal,
                56 as BigDecimal)
        cleanup:
        deleteInvoice(id)
    }

    def "should return correct tax values when there are multiple invoices in database"() {
        given:
        def id1 = postInvoice(firstInvoice)
        def id2 = postInvoice(secondInvoice)
        def id3 = postInvoice(thirdInvoice)
        when:
        def taxCalculatorResult = getTaxCalculatorResult("5261038122")
        then:
        compareTaxCalculatorResults(taxCalculatorResult,
                16500 as BigDecimal,
                700 as BigDecimal,
                15800 as BigDecimal,
                31970 as BigDecimal,
                56 as BigDecimal,
                31914 as BigDecimal)
        cleanup:
        deleteInvoice(id1)
        deleteInvoice(id2)
        deleteInvoice(id3)
    }
}
