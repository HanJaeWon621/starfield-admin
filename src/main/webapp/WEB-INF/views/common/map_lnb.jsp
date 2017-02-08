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

  </script>

</head>

<script>
	function hello(){
		alert("hello")
	}
</script>

<nav class="nav-sidebar">
     <ul class="nav">
         <li><a href="/map/bcns">지점관리</a></li>
         <li class="nav-divider"></li>
         <li><a href="/map/floors">층관리</a></li>
         <li><a href="/styleSets">스타일관리</a></li>
         <li class="nav-divider"></li>
         <li><a href="/map/floorblocks">층별 블록(존) 관리</a></li>
         <li><a href="/map/tenantpois">POI정보 관리</a></li>
         <li class="nav-divider"></li>
         <li><a href="/map/distmng">배포관리</a></li>
         
     </ul>
 </nav>
