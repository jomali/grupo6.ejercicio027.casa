package es.cic.curso.curso17.ejercicio026.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Dibujo;
import es.cic.curso.curso17.ejercicio026.modelo.Lienzo;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioDibujo;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioLienzo;

@Service
@Transactional
public class ServicioGestorDibujoImpl implements ServicioGestorDibujo {

	private static final String ERROR_DIBUJO_ID = "No existe ningún dibujo en BB.DD. con ese ID";
	private static final String ERROR_LIENZO_ID = "No existe ningún lienzo en BB.DD. con ese ID";
	
	@Autowired
	private RepositorioDibujo repositorioDibujo;
	
	@Autowired
	private RepositorioLienzo repositorioLienzo;
	
	@Override
	public void agregaDibujo(Dibujo dibujo) {
		repositorioDibujo.create(dibujo);
	}
	
	@Override
	public void agregaLienzo(Lienzo lienzo) {
		repositorioLienzo.create(lienzo);
	}
	
	@Override
	public Dibujo obtenDibujo(Long idDibujo) {
		Dibujo dibujo = repositorioDibujo.read(idDibujo);
		if (dibujo == null) {
			throw new IllegalArgumentException(ERROR_DIBUJO_ID + ": " + idDibujo);
		}
		return dibujo;
	}
	
	@Override
	public Lienzo obtenLienzo(Long idLienzo) {
		Lienzo lienzo = repositorioLienzo.read(idLienzo);
		if (lienzo == null) {
			throw new IllegalArgumentException(ERROR_LIENZO_ID + ": " + idLienzo);
		}
		return lienzo;
	}
	
	@Override
	public Dibujo eliminaDibujo(Long idDibujo) {
		Dibujo dibujo = repositorioDibujo.read(idDibujo);
		repositorioDibujo.delete(dibujo);
		return dibujo;
	}
	
	@Override
	public Lienzo eliminaLienzo(Long idLienzo) {
		Lienzo lienzo = repositorioLienzo.read(idLienzo);
		repositorioLienzo.delete(lienzo);
		return lienzo;
	}

}
