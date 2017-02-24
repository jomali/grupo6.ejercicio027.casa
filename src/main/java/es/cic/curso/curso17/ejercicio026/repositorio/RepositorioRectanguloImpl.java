package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;

@Repository
@Transactional
public class RepositorioRectanguloImpl extends RepositorioAbstractoImpl<Long, Rectangulo> implements RepositorioRectangulo {

	@Override
	public Class<Rectangulo> obtenClaseT() {
		return Rectangulo.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Rectangulo.class.getSimpleName().toUpperCase();
	}

}
