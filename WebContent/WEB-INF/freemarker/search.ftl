<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<#if query?? && query != "">
		<h1>Search results for <code>${query}</code> (${resultCount} results)</h1>
	<#else>
		<h1>Browse the catalouge (${resultCount} results)</h1>
	</#if>
	<#list items>
		<table>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Thumbnail</th>
			</tr>
			<#items as item>
				<tr>
					<td><a href="${itemURL}/${item.SKU}">${item.name}</a></td>
					<td>${item.description}</td>
					<#if item.thumbnail??>
						<td><img src="${item.thumbnail}" height="100px"/></td>
					</#if>
				</tr>
			</#items>
			<tr>
				<td></td>
				<td>
					<center><table><tr>
						<#if page != 1>
							<td><a href="${searchURL}?page=1&limit=${limit}<#if query??>&query=${query}</#if>">First</a></td>
						</#if>
						<#if previousPage??>
							<td><a href="${searchURL}?page=${previousPage}&limit=${limit}<#if query??>&query=${query}</#if>">Previous</a></td>
						</#if>
						<td>${page}</td>
						<#if nextPage??>
							<td><a href="${searchURL}?page=${nextPage}&limit=${limit}<#if query??>&query=${query}</#if>">Next</a></td>
						</#if>
						<#if page != pageCount>
							<td><a href="${searchURL}?page=${pageCount}&limit=${limit}<#if query??>&query=${query}</#if>">Last</a></td>
						</#if>
					</tr></table></center>
				</td>
		</table>
	<#else>
		<h2>Sorry, there were no results. Please try again with a different search term.</h2>
	</#list>
</body>
</html>