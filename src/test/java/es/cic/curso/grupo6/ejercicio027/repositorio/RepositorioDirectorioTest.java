package es.cic.curso.grupo6.ejercicio027.repositorio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioDirectorioTest {
	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	
	@Autowired
	private RepositorioDirectorio sut;

	private Fichero generaElementoPrueba() {
		Fichero elemento = new Fichero();
		elemento.setRuta("/directorio/prueba/");
		
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Fichero elemento;
		
		elemento = new Fichero();
		assertNull(elemento.getId());

		elemento = generaElementoPrueba();
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Fichero elemento1 = generaElementoPrueba();
		Fichero elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getNombre().equals(elemento2.getNombre()));
		assertEquals(elemento1.getCapacidad(), elemento2.getCapacidad(), 0.0001);

		try {
			@SuppressWarnings("unused")
			Fichero elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Fichero original = generaElementoPrueba();
		Fichero clon = new Fichero();
		clon.setId(original.getId());
		clon.setRuta(original.getRuta());

		original.setNombre("Principal");
		original.setCapacidad(2000);
		sut.update(original);

		Fichero modificado = sut.read(original.getId());
		assertEquals(original.getNombre(), modificado.getNombre());
		assertNotEquals(clon.getCapacidad(), modificado.getCapacidad());
	}

	@Test
	public void testDelete() {
		Fichero elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Fichero resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			generaElementoPrueba();
		}

		List<Fichero> lista = sut.list();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
	}

}
