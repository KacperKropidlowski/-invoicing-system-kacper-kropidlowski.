package pl.futurecollars.invoicing.db.file


import pl.futurecollars.invoicing.DataForTesting
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

class JsonServiceTest extends Specification {

    def "can convert object to json and read it back"() {
        given:
        def jsonService = new JsonService()
        def invoice = DataForTesting.invoice

        when:
        def invoiceAsString = jsonService.convertToJson(invoice)

        and:
        def invoiceFromJson = jsonService.toObject(invoiceAsString, Invoice)

        then:
        invoice.getId() == invoiceFromJson.getId()
        invoice.getBuyer().getName() == invoiceFromJson.getBuyer().getName()
        invoice.getSeller().getAddress() == invoiceFromJson.getSeller().getAddress()
        invoice.getDate() == invoiceFromJson.getDate()
    }

}
