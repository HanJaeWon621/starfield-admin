<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script type="text/javascript">
		var message = '${message}';
		var returnUrl = '${returnUrl}';
		alert(message);
		returnUrl = !returnUrl ? '/' : returnUrl; 
		document.location.href = returnUrl;
	</script>
