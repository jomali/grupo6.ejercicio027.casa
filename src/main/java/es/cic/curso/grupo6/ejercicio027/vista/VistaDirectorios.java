package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDirectorios extends VerticalLayout implements View {
	private static final long serialVersionUID = 6362449485036174011L;
	
	private Grid gridCarpetas;
	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	private Collection<Directorio> listaDirectorios;
	private ServicioGestorDirectorios servicioGestorDirectorios;
	private Notification alerta;
	Button carga = new Button("Carga");
	Button crea = new Button("Crea");
	Button borra = new Button("Borra");
	Button actualiza = new Button("Actualiza");

	public VistaDirectorios(Navigator navegador) {
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorDirectorios.class);

		MenuNavegacion vista = new MenuNavegacion(navegador);
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		gridCarpetas = new Grid();
		
		gridCarpetas.setColumns("Ruta de la Carpeta");
		gridCarpetas.setSizeFull();
		gridCarpetas.setSelectionMode(SelectionMode.SINGLE);
		
        
		gridCarpetas.setSelectionMode(SelectionMode.SINGLE);
		gridCarpetas.addSelectionListener(new SelectionListener() {

        @Override
           public void select(SelectionEvent event) {
              Directorio carpetaSeleccionada =  (Directorio) gridCarpetas.getSelectedRow();
              carpetaSeleccionada = carpetaSeleccionada;
	  			crea.setVisible(true);
	  			borra.setVisible(true);
	  			actualiza.setVisible(true);
           }
        
        });
		
		
		layout.addComponent(gridCarpetas);
		
		
		HorizontalLayout layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setSpacing(true);
		
		crea.setVisible(false);
		borra.setVisible(false);
		actualiza.setVisible(false);
		
		carga.addClickListener(clickEvent -> {
			cargaGrid();
			carga.setVisible(false);

			
		});
		
		layoutHorizontal.addComponents(carga, crea, borra, actualiza);
		layout.addComponent(layoutHorizontal);
		
		addComponents(vista, layout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void cargaGrid() {
		listaDirectorios = servicioGestorDirectorios.listaDirectorios();
		
		gridCarpetas.setContainerDataSource(
        		new BeanItemContainer<>(Directorio.class, listaDirectorios)
        );
	}


	
}
