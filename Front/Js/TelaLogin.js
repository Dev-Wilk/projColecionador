document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const login = document.getElementById("username").value;
    const senha = document.getElementById("password").value;
    const dados = { login, senha };

    try {
        const response = await fetch("http://localhost:9090/Colecionador/rest/usuario/logar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dados),
        });
        if (response.ok) {
            const data = await response.json();
            if (data.idUsuario > 0) {
                alert("Login efetuado com sucesso!");
                sessionStorage.setItem("usuarioLogado", JSON.stringify(data));
                window.location.href = "/TelaPrincipal.html";
            } else {
                alert("As credenciais informadas estão incorretas. Por favor, tente novamente.");
            }
        } else {
            const error = await response.json();
            alert("Não foi possivel realizar o login: " + (error.mensagem || "Credenciais inválidas."));
        }
    } catch (error) {
        console.error("Erro ao conectar ao servidor:", error);
        alert("Houve um problema ao processar sua solicitação. Tente novamente em breve.");
    }
});document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const login = document.getElementById("username").value;
    const senha = document.getElementById("password").value;
    const dados = { login, senha };

    try {
        const response = await fetch("http://localhost:9090/Colecionador/rest/usuario/logar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dados),
        });
        if (response.ok) {
            const data = await response.json();
            if (data.idUsuario > 0) {
                alert("Login efetuado com sucesso!");
                sessionStorage.setItem("usuarioLogado", JSON.stringify(data));
                window.location.href = window.location.href = "Telaprincipal.html";

            } else {
                alert("As credenciais informadas estão incorretas. Por favor, tente novamente.");
            }
        } else {
            const error = await response.json();
            alert("Não foi possivel realizar o login: " + (error.mensagem || "Credenciais inválidas."));
        }
    } catch (error) {
        console.error("Erro ao conectar ao servidor:", error);
        alert("Houve um problema ao processar sua solicitação. Tente novamente em breve.");
    }
});