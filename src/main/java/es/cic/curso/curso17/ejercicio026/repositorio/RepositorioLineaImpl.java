package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioAbstractoImpl;

@Repository
@Transactional
public class RepositorioLineaImpl extends RepositorioAbstractoImpl<Long, Linea> implements RepositorioLinea {

	@Override
	public Class<Linea> obtenClaseT() {
		return Linea.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Linea.class.getSimpleName().toUpperCase();
	}

}
