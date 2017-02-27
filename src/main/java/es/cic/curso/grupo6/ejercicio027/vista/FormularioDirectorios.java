package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;

public class FormularioDirectorios extends FormLayout{
	private static final long serialVersionUID = 5081842639219654799L;

	@PropertyId("ruta")
	protected TextField ruta;
	
	private Button aceptar;
	private Button cancelar;
	private Directorio directorio;
	private VistaDirectorios padre;
	
	public FormularioDirectorios(VistaDirectorios padre) {
		this.padre = padre;
		
		VerticalLayout verticalPrincipal= new VerticalLayout();

		HorizontalLayout nombreLayout = new HorizontalLayout();
		ruta = new TextField("Ruta de la Carpeta: ");
		ruta.setInputPrompt("Ruta de la Carpeta:");
		nombreLayout.addComponent(ruta);


		HorizontalLayout buttonsLayout = new HorizontalLayout();
		aceptar = new Button("Aceptar");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			
			//TODO Aqui tenemos que hacer que vaya a VistaFile
		});
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e -> {
			verticalPrincipal.setVisible(false);
			padre.setVisible(true);

		});
		buttonsLayout.addComponents(aceptar, cancelar);
		
		addComponents(verticalPrincipal);
		
		estableceDirectorio(null);
	}

	public void estableceDirectorio(Directorio directorio) {  
		this.directorio=directorio;
		
		if(directorio!=null) {
		    BeanFieldGroup.bindFieldsUnbuffered(directorio, this);			
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Directorio(), this);
		}
	}
	
}
