<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>Order details</h1>
	<h2>Order ID: ${order.orderID}</h2>
	<h2>Order status: ${order.status}</h2>
	<h2>Total: ${total.total?string.currency}</h2>
	<#list orderLines>
		<table>
			<tr>
				<th>Name</th>
				<th>Size</th>
				<th>Color</th>
				<th>Unit Price</th>
				<th>Quantity</th>
				<th>Total Price</th>
				<th>Status</th>
			</tr>
			<#items as item>
				<tr>
					<td><a href="${itemURL}/${item.SKU}">${item.itemName}</a></td>
					<td>${item.itemAttributeSize}</td>
					<td>${item.itemAttributeColor}</td>
					<td>${item.price?string.currency}</td>
					<td>${item.quantity}</td>
					<td>${(item.quantity * item.price)?string.currency}</td>
					<td>${item.status}</th>
				</tr>
			</#items>
			<tr>
				<td colspan="4"></td>
				<th>Total</th>
				<td>${total.total?string.currency}</td>
			</tr>
		</table>
	<#else>
		<h2>
			This order was empty! Why would you do that?
		</h2>
	</#list>
</body>
</html>
