package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
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
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

/**
 * Tests de integración sobre la implementación de
 * <code>ServicioGestorFicheros</code>.
 * 
 * <p>
 * <strong>IMPORTANTE:</strong> Se puede dar el caso (improbable) de que la
 * aplicación contenga un documento con el mismo nombre utilizado como
 * directorio de prueba: <em>RUTA_TEST</em> y se produzca un error. Ante esta
 * situación, debería modificarse la ruta de este directorio para poder lanzar
 * las pruebas correctamente.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/27
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorFicherosTest {

	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_TEST = "1842474819854513394test";
	public static final String DIRECTORIO_RUTA_1 = RUTA_TEST + "/directorio1";
	public static final String DIRECTORIO_RUTA_2 = RUTA_TEST + "/directorio2";
	public static final String NOMBRE_FICHERO = "fichero";

	@Autowired
	private ServicioGestorFicheros sut;

	@Autowired
	private ServicioGestorDirectorios servicioGestorDirectorios;

	@PersistenceContext
	private EntityManager em;

	// /////////////////////////////////////////////////////////////////////////

	@Before
	public void setUp() {
		Path rootPath = Paths.get(ServicioGestorDirectorios.DIRECTORIO_BASE + RUTA_TEST);
		try {
			System.out.println("create dir: " + rootPath.toString());
			Files.createDirectory(rootPath);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@After
	public void tearDown() {
		Path rootPath = Paths.get(ServicioGestorDirectorios.DIRECTORIO_BASE + RUTA_TEST);
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("delete file: " + file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					System.out.println("delete dir: " + dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private Directorio generaDirectorioEnBD(String ruta) {
		Directorio directorio = new Directorio();
		directorio.setRuta(ruta);
		servicioGestorDirectorios.agregaDirectorio(directorio);
		return directorio;
	}

	private Directorio[] generaFicherosEnBD() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);
		Fichero fichero1, fichero2;
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			fichero1 = generaFichero(NOMBRE_FICHERO + i);
			sut.agregaFichero(directorio1.getId(), fichero1);
			fichero2 = generaFichero(NOMBRE_FICHERO + i);
			sut.agregaFichero(directorio2.getId(), fichero2);
		}
		return new Directorio[] {directorio1, directorio2};
	}
	
	private Fichero generaFichero(String nombre) {
		Fichero fichero = new Fichero();
		fichero.setNombre(nombre);
		fichero.setDescripcion("Descripción " + nombre);
		fichero.setVersion(1.0);
		return fichero;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testAgregaFichero() {
		Fichero fichero = generaFichero(NOMBRE_FICHERO);

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

	@Ignore
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
		Fichero fichero = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio.getId(), fichero);		
		resultado = sut.obtenFichero(fichero.getId());
		assertEquals(fichero, resultado);
	}

	@Ignore
	@Test
	public void testModificaFichero() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);

		Fichero original = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio1.getId(), original);
		
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

	@Ignore
	@Test
	public void testEliminaFichero() {
		Directorio directorio = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Fichero fichero = generaFichero(NOMBRE_FICHERO);
		sut.agregaFichero(directorio.getId(), fichero);
		
		assertTrue(sut.listaFicheros().size() == 1);
		sut.eliminaFichero(fichero.getId());
		assertTrue(sut.listaFicheros().isEmpty());
	}

	@Ignore
	@Test
	public void testEliminaFicherosPorDirectorio() {
		Directorio directorio1 = generaDirectorioEnBD(DIRECTORIO_RUTA_1);
		Directorio directorio2 = generaDirectorioEnBD(DIRECTORIO_RUTA_2);
		Fichero fichero;
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			fichero = generaFichero(NOMBRE_FICHERO + i);
			sut.agregaFichero(directorio1.getId(), fichero);
			sut.agregaFichero(directorio2.getId(), fichero);
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
		Directorio[] dirs = generaFicherosEnBD();
		List<Fichero> ficheros;

		ficheros = sut.listaFicheros();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA * 2, ficheros.size());
		ficheros = sut.listaFicherosPorDirectorio(dirs[0].getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, ficheros.size());
		ficheros = sut.listaFicherosPorDirectorio(dirs[1].getId());
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, ficheros.size());
	}

}
