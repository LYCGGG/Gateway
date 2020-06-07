<?php
    include('conn.php');
    $username = ($_POST['username']);
    $passwd = ($_POST['passwd']);
    //SQL,查询数据库有无当前用户
    $sql = "select * from users where '$username' = username;";
    $query = mysqli_query($conn, $sql);
    $result = mysqli_fetch_array($query);
	$response['username'] = $username;
    if(is_array($result)){
        if($result['passwd'] == $passwd){
            $response['status'] = 1; 
        }
        else {
            $response['status'] = 0;
        }
    }
    else {
        $response['status'] = -1;
    }
    echo(json_encode($response));
    //释放结果集
    mysqli_free_result($result);
    //关闭数据库
    mysqli_close($conn);
?>
