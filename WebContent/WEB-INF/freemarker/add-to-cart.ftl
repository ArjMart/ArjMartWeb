<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>Added ${orderLine.quantity} ${item.name}<#if orderLine.quantity gt 1>s</#if> to cart</h1>
	<h3>
		Color: ${itemAttribute.color} <br/>
		Size: ${itemAttribute.size}
	</h3>
	<h1><a href="${cartURL}">View cart</a></h1>
</body>
</html>