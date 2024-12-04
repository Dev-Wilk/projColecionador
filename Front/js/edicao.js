// Adiciona um ouvinte para o evento "submit" no formulário
document.querySelector('form').addEventListener('submit', async (event) => {
    // Previne o comportamento padrão do formulário, que seria recarregar a página ao enviar
    event.preventDefault();

    function preencherFormulario() {
        const usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    
        if (usuarioLogado) {
            document.getElementById("name").value = usuarioLogado.nome;
            document.getElementById("email").value = usuarioLogado.email;
            document.getElementById("login").value = usuarioLogado.login;
            document.getElementById("senha").value = usuarioLogado.senha;
        }
    }
    // Valida se todos os campos estão preenchidos
    if (!nome || !email || !login || !senha) {
        alert("Preencha todos os campos antes de prosseguir!");
        return;
    }

    // Recupera o usuário logado armazenado na sessão do navegador
    const usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    // Verifica se o usuário está logado e se o ID do usuário existe
    if (!usuarioLogado || !usuarioLogado.idUsuario) {
        alert("Erro: Usuário não logado. Faça login novamente.");
        return;
    }

    // Cria um objeto com os dados do usuário para envio ao servidor
    const usuarioVO = {
        idUsuario: usuarioLogado.idUsuario,
        nome: nome,
        email: email,
        login: login,
        senha: senha,
    };

    try {
        // Faz uma requisição HTTP PUT para atualizar os dados do usuário
        const response = await fetch("http://localhost:8080/colecionador/rest/usuario/atualizar", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
            },
            body: JSON.stringify(usuarioVO), // Converte o objeto para uma string JSON
        });

        // Verifica se a resposta do servidor foi bem-sucedida
        if (response.ok) {
            const result = await response.json(); // Lê o corpo da resposta como JSON
            if (result) {
                alert("Usuário atualizado com sucesso!");
                // Atualiza os dados do usuário armazenados na sessão
                sessionStorage.setItem("usuarioLogado", JSON.stringify({ ...usuarioLogado, ...usuarioVO }));
                console.log("Usuário atualizado e salvo na sessão:", usuarioVO);
            } else {
                alert("Erro: Não foi possível atualizar o usuário.");
            }
        } else {
            // Lê a mensagem de erro enviada pelo servidor
            const errorMessage = await response.text();
            console.error("Erro do backend:", errorMessage);
            alert(`Erro do servidor: ${errorMessage}`);
        }
    } catch (error) {
        // Trata erros de comunicação com o servidor
        console.error("Erro na comunicação com o servidor:", error);
        alert("Erro ao tentar se comunicar com o servidor. Tente novamente mais tarde.");
    }
});

// Adiciona um ouvinte para o clique no botão de exclusão do usuário
document.getElementById('deletarUsuario').addEventListener('click', async () => {
    // Recupera o usuário logado armazenado na sessão
    const usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
    // Verifica se o usuário está logado e se o ID do usuário existe
    if (!usuarioLogado || !usuarioLogado.idUsuario) {
        alert("Erro: Usuário não está logado. Faça login novamente.");
        return;
    }

    // Solicita uma confirmação do usuário antes de prosseguir com a exclusão
    const confirmacao = confirm("Tem certeza de que deseja excluir este usuário? Esta ação não pode ser desfeita.");
    if (!confirmacao) {
        return;
    }

    try {
        // Cria um objeto com os dados do usuário para envio ao servidor
        const usuarioVO = {
            idUsuario: usuarioLogado.idUsuario,
            nome: usuarioLogado.nome,
            email: usuarioLogado.email,
            login: usuarioLogado.login,
            senha: usuarioLogado.senha,
        };

        // Faz uma requisição HTTP DELETE para excluir o usuário
        const response = await fetch("http://localhost:8080/colecionador/rest/usuario/excluir", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
            },
            body: JSON.stringify(usuarioVO), // Converte o objeto para uma string JSON
        });

        // Verifica se a resposta do servidor foi bem-sucedida
        if (response.ok) {
            const result = await response.json(); // Lê o corpo da resposta como JSON
            if (result) {
                alert("Usuário excluído com sucesso!");
                // Remove o usuário da sessão e redireciona para a página de login
                sessionStorage.removeItem("usuarioLogado");
                window.location.href = "login.html";
            } else {
                alert("Erro ao excluir o usuário. Tente novamente.");
            }
        } else {
            // Lê a mensagem de erro enviada pelo servidor
            const errorMessage = await response.text();
            console.error("Erro do backend:", errorMessage);
            alert(`Erro no servidor: ${errorMessage}`);
        }
    } catch (error) {
        // Trata erros de comunicação com o servidor
        console.error("Erro na comunicação com o servidor:", error);
        alert("Erro ao tentar excluir o usuário. Tente novamente mais tarde.");
    }
});

window.addEventListener("load", preencherFormulario);