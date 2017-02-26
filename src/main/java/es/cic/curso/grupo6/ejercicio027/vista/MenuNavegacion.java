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
		String texto;
		String direccion;
		
		EntradaMenu(String texto, String direccion) {
			this.texto = texto;
			this.direccion = direccion;
		}
	}

	// /////////////////////////////////////////////////////////////////////////

	private Navigator navegador;
	private List<EntradaMenu> entradas;

	public MenuNavegacion(Navigator navegador) {
		this.navegador = navegador;
		this.entradas = new ArrayList<>();
	}

	public void agregaEntrada(String texto, String direccion) {
		entradas.add(new EntradaMenu(texto, direccion));
	}

	public void eliminaEntrada(String direccion) {
		for (EntradaMenu entrada : entradas) {
			if (entrada.direccion.equalsIgnoreCase(direccion)) {
				entradas.remove(entrada);
			}
		}
	}

	/**
	 * Crea un componente de tipo <code>MenuBar</code> para navegar por las
	 * diferentes vistas de la aplicación. La entrada de menú que se corresponda
	 * con la vista actual debe mostrarse desabilitada (no tiene sentido navegar
	 * a una vista en la que ya se está).
	 * 
	 * @param entradaActual
	 *            Cadena de texto con la dirección actual
	 * @return Barra de menú con enlaces a vistas de la aplicación
	 */
	public MenuBar creaMenu(String entradaActual) {
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		for (EntradaMenu entrada : entradas) {
			@SuppressWarnings("serial")
			MenuItem menuItem = menuNavegacion.addItem(entrada.texto, new Command() {
				@Override
				public void menuSelected(final MenuItem entradaSeleccionada) {
					navegador.navigateTo(entrada.direccion);
				}
			});
			menuItem.setEnabled(entrada.direccion.equalsIgnoreCase(entradaActual) ? false : true);
		}

		return menuNavegacion;
	}

}
