<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>My past orders</h1>
	<#list orders>
		<table>
			<tr>
				<th>Order</th>
				<th>Status</th>
			</tr>
			<#assign index = 1>
			<#items as order>
				<tr>
					<td><a href="${orderDetailsURL}/${order.orderID}">Order ${index}</a></td>
					<#assign index += 1>
					<td>${order.status}</td>
				</tr>
			</#items>
		</table>
	<#else>
		<h2>
			Unfortunately, you have no orders. But you can still fix that! <br/>
			<a href="${searchURL}">Click here</a> to browse the catalouge and buy something cool!
		</h2>
	</#list>
</body>
</html>
