const inputFile = document.getElementById('input');




let colecao = [];

inputFile.addEventListener('change' , function(){
    if (inputFile.files.length > 0) {
        fileName = inputFile.files[0].name;
    }else {
            fileName = 'Nenhum arquivo selecionado';
        }
    });








