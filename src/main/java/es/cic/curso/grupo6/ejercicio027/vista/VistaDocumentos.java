package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDocumentos extends VerticalLayout implements View {
	private static final long serialVersionUID = 6070082071055226969L;
	
	// Servicios con lógica de negocio y acceso a BB.DD.
	
	private ServicioGestorFicheros servicioGestorFicheros;
	private ServicioGestorDirectorios servicioGestorDirectorios;

	// Componentes gráficos:
	
	@PropertyId("ruta")
	protected TextField textFieldRuta;

	private Grid gridDirectorios;
	private Button botonAgregarD, botonBorrarD, botonActualizarD;
	private Directorio eliminaDirectorio;
	private Directorio actualizaDirectorio;
	private Directorio nuevoDirectorio;
	private Grid gridFicheros;
	private Button botonAgregarF, botonBorrarF, botonActualizarF;
	private Fichero fichero;
	private Fichero eliminaFichero;
	private List<Directorio> listaDirectorios;
	private FormularioFicheros formularioFicheros;
	
	
	public VistaDocumentos() {
		
		// Servicios con lógica de negocio y acceso a BB.DD.

		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);

		// GRID de DIRECTORIOS

		gridDirectorios = new Grid();
		gridDirectorios.setColumns("ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.setCaption("Lista Directorios:");
		gridDirectorios.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				Directorio directorio = (Directorio) gridDirectorios.getSelectedRow();
				if (directorio != null) {
					actualizaDirectorio = directorio;
					eliminaDirectorio = directorio;
					botonAgregarD.setVisible(false);
					botonActualizarD.setVisible(true);
					botonBorrarD.setVisible(true);
					muestraDirectorio(directorio);
					cargaGridFicheros(directorio);
					
				} else {
					botonBorrarD.setVisible(false);
					botonActualizarD.setVisible(false);
					botonAgregarD.setVisible(true);
					reiniciaTextField();
				}
				
			}
		});
		
		// GRID de FICHEROS

		gridFicheros = new Grid();
		gridFicheros.setColumns("directorio", "nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.setCaption("Lista Directorios:");
		gridFicheros.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				Fichero fchero =  (Fichero) gridFicheros.getSelectedRow();
				if(fchero!=null){
					eliminaFichero = fchero;
					botonBorrarF.setVisible(true);
				}else{
					botonBorrarF.setVisible(false);
				}
			}
		});
		
		// BOTONES DIRECTORIOS
		textFieldRuta = new TextField();
		textFieldRuta.setInputPrompt("Ruta de la Carpeta");
		
		botonAgregarD = new Button("Añadir Directorio");
		botonAgregarD.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregarD.setVisible(true);
		botonAgregarD.setEnabled(true);
		botonAgregarD.addClickListener(agregar -> {
			agregarDirectorio(nuevoDirectorio);
	//		actualizarDirectorio(actualizaDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonAgregarD.setVisible(true);
			botonActualizarD.setVisible(false);
			botonBorrarD.setVisible(false);
			
		});

		botonBorrarD = new Button("Borrar");
		botonBorrarD.setIcon(FontAwesome.ERASER);
		botonBorrarD.setVisible(false);
		botonBorrarD.addClickListener(borrar -> {
			borraDirectorio(eliminaDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonActualizarD.setVisible(false);
			botonBorrarD.setVisible(false);
			
			botonAgregarD.setVisible(true);
		});
		
		botonActualizarD = new Button("Actualizar datos");
		botonActualizarD.setIcon(FontAwesome.REFRESH);
		botonActualizarD.setVisible(false);
		botonActualizarD.setEnabled(true);
		botonActualizarD.addClickListener(actualizar -> {
//			actualizarDirectorio(actualizaDirectorio);
			reiniciaTextField();
			cargaGridDirectorios();
		});
		
		// BOTONES FICHEROS

		botonAgregarF = new Button("Añade fichero");
		botonAgregarF.setVisible(true);
		botonAgregarF.setEnabled(false);

		botonBorrarF = new Button("Borrar");
		botonBorrarF.setIcon(FontAwesome.ERASER);
		botonBorrarF.setVisible(false);
		botonBorrarF.addClickListener(e -> {
			borraFichero(eliminaFichero);
			cargaGridFicheros(null);
		});
		
		botonActualizarF = new Button("Actualizar");
		botonActualizarF.setVisible(true);
		botonActualizarF.setEnabled(false);

		HorizontalLayout layoutBotonesDirectorios = new HorizontalLayout();
		layoutBotonesDirectorios.setMargin(false);
		layoutBotonesDirectorios.setSpacing(true);
		layoutBotonesDirectorios.addComponents(textFieldRuta, botonAgregarD, botonBorrarD, botonActualizarD);
		
		cargaGridDirectorios();

		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);
		layoutBotonesFicheros.addComponents(botonAgregarF, botonActualizarF, botonBorrarF);
		
		// LAYOUT PRINCIPAL
		
		HorizontalLayout principalLayout = new HorizontalLayout();
		principalLayout.setMargin(true);
		principalLayout.setSpacing(true);
		principalLayout.setSizeFull();
		
		VerticalLayout layoutDirectorios = new VerticalLayout();
		layoutDirectorios.setMargin(false);
		layoutDirectorios.setSpacing(true);
		layoutDirectorios.addComponents(gridDirectorios, layoutBotonesDirectorios);
		
		VerticalLayout layoutFicheros = new VerticalLayout();
		layoutFicheros.setMargin(false);
		layoutFicheros.setSpacing(true);
		layoutFicheros.addComponents(gridFicheros, layoutBotonesFicheros);
	
		principalLayout.addComponents(layoutDirectorios, layoutFicheros);
		addComponent(principalLayout);
	}

	

	
	private List<Directorio> cargarLista() {
		return servicioGestorDirectorios.listaDirectorios();
	}
	public void borraFichero(Fichero fichero){
		servicioGestorFicheros.eliminaFichero(fichero.getId());
	}

	public void cargaGridFicheros(Directorio directorio) {
		Collection<Fichero> ficheros = servicioGestorFicheros.listaFicherosPorDirectorio(directorio.getId());
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}

//	public void actualizarDirectorio(Directorio directorio){
//		Directorio nuevoDirectorio = new Directorio();
//		nuevoDirectorio
//		nuevoDirectorio.setRuta(textFieldRuta.getValue());
//		servicioGestorDirectorios.modificaDirectorio(idDirectorio, nuevoDirectorio);
//	}


	public void agregarDirectorio(Directorio directorio){
		Directorio nuevoDirectorio = new Directorio();
		nuevoDirectorio.setRuta(textFieldRuta.getValue());
		servicioGestorDirectorios.agregaDirectorio(nuevoDirectorio);
	}
	
	public void borraDirectorio(Directorio directorio) {
		servicioGestorDirectorios.eliminaDirectorio(directorio.getId());
	}
	
	public void muestraDirectorio(Directorio directorio) {
		textFieldRuta.setValue(directorio.getRuta());
	}
	
	public void reiniciaTextField() {
		textFieldRuta.clear();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridDirectorios();
		
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorDirectorios.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}
}

