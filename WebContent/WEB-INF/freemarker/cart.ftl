<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>My Cart</h1>
	<#list cart>
		<table>
			<tr>
				<th>Name</th>
				<th>Size</th>
				<th>Color</th>
				<th>Unit Price</th>
				<th>Quantity</th>
				<th>Total Price</th>
			</tr>
			<#assign subtotal = 0>
			<#items as item>
				<tr>
					<td><a href="${itemURL}/${item.SKU}">${item.itemName}</a></td>
					<td>${item.itemAttributeSize}</td>
					<td>${item.itemAttributeColor}</td>
					<td>${item.price?string.currency}</td>
					<td>${item.quantity} (<a href="${editCartQuantityURL}?id=${item.orderLineID}">Edit</a>)</td>
					<td>${(item.quantity * item.price)?string.currency}</td>
					<#assign subtotal += item.quantity * item.price>
				</tr>
			</#items>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<th>Subtotal</th>
				<td>${subtotal?string.currency}</td>
		</table>
		<br>
		<center><a href="${checkoutURL}">Checkout!</a></center>
	<#else>
		<h2>
			Unfortunately, there is nothing in your cart. But you can still fix that! <br/>
			<a href="${searchURL}">Click here</a> to browse the catalouge and buy something cool!
		</h2>
	</#list>
</body>
</html>
