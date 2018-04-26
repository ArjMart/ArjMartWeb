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
			<#items as item>
				<tr>
					<td><a href="${itemURL}/${item.SKU}">${item.itemName}</a></td>
					<td>${item.itemAttributeSize}</td>
					<td>${item.itemAttributeColor}</td>
					<td>${item.price?string.currency}</td>
					<td>${item.quantity} (<a href="${editCartQuantityURL}?id=${item.orderLineID}">Edit</a>)</td>
					<td>${(item.quantity * item.price)?string.currency}</td>
					<td><a href="${removeFromCartURL}?id=${item.orderLineID}">Remove</a></td>
				</tr>
			</#items>
			<tr>
				<td colspan="4"></td>
				<th>Subtotal</th>
				<td>${total.total?string.currency}</td>
			</tr>
			<tr>
				<td colspan="4"></td>
				<td><a href="${checkoutURL}">Checkout!</a></td>
			</tr>
		</table>
	<#else>
		<h2>
			Unfortunately, there is nothing in your cart. But you can still fix that! <br/>
			<a href="${searchURL}">Click here</a> to browse the catalouge and buy something cool!
		</h2>
	</#list>
</body>
</html>
