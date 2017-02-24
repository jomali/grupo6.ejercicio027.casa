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

	
	public static final int NUMERO_ELEMENTOS_PRUEBA = 10;
	public static final String RUTA_PRUEBA_1 = "/directorio/prueba";
	public static final String RUTA_PRUEBA_2 = "/prueba/directorio";
	
	@Before
	public void setUp() throws Exception {
		
		
		Directorio directorio = new Directorio();
		directorio.setRuta(RUTA_PRUEBA_1);

		em.persist(directorio);

		em.flush();
		}
	

	@Test
	public void testAgregaDirectorio() {
		
		Directorio directorio;
		
		directorio = new Directorio();
		assertNull(directorio.getId());


	}

	@Test
	public void testObtenDirectorio() {
		Directorio directorioId = sut.obtenDirectorio(directorio.getId());
		
		// 1) Obtener un directorio incorrecta
		try {
			directorio = sut.obtenDirectorio(directorio.getId());
			fail("IDs de producto y almacén incorrectos");
		} catch (IllegalArgumentException iae) {
			
		}

		// 2) Obtener una entrada correcta
		directorio = sut.obtenDirectorio(directorio.getId());
		assertNotNull(directorio);
		
	}

	@Test
	public void testEliminarDirectorio() {
		
		Directorio directorioId = sut.obtenDirectorio(directorio.getId());
		assertNotNull(directorioId);
		directorioId = sut.eliminarDirectorio(directorio.getId());
		assertNull(directorioId);
	}

	@Test
	public void testModificaDirectorio() {
		assertTrue(true);
	}

	@Test
	public void testListaEntradasPorDirectorio() {
		Directorio directorioId = sut.obtenDirectorio(directorio.getId());
		assertTrue(directorioId.getId() == directorio.getId());
		}


}
