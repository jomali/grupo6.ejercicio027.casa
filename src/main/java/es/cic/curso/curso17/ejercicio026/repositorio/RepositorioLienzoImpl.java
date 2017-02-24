package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Lienzo;

@Repository
@Transactional
public class RepositorioLienzoImpl extends RepositorioAbstractoImpl<Long, Lienzo> implements RepositorioLienzo {

	@Override
	public Class<Lienzo> obtenClaseT() {
		return Lienzo.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Lienzo.class.getSimpleName().toUpperCase();
	}

}
