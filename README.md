- ¿Qué sucede si intentas borrar una encuesta que no existe? ¿Cómo lo has controlado?
Tal y como tengo yo estrucurado mi programa no se muestra una encuesta que no existe, si lo ponemos en la barra de direccion simplemente redirige a la pagina principal

- Si introduces en un texto del tipo <style>body background-color:red</style> en uno de los
campos ¿Qué sucede al ver la encuesta?
Lo agrege al dar de alta una nueva encuesta en el campo de comentario, simplemente lo que pasa es que se visualiza un texto
 ¿el navegador ejecuta ese código o no? ¿porqué?
No lo ejecuta porque gracias a thymeleaf, por defecto lo permite
¿cómo podrías hacer que se ejecute ese código o que se deje de ejecutar?
cambiando th:text por th:utext
- ¿Qué has tenido que modificar en el repositorio para filtrar por motivo de búsqueda? ¿has
tenido que escribir el código específico o como lo has realizado?
A nivel visual cree un formulario con un select y un boton de filtar, y dentro de la interfaz del repositorio el siguiente método :
 List<Encuesta> findBynivelSatisfaccion(String satisfaccion);
