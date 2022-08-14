package pl.futurecollars.invoicing.db.memory

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat
import spock.lang.Specification
import spock.lang.Stepwise

import java.time.LocalDate

@Stepwise
class InMemoryDatabaseTest extends Specification {


    Database database = new InMemoryDatabase()

    Company seller = new Company("Relax Kebab", "5212205778", "aleja Jana Pawła II 40A, 05-250 Radzymin")
    Company buyer = new Company("AGENCJA MIENIA WOJSKOWEGO", "5261038122", "ul. Nowowiejska 26A, 00-911 Warszawa")

    InvoiceEntry firstEntry = new InvoiceEntry("Stumetrowy kebab", new BigDecimal(700), new BigDecimal(161), Vat.VAT_23)

    Invoice invoice = Invoice.builder()
            .date(LocalDate.now())
            .seller(seller)
            .buyer(buyer)
            .invoiceEntries(List.of(firstEntry))
            .build()

    Invoice updatedInvoice = Invoice.builder()
            .date(LocalDate.now())
            .seller(buyer)
            .buyer(seller)
            .invoiceEntries(List.of(firstEntry))
            .build()

    def "should save an invoice and return id"() {
        when:
        long result = database.save(invoice)
        then:
        result == 1
    }

    def "should get invoice by id"() {
        when:
        database.save(invoice)

        then:
        database.getById(2) == invoice
    }

    def "should update invoice"() {
        when:
        database.save(invoice)
        database.update(3,updatedInvoice)

        then:
        database.getById(3) == updatedInvoice
    }

    def "should expect exception updating invoice that does not exist"() {
        when:
        database.update(50,updatedInvoice)

        then:
        thrown(RuntimeException)
    }

    def "should delete invoice"() {
        when:
        database.save(invoice)
        database.delete(1)
        then:
        database.getById(1) == null
    }
}
