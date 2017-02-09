<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li class="active"><a href="/map/bcns">Home</a></li>
      <li onclick="cal_submenu()"><a href="/map/bcns">지점관리</a></li>
      <li onclick="cal_submenu()"><a href="/map/bcns">지도관리</a></li>
      <li onclick="cal_submenu()"><a href="/map/distmng">배포관리</a></li>
      <li onclick="cal_submenu()"><a href="/pgMngs">프로그램관리</a></li>
      <li onclick="cal_submenu()"><a href="/todoMngs">TO_DO관리</a></li>
    </ul>
  </div>
</nav>
<script>
	function cal_submenu(){
		console("hello")
	}
</script>
<FORM name = "form">
</FORM>
