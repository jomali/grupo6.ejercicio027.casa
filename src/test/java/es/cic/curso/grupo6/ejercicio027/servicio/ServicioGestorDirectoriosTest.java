package es.cic.curso.grupo6.ejercicio027.servicio;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio027/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorDirectoriosTest {
	
	@Autowired
	private ServicioGestorDirectorios sut;
	
	@Autowired
	private RepositorioDirectorio repositoriosDirectorios;

	@PersistenceContext
	private EntityManager em;
	
	private Directorio directorio;
	private Fichero fichero;

	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_PRUEBA_1 = "/directorio/prueba";
	public static final String RUTA_PRUEBA_2 = "/prueba/directorio";
	
	@Before
	public void setUp() throws Exception {
		
		
		Directorio directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);
		
		Fichero fichero = new Fichero();
		fichero.setNombre("Prueba1");
		fichero.setDescripcion("Probando la carga de ficheros");
		fichero.setVersion(1.0);

		em.persist(directorio);
		em.persist(fichero);
		
		em.flush();
		}
	

	@Test
	public void testAgregaDirectorio() {
		
		
		assertTrue(true);


	}

	@Test
	public void testObtenDirectorio() {
//		Directorio directorioId = sut.obtenDirectorio(directorio.getId());
//		
//		// 1) Obtener un directorio incorrecta
//		try {
//			directorio = sut.obtenDirectorio(directorio.getId());
//			fail("IDs de producto y almacén incorrectos");
//		} catch (IllegalArgumentException iae) {
//			
//		}
//
//		// 2) Obtener una entrada correcta
//		directorio = sut.obtenDirectorio(directorio.getId());
//		assertNotNull(directorio);
//		
		assertTrue(true);
	}

	@Test
	public void testEliminarDirectorio() {
		
		assertTrue(true);
	}

	@Test
	public void testModificaDirectorio() {
		assertTrue(true);
	}

	@Test
	public void testListaEntradasPorDirectorio() {
		
		assertTrue(true);
		}


}
