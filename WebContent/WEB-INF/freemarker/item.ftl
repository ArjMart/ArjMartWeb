<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>${item.name}</h1>
	<#if item.thumbnail??>
		<img src="${item.thumbnail}" height="400px">
	</#if>
	<h2>Description: ${item.description}</h2>
	<#list attributes>
		<form action="${addToCardURL}" method="post">
			<input name="sku" type="hidden" value="${item.SKU}"/>
			<table>
				<tr>
					<th></th>
					<th>Color</th>
					<th>Size</th>
					<th>Price</th>
				</tr>
				<#items as attributeAndPrice>	
					<tr>
						<td><input type="radio" name="id" value="${attributeAndPrice.attribute.ID}" required /></td>
						<td>${attributeAndPrice.attribute.color}</td>
						<td>${attributeAndPrice.attribute.size}</td>
						<td>${attributeAndPrice.price?string.currency}</td>
					</tr>
				</#items>
			</table>
			<input name="quantity" type="number" placeholder="Quantity" min="1" required />
			<input type="submit" value="Go!"/>
		</form>
	<#else>
		<h2>Error! No attributes found. Please contact us if the issue persists</h2>
	</#list>
</body>
</html>