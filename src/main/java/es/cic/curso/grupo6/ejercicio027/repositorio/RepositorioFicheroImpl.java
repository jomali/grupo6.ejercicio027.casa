package es.cic.curso.grupo6.ejercicio027.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

@Repository
@Transactional
public class RepositorioFicheroImpl extends RepositorioAbstractoImpl<Long, Fichero> implements RepositorioFichero {

	@Override
	public Class<Fichero> obtenClaseT() {
		return Fichero.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Fichero.class.getSimpleName().toUpperCase();
	}

}
