package com.dcm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

@Service
public class DocumentDraftService {

    public String generateContent(String templateName, Map<String, String> values) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("templates/" + templateName);
        if (in == null) {
            throw new IOException("Template not found: " + templateName);
        }
        String template;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            template = reader.lines().collect(Collectors.joining("\n"));
        }
        for (Map.Entry<String, String> e : values.entrySet()) {
            template = template.replace("{{" + e.getKey() + "}}", e.getValue());
        }
        return template;
    }

    public Path exportToPdf(String content, Path target) throws Exception {
        Document pdf = new Document();
        PdfWriter.getInstance(pdf, Files.newOutputStream(target));
        pdf.open();
        pdf.add(new Paragraph(content));
        pdf.close();
        return target;
    }

    public Path exportToWord(String content, Path target) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);
        try (java.io.OutputStream out = Files.newOutputStream(target)) {
            doc.write(out);
        }
        doc.close();
        return target;
    }
}
