package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
public class InvoiceController {

  private final InvoiceService invoiceService;

  @Autowired
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
  public ResponseEntity<?> deleteInvoice(@PathVariable long id) {
    if (this.invoiceService.deleteInvoice(id) == true) {
      this.invoiceService.deleteInvoice(id);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/invoices/{id}")
  public ResponseEntity<?> updateInvoice(@RequestBody Invoice invoice, @PathVariable long id) {
    if (this.invoiceService.updateInvoice(id, invoice) == true) {
      this.invoiceService.updateInvoice(id, invoice);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/invoices/all")
  public List<Invoice> getAllInvoices() {
    return this.invoiceService.getAllInvoices();
  }
}
