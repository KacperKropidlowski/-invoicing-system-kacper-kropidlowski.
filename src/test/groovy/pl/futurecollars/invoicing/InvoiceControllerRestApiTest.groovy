package pl.futurecollars.invoicing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.futurecollars.invoicing.db.file.JsonService
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@AutoConfigureMockMvc
@SpringBootTest
class InvoiceControllerRestApiTest extends Specification {

    @Autowired
    JsonService jsonService

    @Autowired
    MockMvc mockMvc

    def "should try to get invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().response.contentAsString == ""
    }

    def "should try to delete invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.delete("/invoices/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().response.contentAsString == ""
    }

    def "should try to update invoice from empty database and get status 404"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.put("/invoices/100").content(getInvoiceAsString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "should save invoice and return status 200"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/invoices").content(getInvoiceAsString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().response.contentAsString == "1"
    }

    def "should get invoice from database and return status 200"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().response.contentAsString == getInvoiceAsString().replace('"id":0', '"id":1')
    }

    def "should update invoice from database and return status 200"() {
        expect:
        if (mockMvc.perform(MockMvcRequestBuilders.get("/invoices/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())) {
            mockMvc.perform(MockMvcRequestBuilders.put("/invoices/1").content(getUpdatedInvoiceAsString()).contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
            mockMvc.perform(MockMvcRequestBuilders.get("/invoices/1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().response.contentAsString == getUpdatedInvoiceAsString().replace('"id":0', '"id":1')
        }
    }

    def "should get all invoices from database and return status 200"() {
        setup:
        mockMvc.perform(MockMvcRequestBuilders.post("/invoices").content(getInvoiceAsString()).contentType(MediaType.APPLICATION_JSON))
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().response.contentAsString == getAllInvoicesAsString()
    }

    def "should delete invoice from database and return status 200"() {
        expect:
        if (mockMvc.perform(MockMvcRequestBuilders.get("/invoices/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())) {
            mockMvc.perform(MockMvcRequestBuilders.delete("/invoices/1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
            mockMvc.perform(MockMvcRequestBuilders.delete("/invoices/1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
        }
    }

    def getInvoiceAsString() {
        return jsonService.convertToJson(DataForTesting.invoice)
    }

    def getUpdatedInvoiceAsString() {
        return jsonService.convertToJson(DataForTesting.updatedInvoice)
    }

    def getAllInvoicesAsString() {
        return "[" + (getUpdatedInvoiceAsString().replace('"id":0', '"id":1') + "," + getInvoiceAsString().replace('"id":0', '"id":2')) + "]"
    }
}
