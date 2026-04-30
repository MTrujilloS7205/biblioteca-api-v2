package com.michael.biblioteca.util.pdf.annotation;

import com.itextpdf.layout.properties.TextAlignment;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PdfColumn {

  String header();        // Nombre de la columna

  int order();            // Orden en la tabla

  float width() default 1; // Proporción

  TextAlignment align() default TextAlignment.LEFT; // Alineación
}
