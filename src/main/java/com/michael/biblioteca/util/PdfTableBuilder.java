package com.michael.biblioteca.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.michael.biblioteca.util.pdf.annotation.PdfColumn;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;

import java.util.List;
import java.util.Locale;

public class PdfTableBuilder {

  public static <T> Table buildTable(List<T> data) {

    if (data == null || data.isEmpty()) {
      return new Table(1).addCell("Sin datos");
    }

    Field[] fields = Arrays.stream(data.get(0).getClass().getDeclaredFields())
      .filter(f -> f.isAnnotationPresent(PdfColumn.class))
      .sorted(Comparator.comparingInt(f -> f.getAnnotation(PdfColumn.class).order()))
      .toArray(Field[]::new);

    if (fields.length == 0) {
      throw new IllegalStateException("No hay campos con @PdfColumn");
    }

    float[] widths = new float[fields.length];

    for (int i = 0; i < fields.length; i++) {
      widths[i] = fields[i]
        .getAnnotation(PdfColumn.class)
        .width();
    }

    Table table = new Table(widths);
    table.setHorizontalAlignment(HorizontalAlignment.CENTER);
    table.setBorder(Border.NO_BORDER);

    /* ==== HEADERS ==== */
    for (Field field : fields) {
      PdfColumn col = field.getAnnotation(PdfColumn.class);
      table.addHeaderCell(buildHeaderCell(col.header()));
    }

    /* =============== BODY ================================================= */
    int i = 0;

    for (T item : data) {

      boolean zebra = (i % 2 == 0);

      Color fondo = zebra
        ? new DeviceRgb(221, 235, 247)
        : new DeviceRgb(255, 255, 255);

      for (Field field : fields) {
        field.setAccessible(true);

        Object value;
        try {
          value = field.get(item);
        } catch (IllegalAccessException e) {
          value = null;
        }

        String texto = formatValue(value);

        TextAlignment align = field
          .getAnnotation(PdfColumn.class)
          .align();

        table.addCell(buildBodyCell(texto, fondo).setTextAlignment(align));
      }

      i++;
    }

    return table;
  }

  /* =============== MÉTODOS AUXILIARES ===================================== */
  private static Cell buildHeaderCell(String texto) {
    return new Cell()
      .add(new Paragraph(texto)
        .setBold()
        .setFontSize(10)
        .setFontColor(new DeviceRgb(255, 255, 255))
        .setTextAlignment(TextAlignment.CENTER)
        .setMargin(0))
      .setBackgroundColor(new DeviceRgb(68, 114, 196))
      .setPadding(4)
      .setBorder(new SolidBorder(new DeviceRgb(200, 200, 200), 0.5f)); // 🔥 clave
  }

  private static Cell buildBodyCell(String texto, Color fondo) {
    return new Cell()
      .add(new Paragraph(texto)
        .setFontSize(8)
        .setMargin(0))
      .setBackgroundColor(fondo)
      .setPadding(4)
      .setBorder(new SolidBorder(new DeviceRgb(200, 200, 200), 0.5f)); // 🔥 clave
  }

  private static String formatValue(Object value) {

    if (value == null) {
      return "-";
    }

    if (value instanceof Boolean b) {
      return b ? "Activo" : "No activo";
    }

    if (value instanceof LocalDate date) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      return date.format(formatter);
    }

    if (value instanceof LocalDateTime dateTime) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      return dateTime.format(formatter);
    }

    if (value instanceof Number number) {
      NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "PE"));

      if (number instanceof Integer || number instanceof Long) {
        nf.setMaximumFractionDigits(0); // 🔥 sin decimales
      } else {
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(2); // 🔥 con decimales
      }
      return nf.format(number);
    }

    if (value instanceof String s) {
      String limpio = StringUtils.limpiarTexto(s);
      return limpio != null ? limpio : "-";
    }
    return String.valueOf(value);
  }
}
