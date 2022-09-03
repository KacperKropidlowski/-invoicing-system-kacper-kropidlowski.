package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.memory.MemoryRepository
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.invoice
import static pl.futurecollars.invoicing.DataForTesting.updatedInvoice

class InvoiceServiceIntegrationTest extends Specification {

    private InvoiceService invoiceService

    def setup() {
        Database database = new MemoryRepository()
        invoiceService = new InvoiceService(database)
    }

    def "should save invoice returning id, invoice id is set to correct value"() {
        when:
        long invoiceId = invoiceService.saveInvoice(invoice)

        then:
        invoiceId == 1
        invoiceService.getInvoice(1) == Optional.of(invoice)
    }

    def "it's possible to get invoice by id"() {
        given:
        invoiceService.saveInvoice(invoice)

        when:
        def result = invoiceService.getInvoice(1L)

        then:
        result == Optional.of(invoice)
    }

    def "it's possible to update the invoice"() {
        given:
        invoiceService.saveInvoice(invoice)

        when:
        invoiceService.updateInvoice(1, updatedInvoice)
        def result = invoiceService.getInvoice(1)

        then:
        result.isPresent()
        result.get() == updatedInvoice
    }

    def "it's possible to delete the invoice"() {
        given:
        invoiceService.saveInvoice(invoice)

        when:
        invoiceService.deleteInvoice(1)

        then:
        invoiceService.getInvoice(1) == Optional.empty()
    }

    def "it's possible to get all saved invoices"() {
        given:
        invoiceService.saveInvoice(invoice)
        invoiceService.saveInvoice(updatedInvoice)

        expect:
        invoiceService.getAllInvoices().contains(invoice)
        invoiceService.getAllInvoices().contains(updatedInvoice)
    }
}
