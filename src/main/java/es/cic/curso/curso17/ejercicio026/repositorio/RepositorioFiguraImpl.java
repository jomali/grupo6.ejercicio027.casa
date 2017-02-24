package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Figura;

@Repository
@Transactional
public class RepositorioFiguraImpl extends RepositorioAbstractoImpl<Long, Figura> implements RepositorioFigura {

	@Override
	public Class<Figura> obtenClaseT() {
		return Figura.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Figura.class.getSimpleName().toUpperCase();
	}

}
