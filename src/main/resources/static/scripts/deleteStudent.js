var modal = document.getElementById("myPopUp");
var close = document.getElementById("close");
var btn = document.getElementById('deleteBtn')


btn.addEventListener('click', function(event) {
    modal.style.display = "block";
});

close.addEventListener('click', function(event){
    modal.style.display = "none";
});