package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuNavegacion {

	// /////////////////////////////////////////////////////////////////////////
	// Clase privada
	
	private class EntradaMenu {
		MenuItem componente;
		String direccion;
	}

	// /////////////////////////////////////////////////////////////////////////
	
	private Navigator navegador;
	private List<EntradaMenu> entradas;
	private MenuBar menu;
	
	public MenuNavegacion(Navigator navegador) {
		this.navegador = navegador;
		this.entradas = new ArrayList<>();
		this.menu = new MenuBar();
		this.menu.setWidth(100.0F, Unit.PERCENTAGE);
	}
	
	@SuppressWarnings("serial")
	public void agregaEntrada(String texto, String direccion) {
		EntradaMenu entrada = new EntradaMenu();
		entrada.direccion = direccion;
		entrada.componente = menu.addItem(texto, new Command() {
			@Override
			public void menuSelected(final MenuItem entradaSeleccionada) {
				navegador.navigateTo(direccion);
			}
		});
		entradas.add(entrada);
	}
	
	public void eliminaEntrada(String direccion) {
		for (EntradaMenu entrada : entradas) {
			if (entrada.direccion.equalsIgnoreCase(direccion)) {
				entradas.remove(entrada);
				menu.removeItem(entrada.componente);
			}
		}
	}
	
	public void seleccionaEntrada(String direccion) {
		for (EntradaMenu entrada : entradas) {
			entrada.componente.setEnabled((entrada.direccion.equalsIgnoreCase(direccion)) ? false : true);
		}
	}
	
	public MenuBar obtenMenu() {
		return menu;
	}
		
		
//		MenuItem entrada = menu.addItem(texto, new Command)
//		
//
//		MenuBar menuNavegacion = new MenuBar();
//		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
//		menuNavegacion.setHeight(100.0F, Unit.PERCENTAGE);
//		MenuItem menuItemVistaCine = menuNavegacion.addItem("Cine", null);
//		menuItemVistaCine.setEnabled(false);
//		menuNavegacion.addItem("Películas", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				navegador.navigateTo(MyUI.VISTA_PELICULAS);
//			}
//		});
//		menuNavegacion.addItem("Ventas", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				navegador.navigateTo(MyUI.VISTA_HISTORIAL_VENTAS);
//			}
//		});
//		addComponent(menuNavegacion);
//	}


	
	
	
//	
//	
//	MenuItem menuItemVistaCine = menuNavegacion.addItem("Cine", null);
//
//
//	private Navigator navegador;
//
//	private VerticalLayout layout;
//	private VerticalLayout cuerpo;
//	
//	@SuppressWarnings("serial")
//	public MenuNavegacion(Navigator navegador) {
//		super();
//		this.navegador = navegador;
//		
//		layout = new VerticalLayout();
//		layout.setMargin(true);
//		layout.setSpacing(true);
//		cuerpo = new VerticalLayout();
//		cuerpo.setMargin(true);
//		cuerpo.setSpacing(true);
//		MenuBar menuNavegacion = new MenuBar();
//		
//		menuNavegacion.addItem("INICIO", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				navegador.navigateTo("");
//			}
//		});
//		
//		menuNavegacion.addItem("DIRECTORIOS", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				navegador.navigateTo(MyUI.VISTA_DIRECTORIOS);
//			}
//		});
//		
//		menuNavegacion.addItem("FICHEROS", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				navegador.navigateTo(MyUI.VISTA_FICHEROS);
//			}
//		});	
//		
//		layout.addComponent(menuNavegacion);
//		layout.addComponent(cuerpo);
//		
//		this.setCompositionRoot(layout);	
//	}
	
}
