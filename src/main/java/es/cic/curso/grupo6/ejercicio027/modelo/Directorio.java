package es.cic.curso.grupo6.ejercicio027.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DIRECTORIO")
public class Directorio implements Identificable<Long> {
	private static final long serialVersionUID = -8760299749061904850L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ruta")
	private String ruta;

	@JoinColumn(name = "id_directorio")
	@OneToMany(fetch = FetchType.LAZY)
	private Fichero directorio;

	public Directorio() {
		super();

	}

	public Directorio(String ruta, Fichero directorio) {
		super();
		this.ruta = ruta;
		this.directorio = directorio;
	}

	public Directorio(Long id, String ruta, Fichero directorio) {
		super();
		this.id = id;
		this.ruta = ruta;
		this.directorio = directorio;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Fichero getDirectorio() {
		return directorio;
	}

	public void setDirectorio(Fichero directorio) {
		this.directorio = directorio;
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
		Directorio other = (Directorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fichero [id=" + id + ", ruta=" + ruta + ", directorio=" + directorio.getId() + "]";
	}

}
