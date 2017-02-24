package es.cic.curso.curso17.ejercicio026.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.cic.curso.grupo6.ejercicio027.modelo.Identificable;

/**
 * Representa una figura geométrica básica. Todas las clases para figuras
 * concretas deben tener una referencia a esta clase.
 * 
 * 
 * @author J. Francisco Martín
 * @version 2.0
 * @serial 2017/02/22
 *
 */
@Entity
@Table(name = "FIGURA")
public class Figura implements Identificable<Long> {
	private static final long serialVersionUID = 5435373730372673256L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia al lienzo del que forma parte la figura. */
	@JoinColumn(name = "id_lienzo")
	@ManyToOne(fetch = FetchType.LAZY)
	private Lienzo lienzo;

	/**
	 * Coordenada del origen con respecto a un punto de referencia en el eje X.
	 */
	@Column(name = "x")
	private Double x;

	/**
	 * Coordenada del origen con respecto a un punto de referencia en el eje Y.
	 */
	@Column(name = "y")
	private Double y;

	/** Rotación en sentido horario. (En grados). */
	@Column(name = "rotacion")
	private int rotacion;

	/** Color de la figura (cadena con su representación hexadecimal). */
	@Column(name = "color")
	private String color;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the lienzo
	 */
	public Lienzo getLienzo() {
		return lienzo;
	}

	/**
	 * @return the x
	 */
	public Double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}

	/**
	 * @return the rotacion
	 */
	public int getRotacion() {
		return rotacion;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param lienzo the lienzo to set
	 */
	public void setLienzo(Lienzo lienzo) {
		this.lienzo = lienzo;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Double x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}

	/**
	 * @param rotacion the rotacion to set
	 */
	public void setRotacion(int rotacion) {
		this.rotacion = rotacion;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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
		Figura other = (Figura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Figura [id=" + id + ", x=" + x + ", y=" + y + ", rotacion=" + rotacion + ", color=" + color + "]";
	}

}
