package es.cic.curso.curso17.ejercicio026.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import es.cic.curso.curso17.ejercicio026.dto.FiguraDTO;
import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Figura;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;
import es.cic.curso.curso17.ejercicio026.servicio.ServicioGestorFiguras;

public class VistaFiguras extends VerticalLayout implements View {
	private static final long serialVersionUID = 8801525565672617295L;
	
	private ServicioGestorFiguras servicioGestorFiguras;
	
	// /////////////////////////////////////////////////////////////////////////
	// Referencias  a componentes gráficos
	
	private Grid gridFiguras;
	private Grid gridLineas;
	private Grid gridCirculos;
	private Grid gridRectangulos;
	
	private Linea lineaSeleccionada;
	private Circulo circuloSeleccionado;
	private Rectangulo rectanguloSeleccionado;
	
	// /////////////////////////////////////////////////////////////////////////
	// Métodos públicos

	public VistaFiguras(Navigator navegador, ServicioGestorFiguras servicioGestorFiguras) {
		this.servicioGestorFiguras = servicioGestorFiguras;
		this.setMargin(true);
		this.setSpacing(true);
		
		// TabSheet Figuras:
		TabSheet tabsFiguras = new TabSheet();
		tabsFiguras.setSizeFull();
		tabsFiguras.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabsFiguras.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		tabsFiguras.addTab(creaTabFiguras(), "Todos");
		tabsFiguras.addTab(creaTabLineas(), "Líneas");
		tabsFiguras.addTab(creaTabCirculos(), "Círculos");
		tabsFiguras.addTab(creaTabRectangulos(), "Rectángulos");
		
		addComponent(tabsFiguras);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrids();
	}
	
	public void cargaGrids() {
		List<FiguraDTO> figuras = servicioGestorFiguras.listaFiguras();
		List<Linea> lineas = servicioGestorFiguras.listaLineas();
		List<Circulo> circulos = servicioGestorFiguras.listaCirculos();
		List<Rectangulo> rectangulos = servicioGestorFiguras.listaRectangulos();
		gridFiguras.setContainerDataSource(new BeanItemContainer<>(FiguraDTO.class, figuras));
		gridLineas.setContainerDataSource(new BeanItemContainer<>(Linea.class, lineas));
		gridCirculos.setContainerDataSource(new BeanItemContainer<>(Circulo.class, circulos));
		gridRectangulos.setContainerDataSource(new BeanItemContainer<>(Rectangulo.class, rectangulos));
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Métodos privados
	
	private Figura duplicaFigura(Figura f) {
		Figura figura = new Figura();
		figura.setLienzo(f.getLienzo());
		figura.setX(f.getX());
		figura.setY(f.getY());
		figura.setColor(f.getColor());
		figura.setRotacion(f.getRotacion());
		return figura;
	}
	
	private Linea duplicaLinea(Linea l) {
		Linea linea = new Linea();
		linea.setFigura(duplicaFigura(l.getFigura()));
		linea.setX2(l.getX2());
		linea.setY2(l.getY2());
		return linea;
	}
	
	private Circulo duplicaCirculo(Circulo c) {
		Circulo circulo = new Circulo();
		circulo.setFigura(duplicaFigura(c.getFigura()));
		circulo.setRadio(c.getRadio());
		return circulo;
	}
	
	private Rectangulo duplicaRectangulo(Rectangulo r) {
		Rectangulo rectangulo = new Rectangulo();
		rectangulo.setFigura(duplicaFigura(r.getFigura()));
		rectangulo.setAncho(r.getAncho());
		rectangulo.setAlto(r.getAlto());
		return rectangulo;
	}
	
	private VerticalLayout creaTabFiguras() {		
		gridFiguras = new Grid();
		
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);

		// GRID DE FIGURAS
		gridFiguras.setColumns("id", "tipo", "x", "y", "rotacion", "color");
		gridFiguras.setWidth(100.0F, Unit.PERCENTAGE);
		gridFiguras.setSelectionMode(SelectionMode.SINGLE);
		
		result.addComponent(gridFiguras);

		return result;
	}
	
	private VerticalLayout creaTabLineas() {
		gridLineas = new Grid();
		Button botonDuplicarLinea = new Button("Duplicar Línea");
		Button botonEliminarLinea = new Button("Eliminar Línea");
		
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);

		// GRID DE FIGURAS
		gridLineas.setColumns("id", "figura", "x2", "y2");
		gridLineas.setWidth(100.0F, Unit.PERCENTAGE);
		gridLineas.setSelectionMode(SelectionMode.SINGLE);
		gridLineas.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				lineaSeleccionada = (Linea) e.getSelected().iterator().next();
				botonDuplicarLinea.setEnabled(true);
				botonEliminarLinea.setEnabled(true);
			} else {
				lineaSeleccionada = null;
				botonDuplicarLinea.setEnabled(false);
				botonEliminarLinea.setEnabled(false);
			}
		});
		
		// BOTÓN DUPLICAR/ELIMINAR LINEA
		botonDuplicarLinea.setIcon(FontAwesome.PLUS_SQUARE);
		botonDuplicarLinea.addClickListener(e -> {
			servicioGestorFiguras.agregaLinea(duplicaLinea(lineaSeleccionada));
			cargaGrids();
		});	
		botonDuplicarLinea.setEnabled(false);
		
		botonEliminarLinea.setIcon(FontAwesome.MINUS_SQUARE);
		botonEliminarLinea.addClickListener(e -> {
			Notification.show("Funcionalidad no implementada");
		});
		botonEliminarLinea.setEnabled(false);
		botonEliminarLinea.setVisible(false); // TODO
		
		result.addComponent(gridLineas);
		result.addComponent(botonDuplicarLinea);

		return result;
	}
	
	private VerticalLayout creaTabCirculos() {
		gridCirculos = new Grid();
		Button botonDuplicarCirculo = new Button("Duplicar círculo");
		
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);

		// GRID DE FIGURAS
		gridCirculos.setColumns("id", "figura", "radio");
		gridCirculos.setWidth(100.0F, Unit.PERCENTAGE);
		gridCirculos.setSelectionMode(SelectionMode.SINGLE);
		gridCirculos.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				circuloSeleccionado = (Circulo) e.getSelected().iterator().next();
				botonDuplicarCirculo.setEnabled(true);
			} else {
				botonDuplicarCirculo.setEnabled(false);
			}
		});
		
		// BOTÓN DUPLICAR/ELIMINAR LINEA
		botonDuplicarCirculo.setIcon(FontAwesome.PLUS_SQUARE);
		botonDuplicarCirculo.addClickListener(e -> {
			servicioGestorFiguras.agregaCirculo(duplicaCirculo(circuloSeleccionado));
			cargaGrids();
		});	
		botonDuplicarCirculo.setEnabled(false);
		
		result.addComponent(gridCirculos);
		result.addComponent(botonDuplicarCirculo);

		return result;
	}
	
	private VerticalLayout creaTabRectangulos() {
		gridRectangulos = new Grid();
		Button botonDuplicarRectangulo = new Button("Duplicar rectángulo");
		
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);

		// GRID DE FIGURAS
		gridRectangulos.setColumns("id", "figura", "ancho", "alto");
		gridRectangulos.setWidth(100.0F, Unit.PERCENTAGE);
		gridRectangulos.setSelectionMode(SelectionMode.SINGLE);
		gridRectangulos.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				rectanguloSeleccionado = (Rectangulo) e.getSelected().iterator().next();
				botonDuplicarRectangulo.setEnabled(true);
			} else {
				botonDuplicarRectangulo.setEnabled(false);
			}
		});
		
		// BOTÓN AÑADIR FIGURA
		botonDuplicarRectangulo.setIcon(FontAwesome.PLUS_SQUARE);
		botonDuplicarRectangulo.addClickListener(e -> {
			servicioGestorFiguras.agregaRectangulo(duplicaRectangulo(rectanguloSeleccionado));
			cargaGrids();
		});
		
		result.addComponent(gridRectangulos);
		result.addComponent(botonDuplicarRectangulo);

		return result;
	}

}
