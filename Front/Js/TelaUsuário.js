// Atualizar Usuário
document.querySelector('form').addEventListener('submit', async (event) => {
    event.preventDefault(); // Evita o comportamento padrão do formulário

    // Captura os valores dos campos do formulário
    const nome = document.querySelector('#name').value.trim();
    const email = document.querySelector('#email').value.trim();
    const login = document.querySelector('#login').value.trim();
    const senha = document.querySelector('#senha').value.trim();

    // Validação para garantir que todos os campos estão preenchidos
    if (!nome || !email || !login || !senha) {
        alert("Preencha todos os campos antes de continuar!");
        return;
    }

    // Recupera o usuário logado da sessão
    const usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    if (!usuarioLogado || !usuarioLogado.idUsuario) {
        alert("Erro: Usuário não logado. Faça login novamente.");
        return;
    }

    // Monta o objeto do usuário a ser atualizado
    const usuarioVO = {
        idUsuario: usuarioLogado.idUsuario,
        nome: nome,
        email: email,
        login: login,
        senha: senha,
    };

    try {
        // Faz a requisição para o backend
        const response = await fetch("http://localhost:9090/Colecionador/rest/usuario/atualizar", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(usuarioVO),
        });

        if (response.ok) {
            const result = await response.json();
            if (result) {
                alert("Usuário atualizado com sucesso!");
                // Atualiza os dados na sessão com os novos valores
                sessionStorage.setItem("usuarioLogado", JSON.stringify({ ...usuarioLogado, ...usuarioVO }));
                console.log("Usuário atualizado e salvo na sessão:", usuarioVO);
            } else {
                alert("Erro: Não foi possível atualizar o usuário.");
            }
        } else {
            // Trata erros HTTP não OK
            const errorMessage = await response.text();
            console.error("Erro do backend:", errorMessage);
            alert(`Erro do servidor: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Erro na comunicação com o servidor:", error);
        alert("Erro ao tentar se comunicar com o servidor. Tente novamente mais tarde.");
    }
});

// Função para deletar usuário
document.getElementById('deletarUsuario').addEventListener('click', async () => {
    // Recupera o ID do usuário logado
    const usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    if (!usuarioLogado || !usuarioLogado.idUsuario) {
        alert("Erro: Usuário não está logado. Faça login novamente.");
        return;
    }

    // Solicita confirmação do usuário
    const confirmacao = confirm("Tem certeza de que deseja excluir este usuário? Esta ação não pode ser desfeita.");
    if (!confirmacao) {
        return; // Cancela a exclusão se o usuário não confirmar
    }

    try {
        // Monta o objeto UsuarioVO a ser enviado
        const usuarioVO = {
            idUsuario: usuarioLogado.idUsuario,
            nome: usuarioLogado.nome, // Inclua outros campos, se necessário
            email: usuarioLogado.email,
            login: usuarioLogado.login,
            senha: usuarioLogado.senha
        };

        // Faz a requisição DELETE ao backend
        const response = await fetch("http://localhost:9090/Colecionador/rest/usuario/excluir", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(usuarioVO),
        });

        if (response.ok) {
            const result = await response.json();
            if (result) {
                alert("Usuário excluído com sucesso!");
                // Remove o usuário da sessão e redireciona para a tela de login
                sessionStorage.removeItem("usuarioLogado");
                window.location.href = "TelaLogin.html";
            } else {
                alert("Erro ao excluir o usuário. Tente novamente.");
            }
        } else {
            const errorMessage = await response.text();
            console.error("Erro do backend:", errorMessage);
            alert(`Erro no servidor: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Erro na comunicação com o servidor:", error);
        alert("Erro ao tentar excluir o usuário. Tente novamente mais tarde.");
    }
});
