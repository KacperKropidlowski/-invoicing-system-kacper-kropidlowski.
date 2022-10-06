package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice


class InvoiceServiceUnitTest extends Specification {

    private InvoiceService invoiceService
    private Database database

    def setup() {
        database = Mock()
        invoiceService = new InvoiceService(database)
    }

    def "calling saveInvoice() should delegate to database save() method"() {
        when:
        invoiceService.saveInvoice(firstInvoice)

        then:
        1 * database.save(firstInvoice)
    }

    def "calling getInvoice() should delegate to database getById() method"() {
        given:
        def invoiceId = 1L

        when:
        invoiceService.getInvoice(invoiceId)

        then:
        1 * database.getById(invoiceId)
    }

    def "calling updateInvoice() should return true and delegate to database update() method if invoice to update exists in database"() {
        given:
        database.getById(1) >> Optional.of(firstInvoice)

        when:
        invoiceService.updateInvoice(1, secondInvoice)

        then:
        1 * database.update(1, secondInvoice)
        true
    }

    def "calling updateInvoice() should return false if invoice to update does not exists in database"() {
        given:
        database.getById(1) >> Optional.empty()

        when:
        def result = invoiceService.updateInvoice(1, secondInvoice)

        then:
        !result
    }

    def "calling deleteInvoice() should delegate to database delete() method if invoice exists in database"() {
        given:
        database.getById(1) >> Optional.of(firstInvoice)

        when:
        invoiceService.deleteInvoice(1)

        then:
        1 * database.delete(1)
    }

    def "calling deleteInvoice() should return false if invoice to update does not exists in database"() {
        given:
        database.getById(1) >> Optional.empty()

        when:
        def result = invoiceService.deleteInvoice(1)

        then:
        !result
    }

    def "calling getAllInvoices() should delegate to database getAllInvoices() method"() {
        when:
        invoiceService.getAllInvoices()

        then:
        1 * database.getAllInvoices()
    }
}
