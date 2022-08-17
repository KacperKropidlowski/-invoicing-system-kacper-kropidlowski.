package pl.futurecollars.invoicing

import java.time.LocalDate
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

class DataForTesting {

    static Company seller = new Company("Relax Kebab", "5212205778", "aleja Jana Pawła II 40A, 05-250 Radzymin")
    static Company buyer = new Company("AGENCJA MIENIA WOJSKOWEGO", "5261038122", "ul. Nowowiejska 26A, 00-911 Warszawa")

    static InvoiceEntry firstEntry = new InvoiceEntry("Stumetrowy kebab", new BigDecimal(700), new BigDecimal(161), Vat.VAT_23)

    static Invoice invoice = Invoice.builder()
            .date(LocalDate.now())
            .seller(seller)
            .buyer(buyer)
            .invoiceEntries(List.of(firstEntry))
            .build()

    static Invoice updatedInvoice = Invoice.builder()
            .date(LocalDate.now())
            .seller(buyer)
            .buyer(seller)
            .invoiceEntries(List.of(firstEntry))
            .build()
}
