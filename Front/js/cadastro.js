// Adiciona um ouvinte de evento ao formulário para capturar o envio
document.querySelector("form").addEventListener("submit", async (event) => {
    console.log("Formulário enviado com sucesso!"); // Log de depuração para indicar que o formulário foi enviado
    event.preventDefault(); // Impede o comportamento padrão de recarregar a página ao submeter o formulário

    // Captura os valores dos campos de entrada do formulário
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    // Validações de campo para garantir que os dados atendem aos critérios mínimos
    if (nome.length < 3) {
        alert("O campo Nome deve possuir no mínimo 3 caracteres."); // Exibe um alerta caso o nome seja muito curto
        document.getElementById("nome").focus(); // Move o foco para o campo Nome para facilitar a correção
        return; // Encerra o processamento do formulário
    }

    if (email.length < 3) {
        alert("O campo E-mail deve possuir no mínimo 3 caracteres."); // Alerta para e-mail inválido
        document.getElementById("email").focus(); // Foco no campo E-mail
        return;
    }

    if (login.length < 3) {
        alert("O campo Login deve possuir no mínimo 3 caracteres."); // Alerta para login inválido
        document.getElementById("login").focus(); // Foco no campo Login
        return;
    }

    if (senha.length < 3) {
        alert("O campo Senha deve possuir no mínimo 3 caracteres."); // Alerta para senha inválida
        document.getElementById("senha").focus(); // Foco no campo Senha
        return;
    }

    // Cria um objeto com os dados do usuário para envio ao servidor
    const usuario = { nome, email, login, senha };

    try {
        // Envia uma solicitação HTTP para o backend para cadastrar o usuário
        const response = await fetch("http://localhost:8080/colecionador/rest/usuario/cadastrar", {
            method: "POST", // Define o método HTTP como POST para envio de dados
            headers: {
                "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
            },
            body: JSON.stringify(usuario), // Converte o objeto do usuário em uma string JSON para envio
        });

        // Verifica se a resposta do servidor foi bem-sucedida
        if (response.ok) {
            const data = await response.json(); // Converte a resposta para JSON, caso necessário
            alert("Cadastro efetuado com sucesso!"); // Informa o usuário sobre o sucesso
            window.location.href = "login.html"; // Redireciona para a página de login
        } else {
            // Trata erros retornados pelo servidor
            const error = await response.json(); // Converte o erro para JSON
            alert(`Erro ao cadastrar: ${error.mensagem || "Verifique os dados e tente novamente."}`); // Exibe a mensagem de erro
        }
    } catch (error) {
        // Trata erros de conexão ou falhas ao tentar acessar o servidor
        console.error("Erro ao conectar ao servidor:", error); // Exibe o erro no console para depuração
        alert("Ocorreu um erro ao processar a solicitação. Tente novamente em breve."); // Alerta o usuário sobre a falha
    }
});
