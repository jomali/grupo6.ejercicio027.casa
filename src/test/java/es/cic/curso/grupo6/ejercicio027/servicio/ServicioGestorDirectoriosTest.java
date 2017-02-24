package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioDirectorio;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioFichero;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorDirectoriosTest {
	
	@Autowired
	private ServicioGestorDirectorios servicioGestorDirectorios;
	
	@Autowired
	private ServicioGestorFicheros servicioGestorFicheros;
	
	@Autowired
	private RepositorioDirectorio repositoriosDirectorios;
	
	@Autowired
	private RepositorioFichero repositoriosFicheros;

	@PersistenceContext
	private EntityManager em;
	
	private Directorio directorio;
	private Fichero fichero;

	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_PRUEBA_1 = "/directorio/prueba";
	public static final String RUTA_PRUEBA_2 = "/prueba/directo";

	private Directorio generaDirectorio(String ruta){
		
		Directorio directorio = new Directorio();
		directorio.setRuta(ruta);
		em.persist(directorio);
		em.flush();
		return directorio;
	}
	
	private Fichero generaFichero(Directorio directorio){
		
		Fichero fichero = new Fichero();
		fichero.setNombre("ags");
		fichero.setDescripcion("dafdas");
		fichero.setVersion(3.0);
		fichero.setDirectorio(directorio);
		em.persist(fichero);
		em.flush();
		return fichero;
	}

	@Test
	public void testAgregaDirectorio() {
		
		Directorio directorio;
		directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);
		assertNull(directorio.getId());
		
		servicioGestorDirectorios.agregaDirectorio(directorio);
		assertNotNull(directorio.getId());
	}

	@Test
	public void testObtenDirectorio() {
		
		Directorio elemento1 = generaDirectorio(RUTA_PRUEBA_1);
		Directorio elemento2 = servicioGestorDirectorios.obtenDirectorio(elemento1.getId());
		assertEquals(elemento1.getRuta(), elemento2.getRuta());

		try {
			Directorio elemento3 = servicioGestorDirectorios.obtenDirectorio(Long.MAX_VALUE);
			assertNull(elemento3);
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testEliminarDirectorio() {
		Directorio directorio;

		
		directorio = generaDirectorio(RUTA_PRUEBA_1);
		assertNotNull(directorio.getId());
		servicioGestorDirectorios.eliminarDirectorio(directorio.getId());
		
		try {
			@SuppressWarnings("unused")
			Directorio resultado = servicioGestorDirectorios.obtenDirectorio(directorio.getId());
			fail("La directorio ya no debería estar registrada en BB.DD.");
		} catch (IllegalArgumentException iae) {
			
		}
		assertTrue(directorio.getId() == 0);
	}

	@Test
	public void testModificaDirectorio() {
		
		Directorio original, clon, modificado;

		// 1) Directorio sin sesiones programadas
		original = generaDirectorio(RUTA_PRUEBA_1);
		clon = new Directorio();
		clon.setId(original.getId());
		clon.setRuta(original.getRuta());

		original.setRuta(RUTA_PRUEBA_2);
		servicioGestorDirectorios.modificaDirectorio(original.getId(), original);

		modificado = servicioGestorDirectorios.obtenDirectorio(original.getId());
		assertFalse(original.getRuta().equals(modificado.getRuta()));

	}

	@Test
	public void testListaEntradasPorDirectorio() {
		
		directorio = generaDirectorio(RUTA_PRUEBA_1);
		directorio = generaDirectorio(RUTA_PRUEBA_2);

		List<Directorio> lista = servicioGestorDirectorios.listaEntradasPorDirectorio(directorio.getId());
		assertEquals(2, lista.size());
	}


}
