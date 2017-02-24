package es.cic.curso.grupo6.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

@Repository
@Transactional
public class RepositorioDirectorioImpl extends RepositorioAbstractoImpl<Long, Fichero> implements RepositorioDirectorio {

	@Override
	public Class<Fichero> obtenClaseT() {
		return Fichero.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Fichero.class.getSimpleName().toUpperCase();
	}

}
