package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Ignore;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorDirectoriosTest {
	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_PRUEBA_1 = "1842474819854513394_directorio1";
	public static final String RUTA_PRUEBA_2 = "1842474819854513394_directorio2";
	
	@Autowired
	private ServicioGestorDirectorios servicioGestorDirectorios;

	@PersistenceContext
	private EntityManager em;
	
	// /////////////////////////////////////////////////////////////////////////

	private Directorio generaDirectorio(String ruta){
		Directorio directorio = new Directorio();
		directorio.setRuta(ruta);
		em.persist(directorio);
		em.flush();
		return directorio;
	}
	
	private void limpia(String ruta) {
		Path path = Paths.get(ServicioGestorDirectorios.DIRECTORIO_BASE + ruta);

		try {
		    Files.delete(path);
		    System.out.println("Directorio/Fichero eliminado con éxito: " + path.toAbsolutePath());
		} catch (IOException e) {
		    //deleting file failed
		    e.printStackTrace();
		}
	}
	
	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testAgregaDirectorio() {
		Directorio directorio;
		
		// 1) Introduce un directorio válido
		directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);
		assertNull(directorio.getId());
		
		servicioGestorDirectorios.agregaDirectorio(directorio);
		assertNotNull(directorio.getId());
		
		// 2) Introduce un directorio inválido
		directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);
		try {
			servicioGestorDirectorios.agregaDirectorio(directorio);
			fail("No se debería poder crear un directorio en esa ruta");
		} catch (IllegalArgumentException iae) {
			
		}
		
		limpia(RUTA_PRUEBA_1);
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

	@Ignore
	@Test
	public void testEliminarDirectorio() {
		Directorio directorio;

		
		directorio = generaDirectorio(RUTA_PRUEBA_1);
		assertNotNull(directorio.getId());
		servicioGestorDirectorios.eliminaDirectorio(directorio.getId());
		
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

		original = generaDirectorio(RUTA_PRUEBA_1);
		
		clon = new Directorio();
		clon.setId(original.getId());
		clon.setRuta(original.getRuta());

		original.setRuta(RUTA_PRUEBA_2);
		servicioGestorDirectorios.modificaDirectorio(original.getId(), original);

		modificado = servicioGestorDirectorios.obtenDirectorio(original.getId());
		
		assertTrue(original.getRuta().equals(modificado.getRuta()));

	}


}
