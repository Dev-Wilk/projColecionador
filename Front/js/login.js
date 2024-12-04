// Adiciona um ouvinte para o evento "submit" no formulário de login
document.getElementById("loginForm").addEventListener("submit", async (event) => {
    // Previne o comportamento padrão do formulário, que seria recarregar a página ao enviar
    event.preventDefault();

    // Obtém os valores dos campos "username" e "password" do formulário
    const login = document.getElementById("username").value; // Captura o nome de usuário
    const senha = document.getElementById("password").value; // Captura a senha

    // Cria um objeto contendo os dados de login e senha para envio ao servidor
    const dados = { login, senha };

    try {
        // Faz uma requisição HTTP POST para o endpoint de login
        const response = await fetch("http://localhost:8080/colecionador/rest/usuario/login", {
            method: "POST", // Método HTTP usado para enviar os dados de login
            headers: {
                "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
            },
            body: JSON.stringify(dados), // Converte o objeto "dados" para uma string JSON para envio
        });

        // Verifica se a resposta do servidor foi bem-sucedida (status HTTP 2xx)
        if (response.ok) {
            const data = await response.json(); // Lê o corpo da resposta como JSON
            // Verifica se o ID do usuário retornado é válido
            if (data.idUsuario > 0) {
                alert("Login efetuado com sucesso!"); // Notifica o usuário sobre o sucesso no login
                // Armazena os dados do usuário logado no "sessionStorage" para uso posterior
                sessionStorage.setItem("usuarioLogado", JSON.stringify(data));
                // Redireciona o usuário para a página inicial
                window.location.href = "/index.html";
            } else {
                // Exibe uma mensagem de erro caso as credenciais estejam incorretas
                alert("As credenciais informadas estão incorretas. Por favor, tente novamente.");
            }
        } else {
            // Trata respostas não bem-sucedidas do servidor (ex.: status HTTP 4xx ou 5xx)
            const error = await response.json(); // Lê o corpo da resposta de erro como JSON
            alert(`Não foi possível realizar o login: ${error.mensagem || "Credenciais inválidas."}`); // Exibe a mensagem de erro retornada
        }
    } catch (error) {
        // Trata erros relacionados à comunicação com o servidor (ex.: conexão falhou)
        console.error("Erro ao conectar ao servidor:", error); // Loga o erro no console para depuração
        alert("Houve um problema ao processar sua solicitação. Tente novamente em breve."); // Notifica o usuário sobre o erro
    }
});
