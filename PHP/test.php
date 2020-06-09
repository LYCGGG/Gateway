<?php
	$username = $_POST["username"];
	$passwd = $_POST["passwd"];
	$return["username"] = $username;
	$return["passwd"] = $passwd;
	if($username=="123" && $passwd=="123"){
		$return["passwd"] = $passwd;
	}
	else {
		$return["status"] = 0;
	}
	echo(json_encode($return));
?>
