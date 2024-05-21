package com.literatura.app.client;

import java.util.List;
import java.util.Scanner;

import com.literatura.app.entity.AutorEntity;
import com.literatura.app.entity.LibroEntity;
import com.literatura.app.mapper.transformarDatos;
import com.literatura.app.record.Respuesta;
import com.literatura.app.repository.AutorRepository;
import com.literatura.app.repository.LibroRepository;
import com.literatura.app.service.requestAPI;

public class Client {
    private final Scanner scanner = new Scanner(System.in);
	private final requestAPI requestApi = new requestAPI();
	private final transformarDatos conversor = new transformarDatos();
	private final LibroRepository libroRepo;
	private final AutorRepository autorRepo;
	public Client(LibroRepository libroRepo, AutorRepository autorRepo) {
		this.libroRepo = libroRepo;
		this.autorRepo = autorRepo;
	}
	public void menu() {
		var opt = -1;
		while (opt != 6) {
			System.out.println(" Introduce alguna opcion que desee: ");
			System.out.println("1.- Buscar libro por titulo");
			System.out.println("2.- Lista libros registrados");
			System.out.println("3.- Lista autores registrados");
			System.out.println("4.- Lista autores vivos en algun año");
			System.out.println("5.- Listar libros por idioma");
			System.out.println("6 - Salir");
			opt = scanner.nextInt();
			scanner.nextLine();
			switch (opt) {
			case 1 -> buscarLibroWeb();
			case 2 -> searchLibros();
			case 3 -> searchAutores();
			case 4 -> buscarAutoresVivo();
			case 5 -> buscarPorIdiomas();
			case 6 -> {
                        }
			default -> System.out.println("Opción no valida");
			}
		}

	}
	private void sinResultados(){
		System.out.println("\n\n Disculpa, no se encontraron resultados\n\n");
	}

	private void searchLibros() {
		List<LibroEntity> libros = libroRepo.findAll();
		if (!libros.isEmpty()) {
			for (LibroEntity libro : libros) {
				System.out.println("\n\n-------------------------\n");
				System.out.println(" Libros: \n");
				System.out.println(" Titulo: " + libro.getTitulo());
				System.out.println(" Autor: " + libro.getAutor().getNombre());
				System.out.println(" Idioma: " + libro.getLenguaje());
				System.out.println(" Descargas: " + libro.getDescargas());
				System.out.println("\n-------------------------\n\n");
			}
		} else {
			sinResultados();
		}

	}

	private void searchAutores() {
		List<AutorEntity> autores = autorRepo.findAll();

		if (!autores.isEmpty()) {
			for (AutorEntity autor : autores) {
				System.out.println("\n\n-------------------------\n");
				System.out.println(" Autores:");
				System.out.println(" Nombre: " + autor.getNombre());
				System.out.println(" Fecha de Nacimiento: " + autor.getFechaNacimiento());
				System.out.println(" Fecha de Fallecimiento: " + autor.getFechaMuerte());
				System.out.println(" Libros: " + autor.getLibros().getTitulo());
				System.out.println("\n-------------------------\n\n");
			}
		} else {
			sinResultados();
		}

	}

	private void buscarAutoresVivo() {
		System.out.println("Introduzca el Año de vida a buscar: ");
		var year = scanner.nextInt();
		scanner.nextLine();

		List<AutorEntity> autores = autorRepo.findForYear(year);

		if (!autores.isEmpty()) {
			for (AutorEntity autor : autores) {
				System.out.println("\n\n-------------------------\n");
				System.out.println(" Autores Vivos: ");
				System.out.println(" Nombre: " + autor.getNombre());
				System.out.println(" Fecha de Nacimiento: " + autor.getFechaNacimiento());
				System.out.println(" Fecha de Fallecimiento: " + autor.getFechaMuerte());
				System.out.println(" Libros: " + autor.getLibros().getTitulo());
				System.out.println("\n-------------------------\n\n");
			}
		} else {
			sinResultados();
		}
	}

	private void buscarPorIdiomas() {
		System.out.println("\n\nSelecciona un idioma (1.- Español, 2.- Ingles): ");
		var idioma = scanner.nextInt();
		scanner.nextLine();
		String seleccion = "es";//por defecto
		if(idioma == 1) {
			seleccion = "es";
		} else 	if(idioma == 2) {
			seleccion = "en";
		}
		List<LibroEntity> libros = libroRepo.findForLanguaje(seleccion);
		if (!libros.isEmpty()) {
			for (LibroEntity libro : libros) {
				System.out.println("\n\n-------------------------\n");
				System.out.println(" Libros por idioma: ");
				System.out.println(" Titulo: " + libro.getTitulo());
				System.out.println(" Autor: " + libro.getAutor().getNombre());
				System.out.println(" Idioma: " + libro.getLenguaje());
				System.out.println(" Descargas: " + libro.getDescargas());
				System.out.println("\n-------------------------\n\n");
			}
		} else {
			sinResultados();
		}
	}
	private void buscarLibroWeb() {
		Respuesta datos = getDatosSerie();
		if (!datos.results().isEmpty()) {
			LibroEntity libro = new LibroEntity(datos.results().get(0));
            libroRepo.save(libro);
        }
		System.out.println("datos: ");
		System.out.println(datos);
	}
	private Respuesta getDatosSerie() {
		System.out.println("Introduce el nombre del libro a buscar: ");
		var titulo = scanner.nextLine();
		titulo = titulo.replace(" ", "%20");
		System.out.println("titlulo : " + titulo);
        String URL_BASE = "https://gutendex.com/books/?search=";
        System.out.println(URL_BASE + titulo);
		var json = requestApi.getDatos(URL_BASE + titulo);
		System.out.println(json);
        return conversor.getDatos(json, Respuesta.class);
	}
}
