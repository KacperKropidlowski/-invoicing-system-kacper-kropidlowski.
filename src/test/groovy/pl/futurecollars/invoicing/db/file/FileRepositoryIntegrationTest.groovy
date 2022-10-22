package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.DataForTesting
import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path

import static pl.futurecollars.invoicing.DataForTesting.firstInvoice
import static pl.futurecollars.invoicing.DataForTesting.secondInvoice


class FileRepositoryIntegrationTest extends Specification {

    Path idServiceTestingPath = Path.of("src/test/resources/db/file/testingId.txt")
    Path inFileDatabaseTestingPath = Path.of("src/test/resources/db/file/testingInvoices.json")
    FilesService filesService = new FilesService()
    JsonService jsonService = new JsonService()
    IdService idService = new IdService(idServiceTestingPath, filesService)
    Database inFileDatabase = new FileRepository(inFileDatabaseTestingPath, idService, filesService, jsonService)
    Database wrongPathDatabase = new FileRepository(Path.of("wrongPath"), idService, filesService, jsonService)

    def setup() {
        Files.write(inFileDatabaseTestingPath, [])
        Files.write(idServiceTestingPath, [])
    }

    def "should save invoice"() {
        when:
        def result = inFileDatabase.save(firstInvoice)

        then:
        result == 1
    }

    def "should throw exception with message 'Failed to save invoice'"() {
        when:
        wrongPathDatabase.save(firstInvoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to save invoice"
        exception.cause.class == NoSuchFileException
        exception.cause.message == "wrongPath"
    }

    def "should get invoice by id"() {
        when:
        def id = inFileDatabase.save(firstInvoice)
        def result = inFileDatabase.getById(id)

        then:
        result.isPresent()
        firstInvoice.setId(id)
        result.get().toString() == firstInvoice.toString()
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
        def id = inFileDatabase.save(firstInvoice)

        when:
        inFileDatabase.update(id, secondInvoice)
        def result = inFileDatabase.getById(id)

        then:
        result.isPresent()
        secondInvoice.setId(id)
        result.get().toString() == secondInvoice.toString()
    }

    def "should throw exception with message 'Id 734 does not exist'"() {
        when:
        inFileDatabase.update(734, secondInvoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Id 734 does not exist"
    }

    def "should throw exception with message 'Failed to update invoice with id: 1'"() {
        when:
        wrongPathDatabase.update(1, secondInvoice)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to update invoice with id: 1"
    }

    def "should delete invoice"() {
        given:
        inFileDatabase.save(firstInvoice)
        inFileDatabase.save(secondInvoice)

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
        def id1 = inFileDatabase.save(firstInvoice)
        def id2 = inFileDatabase.save(secondInvoice)
        then:
        firstInvoice.setId(id1)
        secondInvoice.setId(id2)
        def invoices = [firstInvoice, secondInvoice]
        inFileDatabase.getAllInvoices().toString() == invoices.toString()
    }

    def "should throw an exception with message 'Failed to get all ids'"() {
        when:
        wrongPathDatabase.getAllInvoices()

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Failed to get all ids"
    }
}
