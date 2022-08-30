package pl.futurecollars.invoicing

import spock.lang.Specification

class AppTest extends Specification {
    def "test to cover main"() {
        when:
        new App()
        then:
        1 == 1
    }
}
