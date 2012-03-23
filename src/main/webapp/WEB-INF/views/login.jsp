<html>
<head>
<title>
Login page
</title>
</head>
<body>
<h1 style="text-align:center;font-size:20pt;">
Simple Login Page
</h1>
<div style="margin-left: 120px;">
	<form name="login" action="login" method="post" >
		Username :<input type="text" name="userName"/><br/>
		Password :<input type="password" name="password"/><br/>
		<input type="submit" onclick="check(this.form)" value="Login"/>
		<input type="reset" value="Cancel"/>
	</form>
</div>

<script language="javascript">
function check(form)/*function to check userid & password*/
{
	if(form.userName.value == "" || form.password.value == "")
	{
		alert('Cant be empty');
		return false;
	}
 
}
</script>
</body>
</html>