function logout() {
    // Limpar o sessionStorage
    sessionStorage.clear();
  
    // Redirecionar para a página de login
    window.location.href = 'login.html'; 
  }