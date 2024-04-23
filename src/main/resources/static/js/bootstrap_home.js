function showRegisterForm() {
    document.querySelector('.container-form').classList.add('hidden');
    document.querySelectorAll('.container-form')[1].classList.remove('hidden');
}

function showLoginForm() {
    document.querySelectorAll('.container-form')[1].classList.add('hidden');
    document.querySelector('.container-form').classList.remove('hidden');
}