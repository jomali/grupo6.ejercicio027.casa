package es.cic.curso.curso17.ejercicio026.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioAbstractoImpl;

@Repository
@Transactional
public class RepositorioCirculoImpl extends RepositorioAbstractoImpl<Long, Circulo> implements RepositorioCirculo {

	@Override
	public Class<Circulo> obtenClaseT() {
		return Circulo.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Circulo.class.getSimpleName().toUpperCase();
	}

}
