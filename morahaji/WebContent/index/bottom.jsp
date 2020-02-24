<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
.container1{
margin-bottom:0px;
}

#myBtn {
  display: none; 
  position: fixed; 
  bottom: 20px; 
  right: 30px; 
  z-index: 99; 
  border: none;
  outline: none; 
  background-color: #8080FF;
  color: white; 
  cursor: pointer; 
  padding: 15px;
  border-radius: 10px;
  font-size: 18px; 
}

#myBtn:hover {
  background-color: #555; 
}
</style>
   
<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>
<footer class="py-4 bg-dark"> <!-- py는 세로폭 클수록 두꺼움 -->
   <div class="container container1">
     <p class="m-0 text-center text-white">Copyright &copy; 2019 MORAHAJI All rights reserved.</p>
   </div>
</footer>
<script>
mybutton = document.getElementById("myBtn");

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
</script>