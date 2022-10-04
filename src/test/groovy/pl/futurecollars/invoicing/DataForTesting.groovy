package pl.futurecollars.invoicing

import java.time.LocalDate
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.Month

class DataForTesting {

    static Company relaxKebab = new Company("Relax Kebab", "5212205778", "aleja Jana Pawla II 40A, 05-250 Radzymin")
    static Company agencjaMieniaWojskowego = new Company("AGENCJA MIENIA WOJSKOWEGO", "5261038122", "ul. Nowowiejska 26A, 00-911 Warszawa")

    static InvoiceEntry firstEntry = new InvoiceEntry("Stumetrowy kebab", BigDecimal.valueOf(700), BigDecimal.ONE, BigDecimal.valueOf(56), Vat.VAT_8)
    static InvoiceEntry secondEntry = new InvoiceEntry("Karabinek GROT S 16 FB-M1", BigDecimal.valueOf(7000), BigDecimal.ONE, BigDecimal.valueOf(1610), Vat.VAT_23)
    static InvoiceEntry thirdEntry = new InvoiceEntry("Zestaw zastawy sto³owej z kuchni marynarki wojennej", BigDecimal.valueOf(50), BigDecimal.valueOf(50), BigDecimal.valueOf(575), Vat.VAT_23)

    static Invoice firstInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15), relaxKebab, agencjaMieniaWojskowego, List.of(firstEntry))
    static Invoice secondInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15), agencjaMieniaWojskowego, relaxKebab, List.of(secondEntry))
    static Invoice thirdInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15),agencjaMieniaWojskowego,relaxKebab,List.of(secondEntry,thirdEntry))

}
