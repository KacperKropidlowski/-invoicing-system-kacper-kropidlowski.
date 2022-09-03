package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.DataForTesting
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path

class FileRepositoryIntegrationTest extends Specification {

    Path idServiceTestingPath = Path.of("src/test/resources/db/file/testingId.txt")
    Path inFileDatabaseTestingPath = Path.of("src/test/resources/db/file/testingInvoices.json")
    FilesService filesService = new FilesService()
    JsonService jsonService = new JsonService()
    IdService idService = new IdService(idServiceTestingPath, filesService)
    Database inFileDatabase = new FileRepository(inFileDatabaseTestingPath, idService, filesService, jsonService)
    Database wrongPathDatabase = new FileRepository(Path.of("wrongPath"), idService, filesService, jsonService)
    Invoice invoice = DataForTesting.invoice
    Invoice updatedInvoice = DataForTesting.updatedInvoice

    def cleanup() {
        Files.write(inFileDatabaseTestingPath, [])
        Files.write(idServiceTestingPath, [])
    }

    def "should save invoice"() {
        when:
        def result = inFileDatabase.save(invoice)

        then:
        result == 1
    }

    def "should throw exception with message 'Failed to save invoice'"() {
        when:
        wrongPathDatabase.save(invoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to save invoice"
        exception.cause.class == NoSuchFileException
        exception.cause.message == "wrongPath"
    }

    def "should get invoice by id"() {
        when:
        inFileDatabase.save(invoice)
        def result = inFileDatabase.getById(1)

        then:
        result.isPresent()
        result.toString().contains("Invoice(id=1, seller=Company(name=Relax Kebab, taxIdentificationNumber=5212205778, address=aleja Jana Paw³a II 40A, 05-250 Radzymin), buyer=Company(name=AGENCJA MIENIA WOJSKOWEGO, taxIdentificationNumber=5261038122, address=ul. Nowowiejska 26A, 00-911 Warszawa), invoiceEntries=[InvoiceEntry(description=Stumetrowy kebab, price=700, vatValue=161, vatRate=Vat.VAT_23)])") == invoice.toString().contains("Invoice(id=1, date=2022-08-25, seller=Company(name=Relax Kebab, taxIdentificationNumber=5212205778, address=aleja Jana Paw³a II 40A, 05-250 Radzymin), buyer=Company(name=AGENCJA MIENIA WOJSKOWEGO, taxIdentificationNumber=5261038122, address=ul. Nowowiejska 26A, 00-911 Warszawa), invoiceEntries=[InvoiceEntry(description=Stumetrowy kebab, price=700, vatValue=161, vatRate=Vat.VAT_23)])")
    }

    def "should throw exception with message 'Failed to get invoice with id: 1"() {
        when:
        wrongPathDatabase.getById(1)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to get invoice with id: 1"
        exception.cause.class == NoSuchFileException
        exception.cause.message == "wrongPath"
    }

    def "should update invoice"() {
        given:
        inFileDatabase.save(invoice)

        when:
        inFileDatabase.update(1, updatedInvoice)
        def result = inFileDatabase.getById(1)

        then:
        result.isPresent()
        result.toString().contains("id=1")
        result.toString().contains("seller=Company(name=AGENCJA MIENIA WOJSKOWEGO, taxIdentificationNumber=5261038122, address=ul. Nowowiejska 26A, 00-911 Warszawa)")
    }

    def "should throw exception with message 'Id 34 does not exist'"() {
        when:
        inFileDatabase.update(34, updatedInvoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Id 34 does not exist"
    }

    def "should throw exception with message 'Failed to update invoice with id: 1'"() {
        when:
        wrongPathDatabase.update(1, updatedInvoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to update invoice with id: 1"
    }

    def "should delete invoice"() {
        given:
        inFileDatabase.save(invoice)
        inFileDatabase.save(updatedInvoice)

        when:
        inFileDatabase.delete(2)

        then:
        inFileDatabase.getById(2) == Optional.empty()
    }

    def "should throw exception with message 'Failed to delete invoice with id: 1'"() {
        when:
        wrongPathDatabase.delete(1)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to delete invoice with id: 1"
    }

    def "should get all invoices"() {
        when:
        inFileDatabase.save(invoice)
        inFileDatabase.save(updatedInvoice)
        then:
        (inFileDatabase.getAllInvoices()).toString().contains("[Invoice(id=1, date=2022-09-15, seller=Company(name=Relax Kebab, taxIdentificationNumber=5212205778, address=aleja Jana Pawla II 40A, 05-250 Radzymin), buyer=Company(name=AGENCJA MIENIA WOJSKOWEGO, taxIdentificationNumber=5261038122, address=ul. Nowowiejska 26A, 00-911 Warszawa), invoiceEntries=[InvoiceEntry(description=Stumetrowy kebab, price=700, vatValue=161, vatRate=Vat.VAT_23)]), Invoice(id=2, date=2022-09-15, seller=Company(name=AGENCJA MIENIA WOJSKOWEGO, taxIdentificationNumber=5261038122, address=ul. Nowowiejska 26A, 00-911 Warszawa), buyer=Company(name=Relax Kebab, taxIdentificationNumber=5212205778, address=aleja Jana Pawla II 40A, 05-250 Radzymin), invoiceEntries=[InvoiceEntry(description=Stumetrowy kebab, price=700, vatValue=161, vatRate=Vat.VAT_23)])]")
    }

    def "should throw an exception with message 'Failed to get all ids'"() {
        when:
        wrongPathDatabase.getAllInvoices()

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to get all ids"
    }
}
