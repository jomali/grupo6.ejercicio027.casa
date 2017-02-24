package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Dibujo;

@Repository
@Transactional
public class RepositorioDibujoImpl extends RepositorioAbstractoImpl<Long, Dibujo> implements RepositorioDibujo {

	@Override
	public Class<Dibujo> obtenClaseT() {
		return Dibujo.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Dibujo.class.getSimpleName().toUpperCase();
	}

}
