// Abre el cuadro de selección de archivos del input correspondiente
function triggerFileInput(caja) {
    caja.querySelector('.file-input').click();
}

// Previsualiza la imagen seleccionada dentro de la caja
function previewImage(input) {
    const file = input.files[0];
    const tiposPermitidos = ["image/jpeg", "image/png", "image/gif", "image/webp"];

    if(file && !tiposPermitidos.includes(file.type)){
        alert("Por favor, selecciona un archivo de imagen válido (JPEG, PNG, GIF o WEBP).");
    }
    else {
        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
                let img = input.parentElement.querySelector('img');
                // Si ya existe una imagen, la reutilizamos; si no, creamos una nueva
                if (!img) {
                    img = document.createElement('img');
                    input.parentElement.appendChild(img);
                }

                img.src = e.target.result; // Asigna la imagen como fondo de la caja
            };

            reader.readAsDataURL(file);


        } else {    //No ha seleccionado archivo y hay foto la foto
            let img = input.parentElement.querySelector('img');

            if (img) {
                img.remove();
            }
        }
    }
}