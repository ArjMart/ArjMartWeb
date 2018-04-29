<table class="header">
	<tr>
		<td>
			<a href="${homeURL}" style="color:white; text-decoration: none">
				<h1 class="headertitle">ArjMart</h1>
			</a>
			<form action="${searchURL}" class="headersearch">
				<input name="query" type="text" placeholder="Search..." />
			</form>
		</td>
		<td>
			<#if session??>
				<span class="login">
					<#if session.user??>
						<a href="${accountURL}" style="color: white; text-decoration: none"> Welcome, ${session.user.email?keep_before("@")}!</a>
						<a href="${cartURL}" style="color: white; padding-left: 20px; text-decoration: none">My Cart</a>
						<a href="${logoutURL}" style="color: white; padding-left: 20px; text-decoration: none">Log Out</a>
					<#else>
						<a href="${loginURL}" style="color: white; text-decoration: none">Log in</a>
						<a href="${signupURL}" style="color: white; padding-left: 20px; text-decoration: none">Sign up</a>
					</#if>
				</span>
			</#if>
		</td>
	</tr>
</table>