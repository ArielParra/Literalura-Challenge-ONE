package com.literatura.app.entity;

import com.literatura.app.record.Autor;
import com.literatura.app.record.Libro;
import com.literatura.app.util.limitSTR;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Libro")
public class LibroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String lenguaje;
	private Integer descargas;
	@OneToOne(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private AutorEntity autor;
	public LibroEntity() {}
	public LibroEntity(Libro libro) {
		this.titulo = limitSTR.limitLen(libro.title(), 200);
		this.descargas = libro.download();
		if (!libro.languages().isEmpty())
			this.lenguaje = libro.languages().get(0);
		if (!libro.autores().isEmpty()) {
			for (Autor autor : libro.autores()) {
				this.autor = new AutorEntity(autor);
				break;
			}
		}

	}
	public String getTitulo() {
		return titulo;
	}
	public String getLenguaje() {
		return lenguaje;
	}
	public Integer getDescargas() {
		return descargas;
	}
	@Override
	public String toString() {
		return "LibroEntity [id=" + id + ", titulo=" + titulo + ", lenguaje=" + lenguaje + ", descargas=" + descargas
				+ ", autores=" + autor + "]";
	}

	public AutorEntity getAutor() {
		return autor;
	}
}
