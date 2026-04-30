package com.michael.biblioteca.service;

import java.util.List;

public interface PdfService {

  <T> byte[] generarReporte(String titulo, List<T> lista);

}
