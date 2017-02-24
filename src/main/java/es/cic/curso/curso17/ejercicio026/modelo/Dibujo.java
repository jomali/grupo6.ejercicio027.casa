package es.cic.curso.curso17.ejercicio026.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.cic.curso.grupo6.ejercicio027.modelo.Identificable;

@Entity
@Table(name = "DIBUJO")
public class Dibujo implements Identificable<Long> {
	private static final long serialVersionUID = 2111023717653812314L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Descripción breve del lienzo (opcional). */
	@Column(name = "descripcion")
	private String descripcion;

	/** Lista ordenada de lienzos definidos en el dibujo. */
	@OneToMany(mappedBy = "dibujo")
	private List<Lienzo> lienzos = new ArrayList<>();

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the lienzos
	 */
	public List<Lienzo> getLienzos() {
		return lienzos;
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
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param lienzos
	 *            the lienzos to set
	 */
	public void setLienzos(List<Lienzo> lienzos) {
		this.lienzos = lienzos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
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
		Dibujo other = (Dibujo) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
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
		return "Dibujo [id=" + id + ", descripcion=" + descripcion + ", lienzos=" + lienzos + "]";
	}

}
