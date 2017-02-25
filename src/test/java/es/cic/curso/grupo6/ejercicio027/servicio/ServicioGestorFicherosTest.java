package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorFicherosTest {

	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String DIRECTORIO_RUTA_1 = "/directorio/prueba1/";
	public static final String DIRECTORIO_RUTA_2 = "/directorio/prueba2/";
	public static final String NOMBRE_FICHERO = "fichero";

	@Autowired
	private ServicioGestorFicheros sut;

	@PersistenceContext
	private EntityManager em;

	// /////////////////////////////////////////////////////////////////////////

	private Directorio generaDirectorioEnBD(String ruta) {
		Directorio directorio = new Directorio();
		directorio.setRuta(ruta);
		em.persist(directorio);
		em.flush();
		return directorio;
	}

	private Fichero generaFicheroEnBD(Directorio directorio) {
		Fichero fichero = generaFichero();
		fichero.setDirectorio(directorio);
		em.persist(fichero);
		em.flush();
		return fichero;
	}

	private Fichero generaFichero() {
		Fichero fichero = new Fichero();
		fichero.setNombre(NOMBRE_FICHERO);
		fichero.setDescripcion("Descripción " + NOMBRE_FICHERO);
		fichero.setVersion(1.0);
		return fichero;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testAgregaFichero() {
		Fichero fichero = generaFichero();

		// 1) Fichero sobre un directorio no registrado en BB.DD.
		try {
			sut.agregaFichero(0L, fichero);
			fail("No se debería poder añadir un fichero con un ID de directorio inválido");
		} catch (IllegalArgumentException iae) {

		}

		// 2) Fichero sobre un directorio registrado en BB.DD.
		Directorio directorio = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		sut.agregaFichero(directorio.getId(), fichero);
	}

	@Test
	public void testObtenFichero() {
		Fichero resultado = null;

		// 1) Fichero no registrado en BB.DD.
		try {
			resultado = sut.obtenFichero(0L);
			fail("Fichero no registrado en BB.DD.");
		} catch (IllegalArgumentException iae) {
			assertNull(resultado);
		}

		// 2) Fichero registrado en BB.DD.
		Directorio directorio = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Fichero fichero = generaFicheroEnBD(directorio);
		resultado = sut.obtenFichero(fichero.getId());
		assertEquals(fichero, resultado);
	}

	@Test
	public void testModificaFichero() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);
		Fichero original = generaFicheroEnBD(directorio1);

		Fichero clon = new Fichero();
		clon.setId(original.getId());
		clon.setDirectorio(original.getDirectorio());
		clon.setNombre(original.getNombre());
		clon.setDescripcion(original.getDescripcion());
		clon.setVersion(original.getVersion());
		assertEquals(original, clon);

		original.setDirectorio(directorio2);
		sut.modificaFichero(original.getId(), original);
		Fichero modificado = sut.obtenFichero(original.getId());
		assertEquals(original, modificado);
		assertNotEquals(clon, modificado);
	}

	@Test
	public void testEliminaFichero() {
		Directorio directorio = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Fichero fichero = generaFicheroEnBD(directorio);

		assertTrue(sut.listaFicheros().size() == 1);
		sut.eliminaFichero(fichero.getId());
		assertTrue(sut.listaFicheros().isEmpty());
	}

	@Test
	public void testEliminaFicherosPorDirectorio() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			generaFicheroEnBD(directorio1);
			generaFicheroEnBD(directorio2);
			generaFicheroEnBD(directorio2);
		}
		List<Fichero> lista;
		lista = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 3, lista.size());

		sut.eliminaFicherosPorDirectorio(directorio1.getId());
		lista = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 2, lista.size());
	}

	@Test
	public void testListaFicheros() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			generaFicheroEnBD(directorio1);
			generaFicheroEnBD(directorio2);
			generaFicheroEnBD(directorio2);
		}

		List<Fichero> lista;
		lista = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 3, lista.size());
		lista = sut.listaFicherosPorDirectorio(directorio1.getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
		lista = sut.listaFicherosPorDirectorio(directorio2.getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 2, lista.size());
	}

}
