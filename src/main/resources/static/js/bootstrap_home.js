document.addEventListener('DOMContentLoaded', function () {
  var dropdownToggle = document.getElementById('filtroGeneroBtn');
  dropdownToggle.addEventListener('click', function () {
    var dropdownMenu = dropdownToggle.nextElementSibling;
    if (dropdownMenu.classList.contains('show')) {
      dropdownMenu.classList.remove('show');
    } else {
      dropdownMenu.classList.add('show');
    }
  });
});


let x = document.getElementById('login');
let y = document.getElementById('register');
let z = document.getElementById('btn');

function register() {
  x.style.left = '-400px';
  y.style.left = '50px';
  z.style.left = '110px';
}

function login() {
  x.style.left = '50px';
  y.style.left = '450px';
  z.style.left = '0px';
}