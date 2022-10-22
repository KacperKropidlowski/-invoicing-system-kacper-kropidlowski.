package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.Car

import java.time.LocalDate
import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.Month

class DataForTesting {

    static Company firstCompany = new Company("First Company", "5212205778", "First Street 1, New York, USA", BigDecimal.valueOf(1000).setScale(2), BigDecimal.valueOf(1000).setScale(2))
    static Company secondCompany = new Company("Second Company", "5261038122", "Second Street 2, California, USA", BigDecimal.valueOf(5000).setScale(2), BigDecimal.valueOf(5000).setScale(2))

    static Car firstCar = new Car("DW11111",true)
    static Car secondCar = new Car("DW22222",false)

    static InvoiceEntry firstEntry = new InvoiceEntry("Service #1", BigDecimal.valueOf(1000).setScale(2), BigDecimal.ONE.setScale(2), BigDecimal.valueOf(80).setScale(2), Vat.VAT_8, firstCar)
    static InvoiceEntry secondEntry = new InvoiceEntry("Product #1", BigDecimal.valueOf(7000).setScale(2), BigDecimal.ONE.setScale(2), BigDecimal.valueOf(1610).setScale(2), Vat.VAT_23, secondCar)
    static InvoiceEntry thirdEntry = new InvoiceEntry("Product #2", BigDecimal.valueOf(50).setScale(2), BigDecimal.valueOf(50).setScale(2), BigDecimal.valueOf(575).setScale(2), Vat.VAT_23, secondCar)

    static Invoice firstInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15), firstCompany, secondCompany, List.of(firstEntry))
    static Invoice secondInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15), secondCompany, firstCompany, List.of(secondEntry))
    static Invoice thirdInvoice = new Invoice(LocalDate.of(2022, Month.SEPTEMBER, 15),secondCompany,firstCompany,List.of(secondEntry,thirdEntry))

}
