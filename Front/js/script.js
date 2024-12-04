const form = document.querySelector('#formulario');
const limpar = document.querySelector('#limpar');
const exibir = document.querySelector('#exibir');
const principal = document.querySelector('.principal');
const inputFile = document.getElementById('arquivo');
const fileName = document.getElementById('nome-arquivo');

let usuario = {};
let moeda = {};

inputFile.addEventListener('change', function () {
    if (inputFile.files.length > 0) {
        fileName.textContent = inputFile.files[0].name;
    } else {
        fileName.textContent = 'Nenhum arquivo selecionado';
    }
});

exibir.addEventListener('click', function () {
    if (principal.style.display === 'flex') {
        principal.style.display = 'none';
        exibir.innerHTML = 'Mostrar';
    } else {
        principal.style.display = 'flex';
        exibir.innerHTML = 'Ocultar';
    }
});