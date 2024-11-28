function formatarDataPadraoBrasil (data) {
  let dataFormatada = new Date(data),
      dia = dataFormatada.getDate().toString().padStart(2, '0'),
      mes = (dataFormatada.getMonth()+1).toString().padStart(2, '0'), 
      ano = dataFormatada.getFullYear();
  return dia+"/"+mes+"/"+ano;
}

function formatarDataPadraoAmericano (data) {
  let dataFormatada = new Date(data),
      dia = dataFormatada.getDate().toString().padStart(2, '0'),
      mes = (dataFormatada.getMonth()+1).toString().padStart(2, '0'),
      ano = dataFormatada.getFullYear(); 
  return ano+"-"+mes+"-"+dia;
}


async function cadastrarUsuario() {
  const form = document.querySelector('#formularioCadastro');
  const formData = new FormData(form);

  let usuarioVO = {};
  for (let [key, value] of formData.entries()) {
    usuarioVO[key] = value;
  }

  // Ajusta a data de nascimento para o formato ISO
  usuarioVO.dataNascimento = formatarDataPadraoAmericano(usuarioVO.dataNascimento);

  let options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(usuarioVO)
  };

  try {
    const response = await fetch('http://localhost:9090/Colecionador/rest/usuario/cadastrar', options);

    // Verifica se houve erro na requisição
    if (!response.ok) {
      // Monta a mensagem de erro padrão
      let message = `Erro na requisição: ${response.status}`;

      // Trata o erro específico de email já cadastrado (conflito)
      if (response.status === 409) {
        message = `${message} - Email já cadastrado!`;
      }

      // Exibe a mensagem de erro e lança uma exceção
      alert(message);
      throw new Error(message);
    }

    // Obtém o usuário cadastrado da resposta
    const usuarioCadastrado = await response.json();

    // Verifica se o ID do usuário é válido
    if (usuarioCadastrado.idusuario !== 0) {
      alert('Cadastro realizado com sucesso!');
      form.reset();
      window.location.href = 'login.html';
    } else {
      alert('Erro ao cadastrar. Por favor, tente novamente.');
    }
  } catch (error) {
    console.error('Erro ao cadastrar:', error);
    alert('Erro ao cadastrar. Por favor, tente novamente mais tarde.');
  }
} 



  async function editarUsuario() {
    const form = document.querySelector('#formularioEditarUsuario');
    const formData = new FormData(form);
  
    let usuarioVO = {};
    for (let [key, value] of formData.entries()) {
      usuarioVO[key] = value;
    }
  
    // Obter o ID do usuário do sessionStorage
    const idUsuario = sessionStorage.getItem('id_usuario');
    if (!idUsuario) {
      alert('Usuário não autenticado!');
      return;
    }
    usuarioVO.idusuario = parseInt(idUsuario); 
  
    let options = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(usuarioVO)
    };
  
    try {
      const response = await fetch('http://localhost:9090/Colecionador/rest/usuario/atualizar', options); 
  
      if (!response.ok) {
        throw new Error('Erro na requisição: ' + response.status);
      }
  
      const usuarioAtualizado = await response.json(); 
  
      if (usuarioAtualizado.idusuario !== 0) {
        alert('Usuário atualizado com sucesso!');
        form.reset();
        window.location.href = 'perfil.html'; 
      } else {
        alert('Erro ao atualizar. Por favor, tente novamente.');
      }
  
    } catch (error) {
      console.error('Erro ao atualizar:', error);
      alert('Erro ao atualizar. Por favor, tente novamente mais tarde.');
    }
  }


  async function excluirUsuario() {
    // Obter o ID do usuário do sessionStorage
    const idUsuario = sessionStorage.getItem('id_usuario');
    if (!idUsuario) {
      alert('Usuário não autenticado!');
      return;
    }
  
    // Confirmar exclusão
    if (!confirm('Tem certeza que deseja excluir sua conta? Esta ação é irreversível!')) {
      return; 
    }
  
    let options = {
      method: 'DELETE', 
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ idusuario: parseInt(idUsuario) }) 
    };
  
    try {
      const response = await fetch(`http://localhost:9090/Colecionador/rest/usuario/excluir/${idUsuario}`, options);
  
      if (!response.ok) {
        throw new Error('Erro na requisição: ' + response.status);
      }
  
      // Limpar sessionStorage e redirecionar para a página de login
      sessionStorage.clear();
      alert('Conta excluída com sucesso!');
      window.location.href = 'login.html'; 
  
    } catch (error) {
      console.error('Erro ao excluir usuário:', error);
      alert('Erro ao excluir usuário. Por favor, tente novamente mais tarde.');
    }
  }

  function logarUsuario(login, senha) {
    fetch('/rest/usuario/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ login, senha })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Login falhou');
      }
      return response.json(); 
    })
    .then(data => {
      
      console.log('Login bem-sucedido:', data);
    })
    .catch(error => {
      console.error(error);
    });
  }
  
    try {
      const response = await fetch('http://localhost:9090/Colecionador/rest/usuario/logar', options); 
      
      if (!response.ok) {
        throw new Error('Erro na requisição: ' + response.status);
      }
  
      const usuarioLogado = await response.json(); 
  
      if (usuarioLogado.idusuario !== 0) { 
        sessionStorage.setItem('id_usuario', usuarioLogado.idusuario);
        sessionStorage.setItem('nome_usuario', usuarioLogado.nome);
        window.location.href = 'index.html';
      } else {
        alert('Usuário ou senha inválidos!');
      }
  
    } catch (error) {
      console.error('Erro ao fazer login:', error);
      alert('Erro ao fazer login. Por favor, tente novamente mais tarde.');
    }
  }




