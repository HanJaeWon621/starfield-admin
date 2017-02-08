<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Menu - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#menu" ).menu();
  } );
  </script>
  <style>
  .ui-menu { width: 150px; }
  </style>
</head>
<!--   
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li class="active"><a href="/map/bcns">Home</a></li>
      <li onclick="hello()"><a href="/map/bcns">지점관리</a></li>
      <li><a href="/map/floors">층관리</a></li>
      <li><a href="/map/floorblocks">층별블록관리</a></li>
      <li><a href="/map/tenantpois">INFO</a></li>
      <li><a href="/styleSets">스타일관리</a></li>
      <li><a href="/pgMngs">시스템관리</a></li>
    </ul>
  </div>
</nav>
 -->
<ul id="menu">
  <li><div>맵관리</div>
    <ul>
      <li><div><a href="/map/bcns">지점관리</a></div></li>
      <li><div><a href="/map/floors">층관리</a></div></li>
      <li><div><a href="/map/floorblocks">층별블록관리</a></li>
      <li><div><a href="/map/tenantpois">INFO</a></div></li>
    </ul>
  </li>
  <li><div><a href="/styleSets">스타일관리</a></div></li>
  <li><div>시스템관리</div>
    <ul>
      <li><a href="/pgMngs">프로그램관리</a></li>
      <li><div>Utilities</div></li>
    </ul>
  </li>
  <li><div>3depth</div>
    <ul>
      <li><div>Rock</div>
        <ul>
          <li><div>Alternative</div></li>
          <li><div>Classic</div></li>
        </ul>
      </li>
      <li><div>Jazz</div>
        <ul>
          <li><div>Freejazz</div></li>
          <li><div>Big Band</div></li>
          <li><div>Modern</div></li>
        </ul>
      </li>
      <li><div>Pop</div></li>
    </ul>
  </li>
  <li class="ui-state-disabled"><div>Specials (n/a)</div></li>
</ul>
<script>
	function hello(){
		alert("hello")
	}
</script>
