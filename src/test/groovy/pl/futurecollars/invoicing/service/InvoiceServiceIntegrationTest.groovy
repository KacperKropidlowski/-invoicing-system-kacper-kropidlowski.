package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.invoice
import static pl.futurecollars.invoicing.DataForTesting.updatedInvoice

class InvoiceServiceIntegrationTest extends Specification {

    private InvoiceService invoiceService

    def setup() {
        Database database = new InMemoryDatabase()
        invoiceService = new InvoiceService(database)
    }

    def "should save invoice returning id, invoice should have id set to correct value, get by id returns saved invoice"() {
        when:
        long invoiceId = invoiceService.saveInvoice(invoice)
        then:
        invoiceId == 1
        invoiceService.getInvoice(1) == invoice
    }

    def "it's possible to update the invoice"() {
        given:
        invoiceService.saveInvoice(invoice)
        when:
        invoiceService.updateInvoice(1,updatedInvoice)
        then:
        invoiceService.getInvoice(1) == updatedInvoice
    }

    def "it's possible to delete the invoice"(){
        given:
        invoiceService.saveInvoice(invoice)
        when:
        invoiceService.deleteInvoice(1)
        then:
        invoiceService.getInvoice(1) == null
    }
}
