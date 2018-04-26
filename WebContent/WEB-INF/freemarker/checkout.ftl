<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>Are you sure you wish to proceed?</h1>
	<h2>${total.total?string.currency} will be charged to the credit card ${creditCard}</h2>
	<h3>To change your credit card number, head to your account page</h3>
	<h2><a href="${handleCheckoutURL}">Proceed!</a></h2>
</body>
</html>
