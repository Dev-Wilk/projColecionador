// Seleção de elementos do DOM
const form = document.querySelector('#formulario');
const limpar = document.querySelector('#limpar');
const exibir = document.querySelector('#exibir');
const principal = document.querySelector('.principal');
const inputFile = document.getElementById('arquivo');
const fileName = document.getElementById('nome-arquivo');
const tabelaCorpo = document.getElementById('tbody');
const usuarioLogadoStr = sessionStorage.getItem("usuarioLogado");
const usuarioLogado = JSON.parse(usuarioLogadoStr);
const userId = usuarioLogado?.idUsuario || null;

// Verifica se o usuário está logado
if (!userId) {
    alert("Usuário não está logado. Redirecionando para a tela de login.");
    window.location.href = "TelaLogin.html";
}

// Atualiza o nome do arquivo selecionado
inputFile.addEventListener('change', () => {
    fileName.textContent = inputFile.files.length > 0 ? inputFile.files[0].name : 'Nenhum arquivo selecionado';
});

// Alterna a exibição do formulário
exibir.addEventListener("click", () => {
    const isDisplayed = principal.style.display === 'flex';
    principal.style.display = isDisplayed ? 'none' : 'flex';
    exibir.textContent = isDisplayed ? 'Mostrar' : 'Ocultar';
});

// Limpa o formulário
limpar.addEventListener('click', () => {
    form.reset();
    inputFile.value = '';
    fileName.textContent = 'Nenhum arquivo selecionado';
});

// Função para buscar moedas do backend e preencher a tabela
async function buscarMoedas() {
    try {
        console.log(`Buscando moedas para o usuário: ${userId}`);
        const response = await fetch(`http://localhost:9090/Colecionador/rest/moeda/listar/${userId}`);

        if (response.ok) {
            const moedas = await response.json();
            console.log("Moedas recebidas do backend:", moedas);

            if (moedas.length === 0) {
                tabelaCorpo.innerHTML = '<tr><td colspan="8">Nenhuma moeda encontrada.</td></tr>';
                return;
            }

            preencherTabela(moedas);
        } else {
            const errorMessage = await response.text();
            console.error("Erro ao buscar moedas:", errorMessage);
            alert("Erro ao buscar moedas: " + errorMessage);
        }
    } catch (error) {
        console.error("Erro ao buscar moedas:", error);
        alert("Erro ao buscar moedas. Verifique sua conexão com o servidor.");
    }
}

// Função para preencher a tabela com os dados das moedas
function preencherTabela(moedas) {
    tabelaCorpo.innerHTML = ''; // Limpa o conteúdo atual da tabela

    moedas.forEach(moeda => {
        const linha = document.createElement('tr');

        // Coluna da imagem
        const imgCelula = document.createElement('td');
        if (moeda.imagem) {
            const img = document.createElement('img');
            img.src = `data:image/jpeg;base64,${moeda.imagem}`;
            img.style.width = '50px';
            img.style.height = '50px';
            imgCelula.appendChild(img);
        } else {
            imgCelula.textContent = 'Sem imagem';
        }

        linha.appendChild(imgCelula);

        // Colunas de dados
        linha.innerHTML += `
            <td>${moeda.idMoeda}</td>
            <td>${moeda.nomeMoeda}</td>
            <td>${moeda.pais}</td>
            <td>${moeda.ano}</td>
            <td>R$ ${moeda.valor.toFixed(2)}</td>
            <td>${moeda.detalhes}</td>
        `;

        // Coluna de ações
        const acoesCelula = document.createElement('td');
        const botaoEditar = document.createElement('button');
        botaoEditar.textContent = 'Editar';
        botaoEditar.className = 'acao-button';
        botaoEditar.onclick = () => editarMoeda(moeda);

        const botaoExcluir = document.createElement('button');
        botaoExcluir.textContent = 'Excluir';
        botaoExcluir.className = 'acao-button';
        botaoExcluir.onclick = () => excluirMoeda(moeda);

        acoesCelula.appendChild(botaoEditar);
        acoesCelula.appendChild(botaoExcluir);
        linha.appendChild(acoesCelula);

        tabelaCorpo.appendChild(linha);
    });
}

// Função para enviar o formulário (cadastrar ou atualizar moeda)
form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const action = form.dataset.action || 'cadastrar';
    const file = inputFile.files[0];

    const moedaVO = {
        idMoeda: form.dataset.moedaId || null,
        nomeMoeda: document.getElementById('nome').value,
        pais: document.getElementById('pais').value,
        ano: parseInt(document.getElementById('ano').value),
        valor: parseFloat(document.getElementById('valor').value),
        detalhes: document.getElementById('detalhes').value,
        idUsuario: userId,
    };

    const formData = new FormData();
    formData.append('moedaVO', new Blob([JSON.stringify(moedaVO)], { type: 'application/json' }));
    
    // Apenas adiciona o arquivo ao FormData se ele foi selecionado
    if (file) {
        formData.append('file', file);
    }else {
        formData.append('file', null);
    }

    try {
        const endpoint = action === 'cadastrar' ? 'cadastrar' : 'atualizar';
        const response = await fetch(`http://localhost:9090/Colecionador/rest/moeda/${endpoint}`, {
            method: action === 'cadastrar' ? 'POST' : 'PUT',
            body: formData,
        });

        if (response.ok) {
            alert(`${action === 'cadastrar' ? 'Cadastro' : 'Atualização'} realizado com sucesso.`);
            form.reset();
            principal.style.display = 'none';
            exibir.textContent = 'Mostrar';
            buscarMoedas();
        } else {
            const errorMessage = await response.text();
            console.error("Erro ao salvar moeda:", errorMessage);
            alert("Erro ao salvar moeda: " + errorMessage);
        }
    } catch (error) {
        console.error("Erro ao salvar moeda:", error);
        alert("Erro ao salvar moeda. Tente novamente.");
    }
});

// Função para excluir uma moeda
async function excluirMoeda(moeda) {
    if (confirm('Tem certeza de que deseja excluir esta moeda?')) {
        try {
            const response = await fetch('http://localhost:9090/Colecionador/rest/moeda/excluir', {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ idMoeda: moeda.idMoeda }),
            });

            if (response.ok) {
                alert('Moeda excluída com sucesso.');
                buscarMoedas();
            } else {
                const errorMessage = await response.text();
                console.error("Erro ao excluir moeda:", errorMessage);
                alert("Erro ao excluir moeda: " + errorMessage);
            }
        } catch (error) {
            console.error("Erro ao excluir moeda:", error);
            alert("Erro ao excluir moeda. Tente novamente.");
        }
    }
}

// Função para editar uma moeda
function editarMoeda(moeda) {
    form.dataset.action = 'atualizar';
    form.dataset.moedaId = moeda.idMoeda;
    document.getElementById('nome').value = moeda.nomeMoeda;
    document.getElementById('pais').value = moeda.pais;
    document.getElementById('ano').value = moeda.ano;
    document.getElementById('valor').value = moeda.valor;
    document.getElementById('detalhes').value = moeda.detalhes;
    principal.style.display = 'flex';
    exibir.textContent = 'Ocultar';
}

// Inicializa a página carregando as moedas
buscarMoedas();
