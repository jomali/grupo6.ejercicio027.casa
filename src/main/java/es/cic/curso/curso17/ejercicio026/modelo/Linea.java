package es.cic.curso.curso17.ejercicio026.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.cic.curso.grupo6.ejercicio027.modelo.Identificable;

@Entity
@Table(name = "LINEA")
public class Linea implements Identificable<Long> {
	private static final long serialVersionUID = 4087639649260479504L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la figura registrada en BB.DD. */
	@JoinColumn(name = "id_figura")
	@OneToOne(fetch = FetchType.EAGER)
	private Figura figura;

	/** Coordenada del segundo punto en el eje X con respecto al origen. */
	@Column(name = "x2")
	private Double x2;

	/** Coordenada del segundo punto en el eje Y con respecto al origen. */
	@Column(name = "y2")
	private Double y2;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the figura
	 */
	public Figura getFigura() {
		return figura;
	}

	/**
	 * @return the x2
	 */
	public Double getX2() {
		return x2;
	}

	/**
	 * @return the y2
	 */
	public Double getY2() {
		return y2;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param figura
	 *            the figura to set
	 */
	public void setFigura(Figura figura) {
		this.figura = figura;
	}

	/**
	 * @param x2
	 *            the x2 to set
	 */
	public void setX2(Double x2) {
		this.x2 = x2;
	}

	/**
	 * @param y2
	 *            the y2 to set
	 */
	public void setY2(Double y2) {
		this.y2 = y2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((figura == null) ? 0 : figura.hashCode());
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
		Linea other = (Linea) obj;
		if (figura == null) {
			if (other.figura != null)
				return false;
		} else if (!figura.equals(other.figura))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Linea [id=" + id + ", figura=" + figura + ", x2=" + x2 + ", y2=" + y2 + "]";
	}

}
