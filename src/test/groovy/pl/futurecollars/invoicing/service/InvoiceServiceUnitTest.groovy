package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.invoice
import static pl.futurecollars.invoicing.DataForTesting.updatedInvoice


class InvoiceServiceUnitTest extends Specification {

    private InvoiceService invoiceService
    private Database database

    def setup() {
        database = Mock()
        invoiceService = new InvoiceService(database)
    }

    def "calling saveInvoice() should delegate to database save() method"() {
        given:
        invoice

        when:
        invoiceService.saveInvoice(invoice)

        then:
        1 * database.save(invoice)
    }

    def "calling updateInvoice() should delegate to database update() method"() {
        given:
        updatedInvoice

        when:
        invoiceService.updateInvoice(1, updatedInvoice)

        then:
        1 * database.update(1, updatedInvoice)
    }

    def "calling getInvoice() should delegate to database getById() method"() {
        given:
        def invoiceId = 1L

        when:
        invoiceService.getInvoice(invoiceId)

        then:
        1 * database.getById(invoiceId)
    }

    def "calling deleteInvoice() should delegate to database delete() method"() {
        given:
        def invoiceId = 1L

        when:
        invoiceService.deleteInvoice(invoiceId)

        then:
        1 * database.delete(invoiceId)
    }

    def "calling getAllIds() should delegate to database getAllIds() method"() {
        when:
        invoiceService.getAllIds()

        then:
        1 * database.getAllIds()
    }
}