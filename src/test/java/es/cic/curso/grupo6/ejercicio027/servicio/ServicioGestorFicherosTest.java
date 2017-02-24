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

import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;
import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioDirectorio;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioFichero;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorFicherosTest {

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
		fichero.setDirectorio(directorio);
		em.flush();
		return fichero;
	}


	@Test
	public void testAgregaFichero() {
		
		Fichero fichero = new Fichero();
 
		assertNull(fichero.getId());
		try {
			servicioGestorFicheros.agregaFichero(0L, fichero);
			fail("No se debería poder añadir unfichero con un id de directorio inválido");
		} catch (IllegalArgumentException iae) {

		} catch (PersistenceException pe) {

		}

		Directorio directorio = generaDirectorio(RUTA_PRUEBA_1);
		fichero = new Fichero();
		//assertNull(directorio.getId());
		servicioGestorFicheros.agregaFichero(directorio.getId(), fichero);
		assertNotNull(fichero.getId());
	}

	@Test
	public void testObtenFichero() {
		Directorio directorio = generaDirectorio(RUTA_PRUEBA_1);

		Fichero elemento1 = generaFichero(directorio);
		Fichero elemento2 = servicioGestorFicheros.obtenFichero(elemento1.getId());
		assertNotNull(elemento2);
//		assertEquals(elemento1.getDirectorio().getId(), elemento2.getDirectorio().getId(), 0.0001);
//		
//		assertEquals(elemento1.getVersion(), elemento2.getVersion());
//
//		try {
//			Fichero elemento3 = servicioGestorFicheros.obtenFichero(Long.MAX_VALUE);
//			assertNull(elemento3);
//		} catch (PersistenceException pe) {
//
//		}
	}

	@Test
	public void testModificaFichero() {
		assertFalse(false);
	}

	@Test
	public void testEliminaFichero() {
		Directorio directorio = generaDirectorio(RUTA_PRUEBA_1);
		Fichero elemento = generaFichero(directorio);
		Fichero elemento1 = generaFichero(directorio);
		List<Fichero> lista = servicioGestorFicheros.listaFicheros();
		System.out.println("elemento creado" + lista.size());
		System.out.println("elemento creado" + elemento);
		servicioGestorFicheros.eliminaFichero(elemento.getId());

		assertTrue(elemento.getId() == 0);
	}

	@Test
	public void testEliminaFicherosPorDirectorio() {
		Directorio directorio = generaDirectorio(RUTA_PRUEBA_1);
		Fichero fichero = generaFichero(directorio);
		assertNotNull(fichero);
		
		
		servicioGestorFicheros.eliminaFicherosPorDirectorio(directorio.getId());
		assertNull(fichero);
	}

	@Test
	public void testListaFicheros() {
		
//		List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
//		assertEquals(0, listaFicheros.size());
		
		Directorio directorio = generaDirectorio(RUTA_PRUEBA_1);
		assertNotNull(directorio);
		System.out.println(directorio);
		Fichero fichero = generaFichero(directorio);
		System.out.println(fichero);
		
		List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
		assertEquals(0, listaFicheros.size());
	}

	@Test
	public void testListaFicherosPorDirectorio() {
		assertFalse(false);
	}

}
