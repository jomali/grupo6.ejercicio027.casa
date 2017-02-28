package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
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
	private LayoutFicheros maestro;
	
	public FormularioFicheros(VistaDocumentos padre, LayoutFicheros maestro) {
		this.padre = padre;
		this.maestro = maestro;
		
		VerticalLayout verticalPrincipal= new VerticalLayout();
		verticalPrincipal.setMargin(true);
		verticalPrincipal.setSpacing(true);
		
		HorizontalLayout datosLayout = new HorizontalLayout();
//		datosLayout.setMargin(true);
		datosLayout.setSpacing(true);
		nombre = new TextField("Nombre: ");
		nombre.setInputPrompt("Nombre:");
		descripcion = new TextField("Descripción: ");
		descripcion.setInputPrompt("Descripción");
		version = new TextField("Versión: ");
		version.setInputPrompt("Versión");


		HorizontalLayout buttonsLayout = new HorizontalLayout();
//		datosLayout.setMargin(true);
		buttonsLayout.setSpacing(true);
		aceptar = new Button("Aceptar");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			//padre.cargaGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VistaFile
		});
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e-> {
//			padre.cargaGridDirectorios();
			verticalPrincipal.setVisible(false);
//			maestro.activaBotonAgregarFichero(true);
			
		});
		datosLayout.addComponents(nombre, descripcion, version);
		buttonsLayout.addComponents(aceptar, cancelar);
		verticalPrincipal.addComponents(datosLayout, buttonsLayout);
		addComponents(verticalPrincipal);

		setFichero(null);
	}
	
	public void setFichero(Fichero fichero) {
		this.setVisible(fichero != null);
		this.fichero = fichero;

		if (fichero != null) {
			BeanFieldGroup.bindFieldsUnbuffered(fichero, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Fichero(), this);

		}
	}
	
//	public void crearFichero(String nombre, String descripcion, double version) {
//		Fichero f = new Fichero();
//		
//		setFichero(f);
//		padre.setContainerDataSource(new BeanItemContainer<>(Fichero.class, listaFicheros));
//		servicioGestorFicheros.agregaFichero(idDirectorio, fichero);
//	}
}
