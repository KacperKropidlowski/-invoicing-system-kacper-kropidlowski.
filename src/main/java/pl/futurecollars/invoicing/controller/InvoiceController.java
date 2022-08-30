package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
public class InvoiceController {

  private final Database database = new InMemoryDatabase();
  private final InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/invoices/{id}")
  public ResponseEntity<Invoice> getInvoice(@PathVariable long id) {
    return ResponseEntity.of(this.invoiceService.getInvoice(id));
  }

  @PostMapping("/invoices")
  public long saveInvoice(@RequestBody Invoice invoice) {
    return this.invoiceService.saveInvoice(invoice);
  }

  @DeleteMapping("/invoices/{id}")
  public void deleteInvoice(@PathVariable long id) {
    if (this.invoiceService.getInvoice(id).isPresent()) {
      this.invoiceService.deleteInvoice(id);
    } else {
      ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/invoices/{id}")
  public void updateInvoice(@RequestBody Invoice invoice, @PathVariable long id) {
    if (this.invoiceService.getInvoice(id).isPresent()) {
      this.invoiceService.updateInvoice(id, invoice);
    } else {
      ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/invoices/allIds")
  public List<Long> getAllIds() {
    return this.invoiceService.getAllIds();
  }
}
