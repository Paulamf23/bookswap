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