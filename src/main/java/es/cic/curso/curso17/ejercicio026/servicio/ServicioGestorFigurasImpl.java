package es.cic.curso.curso17.ejercicio026.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.dto.FiguraDTO;
import es.cic.curso.curso17.ejercicio026.dto.FiguraDTOTraductor;
import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Figura;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioCirculo;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioFigura;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioLinea;
import es.cic.curso.curso17.ejercicio026.repositorio.RepositorioRectangulo;

@Service
@Transactional
public class ServicioGestorFigurasImpl implements ServicioGestorFiguras {
	
	private static final String ERROR_FIGURA_NULA = "La referencia a figura no puede ser nula";
	private static final String ERROR_LINEA_ID = "No existe ninguna línea en BB.DD. con ese ID";
	private static final String ERROR_CIRCULO_ID = "No existe ningún círculo en BB.DD. con ese ID";
	private static final String ERROR_RECTANGULO_ID = "No existe ningún rectángulo en BB.DD. con ese ID";
	
	@Autowired
	private RepositorioCirculo repositorioCirculo;

	@Autowired
	private RepositorioFigura repositorioFigura;
	
	@Autowired
	private RepositorioLinea repositorioLinea;
	
	@Autowired
	private RepositorioRectangulo repositorioRectangulo;
	
	@Autowired
	private FiguraDTOTraductor figuraDTOTraductor;
	
	private void refrescaFigura(Figura figura) {
		if (figura.getId() == null) {
			repositorioFigura.create(figura);
		} else {
			if (repositorioFigura.read(figura.getId()) == null) {
				repositorioFigura.create(figura);
			} else {
				repositorioFigura.update(figura);
			}
		}
	}
	
	@Override
	public void agregaLinea(Linea linea) {
		Figura figura = linea.getFigura();
		if (figura == null) {
			throw new IllegalArgumentException(ERROR_FIGURA_NULA);
		}
		refrescaFigura(figura);
		repositorioLinea.create(linea);
	}

	@Override
	public void agregaCirculo(Circulo circulo) {
		Figura figura = circulo.getFigura();
		if (figura == null) {
			throw new IllegalArgumentException(ERROR_FIGURA_NULA);
		}
		refrescaFigura(figura);
		repositorioCirculo.create(circulo);
	}

	@Override
	public void agregaRectangulo(Rectangulo rectangulo) {
		Figura figura = rectangulo.getFigura();
		if (figura == null) {
			throw new IllegalArgumentException(ERROR_FIGURA_NULA);
		}
		refrescaFigura(figura);
		repositorioRectangulo.create(rectangulo);
	}
	
	@Override
	public Linea obtenLinea(Long idLinea) {
		Linea linea = repositorioLinea.read(idLinea);
		if (linea == null) {
			throw new IllegalArgumentException(ERROR_LINEA_ID);
		}
		return linea;
	}
	
	@Override
	public Circulo obtenCirculo(Long idCirculo) {
		Circulo circulo = repositorioCirculo.read(idCirculo);
		if (circulo == null) {
			throw new IllegalArgumentException(ERROR_CIRCULO_ID);
		}
		return circulo;
	}
	
	@Override
	public Rectangulo obtenRectangulo(Long idRectangulo) {
		Rectangulo rectangulo = repositorioRectangulo.read(idRectangulo);
		if (rectangulo == null) {
			throw new IllegalArgumentException(ERROR_RECTANGULO_ID);
		}
		return rectangulo;
	}
	
	@Override
	public Linea eliminaLinea(Long idLinea) {
		Linea linea = obtenLinea(idLinea);
		repositorioLinea.delete(linea);
		repositorioFigura.delete(linea.getFigura());
		return linea;
	}
	
	@Override
	public Circulo eliminaCirculo(Long idCirculo) {
		Circulo circulo = obtenCirculo(idCirculo);
		repositorioCirculo.delete(circulo);
		repositorioFigura.delete(circulo.getFigura());
		return circulo;
	}
	
	@Override
	public Rectangulo eliminaRectangulo(Long idRectangulo) {
		Rectangulo rectangulo = obtenRectangulo(idRectangulo);
		repositorioRectangulo.delete(rectangulo);
		repositorioFigura.delete(rectangulo.getId());
		return rectangulo;
	}

	@Override
	public List<FiguraDTO> listaFiguras() {
		List<FiguraDTO> resultado = new ArrayList<>();
		resultado.addAll(figuraDTOTraductor.lineas2DTO(listaLineas()));
		resultado.addAll(figuraDTOTraductor.circulos2DTO(listaCirculos()));
		resultado.addAll(figuraDTOTraductor.rectangulos2DTO(listaRectangulos()));
		return resultado;
	}
	
	@Override
	public List<Linea> listaLineas() {
		return repositorioLinea.list();
	}
	
	@Override
	public List<Circulo> listaCirculos() {
		return repositorioCirculo.list();
	}
	
	@Override
	public List<Rectangulo> listaRectangulos() {
		return repositorioRectangulo.list();
	}

}
