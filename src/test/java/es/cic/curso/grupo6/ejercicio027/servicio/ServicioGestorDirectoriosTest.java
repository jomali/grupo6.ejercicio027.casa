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

import org.junit.After;
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

/**
 * <strong>IMPORTANTE:</strong> Tests de integración sobre la implementación de
 * <code>ServicioGestorDirectorios</code>. Se puede dar el caso (improbable) de
 * que la aplicación contenga un documento con el mismo nombre utilizado como
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
public class ServicioGestorDirectoriosTest {

	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_TEST = "1842474819854513394test";
	public static final String RUTA_PRUEBA = RUTA_TEST + "/directorio";

	@Autowired
	private ServicioGestorDirectorios sut;

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

	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testAgregaDirectorio() {
		Directorio directorio;

		// 1) Introduce un directorio válido
		directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA);
		assertNull(directorio.getId());

		sut.agregaDirectorio(directorio);
		assertNotNull(directorio.getId());

		// 2) Introduce un directorio inválido
		directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA);
		try {
			sut.agregaDirectorio(directorio);
			fail("No se debería poder crear un directorio en esa ruta");
		} catch (IllegalArgumentException iae) {

		}
	}

	@Test
	public void testObtenDirectorio() {
		Directorio resultado;

		// 1) Obtener un directorio que no está en BB.DD.
		try {
			resultado = sut.obtenDirectorio(0L);
			fail("El directorio no existe en BB.DD.");
		} catch (IllegalArgumentException iae) {

		}

		// 2) Obtener un directorio que está en BB.DD.
		Directorio directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA);
		sut.agregaDirectorio(directorio);
		resultado = sut.obtenDirectorio(directorio.getId());
		assertEquals(directorio, resultado);
	}

	@Test
	public void testEliminarDirectorio() {
		Directorio resultado;
		
		// 1) Eliminar un directorio que está en BB.DD.
		Directorio directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA);
		sut.agregaDirectorio(directorio);
		resultado = sut.eliminaDirectorio(directorio.getId());
		assertNotNull(resultado);
		
		// 2) Eliminar un directorio que no está en BB.DD.
		try {
			resultado = sut.eliminaDirectorio(directorio.getId());
			fail("El directorio no existe en BB.DD.");
		} catch(IllegalArgumentException iae) {
			
		}
	}
	
	@Test
	public void testListaDirectorios() {
		Directorio directorio;
		for (int i = 0; i < NUMERO_ELEMENTOS_PRUEBA; i++) {
			directorio = new Directorio();
			directorio.setRuta(RUTA_PRUEBA + i);
			sut.agregaDirectorio(directorio);
		}

		List<Directorio> lista = sut.listaDirectorios();
		assertEquals(NUMERO_ELEMENTOS_PRUEBA, lista.size());
	}

}
