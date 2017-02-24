package es.cic.curso.curso17.ejercicio026.servicio;

import java.util.List;

import es.cic.curso.curso17.ejercicio026.dto.FiguraDTO;
import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;

public interface ServicioGestorFiguras {
	
	void agregaLinea(Linea linea);
	
	void agregaCirculo(Circulo circulo);
	
	void agregaRectangulo(Rectangulo rectangulo);

	Linea obtenLinea(Long idLinea);
	
	Circulo obtenCirculo(Long idCirculo);
	
	Rectangulo obtenRectangulo(Long idRectangulo);
	
	Linea eliminaLinea(Long idLinea);
	
	Circulo eliminaCirculo(Long idCirculo);
	
	Rectangulo eliminaRectangulo(Long idRectangulo);
	
	List<FiguraDTO> listaFiguras();
	
	List<Linea> listaLineas();
	
	List<Circulo> listaCirculos();
	
	List<Rectangulo> listaRectangulos();
	
}
