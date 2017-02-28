# GRUPO6.EJERCICIO027


## Introducción

Se plantea el desarrollo de un prototipo de aplicación web encargada de gestionar documentos. Las funcionalidades básicas que suele incluir una aplicación de este tipo incluyen cargar/descargar documentos en un servidor, registrar ciertos metadatos relacionados con cada documento (fecha de la última modificación, autor...) y consultar los documentos que un usuario tiene cargados.

Referencias:

+ <https://en.wikipedia.org/wiki/Document_management_system>
+ <http://www.arquitecturajava.com/gestion-de-ficheros-con-java-nio-path/>


## Casos de uso

Punto de partida del desarrollo:

1. Operaciones CRUD sobre directorios.  
1.1. Creación de directorios sobre un directorio raíz del servidor. **Dos directorios no pueden tener la misma ruta.**  
1.2. Modificación. Sólo se puede modificar un directorio si no existen ficheros que cuelguen de él.  
1.3. Eliminación. Si se elimina un directorio, se eliminan también todos los ficheros que cuelguen de él.  
1.4. Listado de directorios.  
1.5. Listado de ficheros que cuelgan de un directorio.  
2. Operaciones CRUD sobre ficheros.  
2.1. Creación. Los ficheros deben colgar siempre de un directorio. (Se debe haber creado un directorio antes de crear el fichero).  
2.2. Modificación. Modificación del nombre. Mover fichero a un directorio diferente.  
2.3. Eliminación.  
2.4. Consulta de un fichero.  
2.5. Listado de ficheros.  
3. Subir/Bajar archivos (API Ficheros Java)


## Estructura del proyecto

+ es.cic.curso.grupo6.ejercicio027.modelo
    - Identificable
    - Directorio
    - Fichero
+ es.cic.curso.grupo6.ejercicio027.repositorio
	- Repositorio
	- RepositorioAbstractoImpl
	- RepositorioDirectorio
	- RepositorioDirectorioImpl
	- RepositorioFichero
	- RepositorioFicheroImpl
+ es.cic.curso.grupo6.ejercicio027.servicio
	- ServicioGestorDirectorios 
	- ServicioGestorDirectoriosImpl
	- ServicioGestorFicheros
	- ServicioGestorFicherosImpl
+ es.cic.curso.grupo6.ejercicio027.vista
	- MyUI
	- VistaDocumentos
	- VistaDemo
	- FormularioFichero


## Tareas pendientes

[ ] Estudiar la utilización de FicheroDTO
[x] Comprobación en los servicios: dos directorios no pueden compartir una misma ruta
[x] Uso de API de ficheros dentro de ServicioGestorDirectorios
[x] Tests de ServicioGestorDirectorios con API de ficheros  
[ ] Uso de API de ficheros dentro de ServicioGestorFicheros
[ ] Tests de ServicioGestorFicheros con API de ficheros
[x] Cohesión de idioma a lo largo de todo el proyecto. (Evitar el uso indistinto de Español e Inglés)  
[x] Desarrollo de VistaDocumentos
[ ] Integración completa de funcionalidades dentro de la vista  
[ ] Preparación de la presentación  
[ ] Creación de documento para el cliente  

