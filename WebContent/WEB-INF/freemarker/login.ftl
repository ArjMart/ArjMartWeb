<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>Login to ArjMart!</h1>
	<form action="${loginURL}?redirect=${redirect}" method="post">
		<table>
			<#if errorMessage??>
				<tr>
					<td colspan="2">
						<div class="errorbox">${errorMessage}</div>
					</td>
				</tr>
			</#if>
			<tr>
				<td><label for="email">Email:</label></td>
				<td><input id="email" name="email" type="email" required/></td>
			</tr>
			<tr>
				<td><label for="password">Password:</label></td>
				<td><input id="password" name="password" type="password" required/></td>
			</tr>
		</table>
		<input type="submit" value="Login"/>
	</form>
	No account, no problem!
	<a href="${signupURL}?redirect=${redirect}" method="post">Sign up!</a>
</body>
</html>