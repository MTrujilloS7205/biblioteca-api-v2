package com.michael.biblioteca.service.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.michael.biblioteca.service.PdfService;
import java.io.ByteArrayOutputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.michael.biblioteca.util.PdfTableBuilder;
import com.itextpdf.layout.properties.TextAlignment;

@Slf4j
@Service
public class PdfServiceImpl implements PdfService {

  @Override
  public <T> byte[] generarReporte(String titulo, List<T> data) {

    log.info("Creando el archivo PDF");
    log.info("Cantidad de registros: {}", data.size());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    PdfWriter writer = new PdfWriter(baos);
    PdfDocument pdf = new PdfDocument(writer);
    try (Document document = new Document(pdf)) {
      document.add(new Paragraph(titulo)
        .setBold()
        .setFontSize(16)
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));

      Table table = PdfTableBuilder.buildTable(data);
      
      document.add(table);
    }

    return baos.toByteArray();
  }
}
