package com.dcm.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcm.modal.Invoice;
import com.dcm.service.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class BillingController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice saved = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{caseno}")
    public ResponseEntity<List<Invoice>> getInvoices(@PathVariable String caseno) {
        return ResponseEntity.ok(invoiceService.findByCaseno(caseno));
    }

    @GetMapping
    public ResponseEntity<Iterable<Invoice>> getAll() {
        return ResponseEntity.ok(invoiceService.findAll());
    }
}
