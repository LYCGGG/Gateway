<?php
	$dbhost = "localhost:3306";
	$dbuser = "lyc";
	$dbpass = "100418";
	$dbname = "gateway";
	$conn = mysqli_connect($dbhost,$dbuser,$dbpass);
	if(!$conn){
		die("can't connect".mysql_error());
	}
	mysqli_select_db($conn,'gateway');
?>

