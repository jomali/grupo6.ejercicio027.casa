package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

public class VistaFormFile extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1842474819854513394L;

	@PropertyId("nombre")
	protected TextField nombre;
	@PropertyId("descripcion")
	protected TextField descripcion;
	@PropertyId("version")
	protected TextField version;
	
	private Button aceptar;
	private Button cancelar;
	private Fichero fichero;
	
	private VistaFiles padre;
	
	public VistaFormFile(VistaFiles padre) {
		this.padre = padre;
		
		VerticalLayout verticalPrincipal= new VerticalLayout();

		HorizontalLayout nombreLayout = new HorizontalLayout();
		nombre = new TextField("File Name: ");
		nombre.setInputPrompt("File Name:");
		nombreLayout.addComponent(nombre);
		HorizontalLayout descripcionLayout = new HorizontalLayout();
		descripcion = new TextField("Description: ");
		descripcion.setInputPrompt("Description");
		descripcionLayout.addComponent(descripcion);
		HorizontalLayout versionLayout = new HorizontalLayout();
		version = new TextField("Version: ");
		version.setInputPrompt("Version");
		versionLayout.addComponent(version);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		aceptar = new Button("Ok");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			//padre.cargaGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VistaFile
		});
		cancelar = new Button("Cancel");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e-> {
			//padre.borrarGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VisaHome
		});
		buttonsLayout.addComponents(aceptar, cancelar);
		
		addComponents(verticalPrincipal);
		
		
		//setFichero(null);
	}
}
