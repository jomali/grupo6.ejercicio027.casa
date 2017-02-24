package es.cic.curso.curso17.ejercicio026.servicio;

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

import es.cic.curso.curso17.ejercicio026.dto.FiguraDTO;
import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Figura;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/curso17/ejercicio026/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorFigurasTest {
	
	private static final int NUMERO_FIGURAS = 10;

	@Autowired
	private ServicioGestorFiguras sut;
	
	@PersistenceContext
	private EntityManager em;
	
	private Figura creaFiguraPrueba() {
		Figura figura = new Figura();
		figura.setX(10.0);
		figura.setY(10.0);
		figura.setColor("#000000");
		figura.setRotacion(0);
		return figura;
	}
	
	private Linea creaLineaPrueba() {
		Linea linea = new Linea();
		linea.setFigura(creaFiguraPrueba());
		linea.setX2(20.0);
		linea.setY2(20.0);
		return linea;
	}
	
	private Circulo creaCirculoPrueba() {
		Circulo circulo = new Circulo();
		circulo.setFigura(creaFiguraPrueba());
		circulo.setRadio(10.0);
		return circulo;
	}
	
	private Rectangulo creaRectanguloPrueba() {
		Rectangulo rectangulo = new Rectangulo();
		rectangulo.setFigura(creaFiguraPrueba());
		rectangulo.setAncho(25.0);
		rectangulo.setAlto(10.0);
		return rectangulo;
	}

	@Test
	public void testAgregaLinea() {
		Linea linea;
		
		linea = creaLineaPrueba();
		assertNull(linea.getId());
		assertNull(linea.getFigura().getId());
		
		sut.agregaLinea(linea);
		assertNotNull(linea.getId());
		assertNotNull(linea.getFigura().getId());
	}
	
	public void testAgregaCirculo() {
		Circulo circulo;
		
		circulo = creaCirculoPrueba();
		assertNull(circulo.getId());
		assertNull(circulo.getFigura().getId());
		
		sut.agregaCirculo(circulo);
		assertNotNull(circulo.getId());
		assertNotNull(circulo.getFigura().getId());
	}
	
	public void testAgregaRectangulo() {
		Rectangulo rectangulo;
		
		rectangulo = creaRectanguloPrueba();
		assertNull(rectangulo.getId());
		assertNull(rectangulo.getFigura().getId());
		
		sut.agregaRectangulo(rectangulo);
		assertNotNull(rectangulo.getId());
		assertNotNull(rectangulo.getFigura().getId());
	}
	
	public void testListas() {
		for (int i = 0; i < NUMERO_FIGURAS; i++) {
			sut.agregaLinea(creaLineaPrueba());
			sut.agregaCirculo(creaCirculoPrueba());
			sut.agregaRectangulo(creaRectanguloPrueba());
		}
		
		List<FiguraDTO> figuras = sut.listaFiguras();
		assertEquals((NUMERO_FIGURAS * 3), figuras.size());
		
		List<Linea> lineas = sut.listaLineas();
		assertEquals(NUMERO_FIGURAS, lineas.size());
		
		List<Circulo> circulos = sut.listaCirculos();
		assertEquals(NUMERO_FIGURAS, circulos.size());
		
		List<Rectangulo> rectangulos = sut.listaRectangulos();
		assertEquals(NUMERO_FIGURAS, rectangulos.size());
	}

}
