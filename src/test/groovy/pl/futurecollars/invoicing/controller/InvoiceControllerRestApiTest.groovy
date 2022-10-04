package pl.futurecollars.invoicing.controller

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Stepwise

import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice

@Stepwise
@SpringBootTest
class InvoiceControllerRestApiTest extends AbstractControllerRestApiTest {

    def WRONG_ID = "100"

    def "should try to get invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("$INVOICES_ENDPOINT$WRONG_ID"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "should try to delete invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete("$INVOICES_ENDPOINT$WRONG_ID"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "should try to update invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.put("$INVOICES_ENDPOINT$WRONG_ID").content(getInvoiceAsJson(firstInvoice)).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "should save invoice and return status 200"() {
        expect:
        postInvoice(firstInvoice) == 1L
    }

    def "should get invoice from database and return status 200"() {
        when:
        def resultInvoice = getInvoice(1L)
        then:
        compareInvoices(resultInvoice, firstInvoice)
    }

    def "should update invoice from database and return status 200"() {
        when:
        updateInvoice(1L, secondInvoice)
        then:
        compareInvoices(getInvoice(1L), secondInvoice)
    }

    def "should get all invoices from database and return status 200"() {
        when:
        postInvoice(firstInvoice)
        then:
        getAllInvoices().size() == 2
    }

    def "should delete invoice from database and return status 200"() {
        when:
        deleteInvoice(1)
        then:
        mockMvc.perform(MockMvcRequestBuilders.delete("$INVOICES_ENDPOINT" + "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        cleanup:
        deleteInvoice(2)
    }
}
