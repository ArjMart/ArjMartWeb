<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>My Account</h1>
	<h3><a href="${pastOrdersURL}">See my past orders</a></h3>
	Email: ${user.email}<br/>
	Unique Login Code: ${user.UUID?c}<br/>
	<form action="${accountURL}" method="POST">
		Edit my credit card number: <input name="creditCardNumber" type="number" value="${user.creditCardNumber}"/>
		<input type="submit" value="Save"/>
	</form>
</body>
</html>