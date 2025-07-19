package com.dcm.rest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcm.service.DocumentDraftService;

@RestController
@RequestMapping("/api/draft")
public class DocumentDraftController {

    @Autowired
    private DocumentDraftService draftService;

    @PostMapping(value = "/generate/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, String> body) throws Exception {
        String template = body.getOrDefault("template", "demand_letter.txt");
        String content = draftService.generateContent(template, body);
        Path file = Files.createTempFile("draft", ".pdf");
        draftService.exportToPdf(content, file);
        byte[] data = Files.readAllBytes(file);
        Files.deleteIfExists(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.pdf")
                .body(data);
    }

    @PostMapping(value = "/generate/word", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<byte[]> generateWord(@RequestBody Map<String, String> body) throws Exception {
        String template = body.getOrDefault("template", "demand_letter.txt");
        String content = draftService.generateContent(template, body);
        Path file = Files.createTempFile("draft", ".docx");
        draftService.exportToWord(content, file);
        byte[] data = Files.readAllBytes(file);
        Files.deleteIfExists(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document.docx")
                .body(data);
    }
}
