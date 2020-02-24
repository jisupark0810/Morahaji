<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
#myword_list {
	font-size: 30px;
	font-weight: bold;
	line-height: 2em;
}

.fa-2x {
	font-size: 1.5em;
}

.setting {
	color: #ADB5BD;
	vertical-align: middle;
	cursor: pointer;
}

.setting:hover {
	color: #343A40;
}

.settingmenu {
	background: rgba(241, 243, 244, 0.8);
	float: right;
}

.settingbar_ {
   width: 70px;
   position: absolute;
   bottom: -50px;
   right: -65px;
}

.menuBox {
	position: absolute;
	width: 30px;
	height: 30px;
	right: 15px;
	top: 10px;
	display: inline-block
}
#register_hiddenbtn{
border-radius:.25rem;
color: #fff !important;
text-transform: uppercase;
text-decoration: none;
background: #60a3bc;
padding: 20px;
border-radius: 50px;
display: inline-block;
border: none;
transition: all 0.4s ease 0s;
	position: relative;
	cursor: pointer;
	top:50%; left:28%;
	margin-bottom:2em;
	font-weight: bold;
	}
#register_hiddenbtn:hover {
	text-shadow: 0px 0px 6px rgba(255, 255, 255, 1);
-webkit-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
-moz-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
transition: all 0.4s ease 0s;
font-weight: bold;
}


	
</style>


</head>
<body>

	<div class="row">
		<div class="col">
			<!-- <span id="myword_list">나의단어</span> -->
		</div>
	</div>

	<div id="wordboxAppend"></div>
	
	<div class="row">
		<div class="col">
			<ul class="pagination pagination-sm" id="pempty"></ul>
			
		</div>
	</div>
<script src="users.info/myword.js"></script>
</body>

