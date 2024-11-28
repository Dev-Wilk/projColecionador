// Adiciona um ouvinte de evento ao formulário para tratar o evento de envio (submit)
document.querySelector("form").addEventListener("submit", async (event) => {
    console.log("Formulário enviado com sucesso!");
    event.preventDefault(); // Impede o comportamento padrão de envio do formulário para que possamos controlar com JavaScript

    // Captura os valores dos campos do formulário pelo ID e armazena nas variáveis correspondentes
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    // valida nome
    if (nome.length < 3) {
        alert("O campo Nome deve ter pelo menos 3 caracteres.");
        document.getElementById("nome").focus();
        return;
    }

    // valida email
    if (email.length < 3) {
        alert("O campo E-mail deve ter pelo menos 3 caracteres.");
        document.getElementById("email").focus();
        return;
    }

   // balida login
    if (login.length < 3) {
        alert("O campo Login deve ter pelo menos 3 caracteres.");
        document.getElementById("login").focus();
        return;
    }

    // valida senha
    if (senha.length < 3) {
        alert("O campo Senha deve ter pelo menos 3 caracteres.");
        document.getElementById("senha").focus();
        return;
    }

    
    const usuario = { nome, email, login, senha };

    try {
        
        const response = await fetch("http://localhost:9090/Colecionador/rest/usuario/cadastrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(usuario),
        });

        // Resposta do servidor
        if (response.ok) {
            const data = await response.json();
            alert("Cadastro realizado com sucesso!"); 
            window.location.href = "Telalogin.html"; 
        } else {
            const error = await response.json();
            alert(`Erro ao cadastrar: ${error.mensagem || "Verifique os dados e tente novamente."}`); 
        }
    } catch (error) {
        console.error("Erro ao conectar ao servidor:", error);
        alert("Ocorreu um erro ao processar a solicitação. Tente novamente mais tarde."); 
    }
});
