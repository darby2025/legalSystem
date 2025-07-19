package com.dcm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dcm.modal.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    List<Invoice> findByCaseno(String caseno);
}
