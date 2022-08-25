package pl.futurecollars.invoicing.db.file

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class IdServiceIntegrationTest extends Specification {

    private Path nextIdDbPath = File.createTempFile('nextId', '.txt').toPath()

    def "next id starts from 1 if file was empty"() {
        given:
        IdService idService = new IdService(nextIdDbPath, new FilesService())

        expect:
        ['1'] == Files.readAllLines(nextIdDbPath)

        and:
        1L == idService.getNextAndIncrement()
        ['2'] == Files.readAllLines(nextIdDbPath)

        and:
        2L == idService.getNextAndIncrement()
        ['3'] == Files.readAllLines(nextIdDbPath)

        and:
        3L == idService.getNextAndIncrement()
        ['4'] == Files.readAllLines(nextIdDbPath)
    }

    def "next id starts from last number if file was not empty"() {
        given:
        Files.writeString(nextIdDbPath, "17")
        IdService idService = new IdService(nextIdDbPath, new FilesService())

        expect:
        ['17'] == Files.readAllLines(nextIdDbPath)

        and:
        17L == idService.getNextAndIncrement()
        ['18'] == Files.readAllLines(nextIdDbPath)

        and:
        18L == idService.getNextAndIncrement()
        ['19'] == Files.readAllLines(nextIdDbPath)

        and:
        19L == idService.getNextAndIncrement()
        ['20'] == Files.readAllLines(nextIdDbPath)
    }
}
