<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "include/header.ftl">
<body>
	<#include "include/navbar.ftl">
	<h1>Sign up for ArjMart!</h1>
	<script>
		function escapeRegExp(str) {
			return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
		}
	</script>
	<form action="${signupURL}?redirect=${redirect}" method="post">
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
				<td><input id="password" name="password" type="password" oninput="form.confirm.pattern = escapeRegExp(this.value)" required/></td>
			</tr>
			<tr>
				<td><label for="confirm">Confirm Password:</label></td>
				<td><input id="confirm" name="confirm" type="password" pattern="" title="Fields must match" required/></td>
			</tr>
			<tr>
				<td><label for="creditCardNumber">Credit Card Number:</label></td>
				<td><input id="creditCardNumber" name="creditCardNumber" type="text" required/></td>
			</tr>
		</table>
		<input type="submit" value="Sign up"/>
	</form>
	Already have an account?
	<a href="${loginURL}?redirect=${redirect}" method="post">Log in!</a>
</body>
</html>