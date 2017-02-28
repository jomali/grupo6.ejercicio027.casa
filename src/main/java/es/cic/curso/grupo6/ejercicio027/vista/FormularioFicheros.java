package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

public class FormularioFicheros extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1842474819854513394L;

	@PropertyId("ruta")
	protected TextField ruta;	
	@PropertyId("nombre")
	protected TextField nombre;
	@PropertyId("descripcion")
	protected TextField descripcion;
	@PropertyId("version")
	protected TextField version;
	
	private Button aceptar;
	private Button cancelar;
	private Fichero fichero;
	
	private VistaDocumentos padre;
	
	public FormularioFicheros(VistaDocumentos padre) {
		this.padre = padre;
		
		VerticalLayout verticalPrincipal= new VerticalLayout();

		HorizontalLayout nombreLayout = new HorizontalLayout();
		nombre = new TextField("Nombre del Archivo: ");
		nombre.setInputPrompt("Nombre del Archivo:");
		nombreLayout.addComponent(nombre);
		HorizontalLayout descripcionLayout = new HorizontalLayout();
		descripcion = new TextField("Descripión: ");
		descripcion.setInputPrompt("Descripión");
		descripcionLayout.addComponent(descripcion);
		HorizontalLayout versionLayout = new HorizontalLayout();
		version = new TextField("Versión: ");
		version.setInputPrompt("Versión");
		versionLayout.addComponent(version);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		aceptar = new Button("Aceptar");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			//padre.cargaGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VistaFile
		});
		cancelar = new Button("Cancelar");
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
