package pl.futurecollars.invoicing.db.memory

import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.invoice
import static pl.futurecollars.invoicing.DataForTesting.updatedInvoice


class MemoryRepositoryTest extends Specification {

    Database database

    def setup() {
        database = new MemoryRepository()
    }

    def "should save an invoice and return id"() {
        expect:
        1L == database.save(invoice)
    }

    def "should get invoice by id"() {
        when:
        database.save(invoice)
        def result = database.getById(1)

        then:
        result.isPresent()
        result == Optional.of(invoice)
    }

    def "should update invoice"() {
        when:
        database.save(invoice)
        database.update(1, updatedInvoice)

        then:
        database.getById(1) == Optional.of(updatedInvoice)
    }

    def "should expect exception updating invoice that does not exist"() {
        when:
        database.update(50, updatedInvoice)

        then:
        thrown(RuntimeException)
    }

    def "should delete invoice"() {
        when:
        database.save(invoice)
        database.delete(1L)

        then:
        database.getById(1) == Optional.empty()
    }

    def "should get all invoices"() {
        when:
        database.save(invoice)
        database.save(updatedInvoice)

        then:
        database.getAllInvoices().contains(invoice)
        database.getAllInvoices().contains(updatedInvoice)
    }
}