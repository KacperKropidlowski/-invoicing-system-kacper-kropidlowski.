package pl.futurecollars.invoicing.db.memory

import pl.futurecollars.invoicing.db.Database
import spock.lang.Specification

import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice


class MemoryRepositoryTest extends Specification {

    Database database

    def setup() {
        database = new MemoryRepository()
    }

    def "should save an invoice and return id"() {
        expect:
        1L == database.save(firstInvoice)
    }

    def "should get invoice by id"() {
        when:
        database.save(firstInvoice)
        def result = database.getById(1)

        then:
        result.isPresent()
        result == Optional.of(firstInvoice)
    }

    def "should update invoice"() {
        when:
        database.save(firstInvoice)
        database.update(1, secondInvoice)

        then:
        database.getById(1) == Optional.of(secondInvoice)
    }

    def "should expect exception updating invoice that does not exist"() {
        when:
        database.update(50, secondInvoice)

        then:
        thrown(RuntimeException)
    }

    def "should delete invoice"() {
        when:
        database.save(firstInvoice)
        database.delete(1L)

        then:
        database.getById(1) == Optional.empty()
    }

    def "should get all invoices"() {
        when:
        database.save(firstInvoice)
        database.save(secondInvoice)

        then:
        database.getAllInvoices().contains(firstInvoice)
        database.getAllInvoices().contains(secondInvoice)
    }
}