<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<form action="${editCartQuantityURL}" method="POST">
		Change quantity of <a href="${itemURL}/${orderLine.SKU}">${orderLine.itemName}</a> (Size: ${orderLine.itemAttributeSize}, Color: ${orderLine.itemAttributeColor}): <br>
		<input type="number" name="quantity" value="${orderLine.quantity}" min="1"/>
		<input type="hidden" name="id" value="${orderLine.orderLineID}"/>
		<input type="submit" value="Save"/>
	</form>
</body>
</html>