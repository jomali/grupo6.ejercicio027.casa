package es.cic.curso.curso17.ejercicio026.servicio;

import es.cic.curso.curso17.ejercicio026.modelo.Dibujo;
import es.cic.curso.curso17.ejercicio026.modelo.Lienzo;

public interface ServicioGestorDibujo {
	
	void agregaDibujo(Dibujo dibujo);
	
	void agregaLienzo(Lienzo lienzo);
	
	Dibujo obtenDibujo(Long idDibujo);
	
	Lienzo obtenLienzo(Long idLienzo);
	
	Dibujo eliminaDibujo(Long idDibujo);
	
	Lienzo eliminaLienzo(Long idLienzo);

}
