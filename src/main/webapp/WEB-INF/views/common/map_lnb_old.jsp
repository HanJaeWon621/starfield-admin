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
<ul id="menu">
  <li><div><a href="/map/bcns">지점관리</a></div></li>
  <li class="ui-menu-item"><div id="ui-id-4" tabindex="-1" role="menuitem" class="ui-menu-item-wrapper"><strong>▼ 기본 설정</strong></div></li>
  <li><div><a href="/map/floors">층관리</a></div></li>
  <li><div><a href="/styleSets">스타일 관리</a></div></li>
  <li class="ui-menu-item"><div id="ui-id-4" tabindex="-1" role="menuitem" class="ui-menu-item-wrapper"><strong>▼ 지도 관리</strong></div></li>
  <li><div><a href="/map/floorblocks">층별 블록(존) 관리</a></li>
  <li><div><a href="/map/tenantpois">POI정보 관리</a></div></li>
  <li class="ui-menu-item"><div id="ui-id-4" tabindex="-1" role="menuitem" class="ui-menu-item-wrapper"><strong>▼ 배포관리</strong></div></li>
  <li><a href="/map/distmng">배포관리</a></li>
  <li class="ui-menu-item"><div id="ui-id-4" tabindex="-1" role="menuitem" class="ui-menu-item-wrapper"><strong>▼ 기타</strong></div></li>
  <li><a href="/pgMngs">프로그램관리</a></li>
  
  
  <!-- 
  
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
   -->
</ul>
<script>
	function hello(){
		alert("hello")
	}
</script>
