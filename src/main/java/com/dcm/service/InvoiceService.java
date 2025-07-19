package com.dcm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcm.modal.Invoice;
import com.dcm.repository.InvoiceRepository;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findByCaseno(String caseno) {
        List<Invoice> invoices = new ArrayList<>();
        invoiceRepository.findByCaseno(caseno).forEach(invoices::add);
        return invoices;
    }

    public Iterable<Invoice> findAll() {
        return invoiceRepository.findAll();
    }
}
