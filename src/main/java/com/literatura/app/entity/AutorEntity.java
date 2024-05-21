package com.literatura.app.entity;

import com.literatura.app.record.Autor;
import com.literatura.app.util.limitSTR;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Autor")
public class AutorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Integer fechaNacimiento;
	private Integer fechaMuerte;
	 @OneToOne
	    @JoinTable(
	            name = "Libro",
	            joinColumns = @JoinColumn(name = "autor_id"),
	            inverseJoinColumns = @JoinColumn(name = "id"))
	    private LibroEntity libros;
	

	public AutorEntity() {}

	public AutorEntity(Autor autor) {
		this.nombre = limitSTR.limitLen(autor.name(), 200);
		this.fechaNacimiento = (autor.birthYear() == null) ? 1950 : autor.birthYear();
		this.fechaMuerte = (autor.deathYear() == null) ? 3022 : autor.deathYear();
	}
	public String getNombre() { return nombre;}
	public Integer getFechaNacimiento() { return fechaNacimiento;}
	public Integer getFechaMuerte() { return fechaMuerte;}
	@Override
	public String toString() {
		return "AutorEntity [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", fechaFallecimiento=" + fechaMuerte + ", libro="  + "]";
	}
	public LibroEntity getLibros() {
		return libros;
	}

}
