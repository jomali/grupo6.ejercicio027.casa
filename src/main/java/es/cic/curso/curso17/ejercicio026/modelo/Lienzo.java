package es.cic.curso.curso17.ejercicio026.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.cic.curso.grupo6.ejercicio027.modelo.Identificable;

@Entity
@Table(name = "LIENZO")
public class Lienzo implements Identificable<Long> {
	private static final long serialVersionUID = 8929320440157331808L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia al dibujo del que forma parte el lienzo. */
	@JoinColumn(name = "id_dibujo")
	@ManyToOne(fetch = FetchType.LAZY)
	private Dibujo dibujo;

	/**
	 * Peso del lienzo. Se utiliza para establecer el orden en que se pintan los
	 * distintos lienzos de un dibujo.
	 */
	@Column(name = "peso")
	private int peso;

	/** Descripción breve del lienzo (opcional). */
	@Column(name = "descripcion")
	private String descripcion;

	/** Lista de figuras definidas en el lienzo. */
	@OneToMany(mappedBy = "lienzo")
	private List<Figura> figuras = new ArrayList<>();

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the dibujo
	 */
	public Dibujo getDibujo() {
		return dibujo;
	}

	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the figuras
	 */
	public List<Figura> getFiguras() {
		return figuras;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param dibujo the dibujo to set
	 */
	public void setDibujo(Dibujo dibujo) {
		this.dibujo = dibujo;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param figuras the figuras to set
	 */
	public void setFiguras(List<Figura> figuras) {
		this.figuras = figuras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lienzo other = (Lienzo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
