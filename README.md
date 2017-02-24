# GRUPO6.EJERCICIO027


## Introducción

Se plantea el desarrollo de un prototipo de aplicación web encargada de gestionar documentos. Las funcionalidades básicas que suele incluir una aplicación de este tipo incluyen cargar/descargar documentos en un servidor, registrar ciertos metadatos relacionados con cada documento (fecha de la última modificación, autor...) y consultar los documentos que un usuario tiene cargados.

Referencias:

+ <https://en.wikipedia.org/wiki/Document_management_system>
+ <http://www.arquitecturajava.com/gestion-de-ficheros-con-java-nio-path/>


## Casos de uso

Punto de partida del desarrollo:

1. Operaciones CRUD sobre directorios.  
1.1. Creación de directorios sobre un directorio raíz del servidor.  
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


## Reparto de tareas

[x] 1. Creación del proyecto. **José Fco.**  
[x] 2. Crear proyecto en GitLab. **José María**  
[x] 3. Dar de alta tarea en Jenkins. **José María**  
[ ] 4. Diseñar BB.DD.  
[ ] 5. Definición del modelo  
[ ] 6. Definición del fichero: *changelog.xml*  
[ ] 7. Definición de los repositorios  
[ ] 8. Tests sobre los repositorios  
[ ] 9. Definición de los servicios  
[ ] 10. Tests sobre los servicios  
[ ] 11. Diseño de las vistas de la aplicación  
[ ] 12. Desarrollo de vistas en paralelo  
[ ] 13. Refinamiento de las vistas  
[ ] 14. Presentación  
[ ] 15. Creación de documento para el cliente  
