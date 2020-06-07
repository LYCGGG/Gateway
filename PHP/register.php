<?php
    include('conn.php');
    $username = ($_POST['username']);
    $passwd = ($_POST['passwd']);
    //SQL,查询有无用户已经注册
    $sql = "select * from users where '$username' = username;";
    $query = mysqli_query($conn, $sql);
    $result = mysqli_fetch_array($query);
    if(is_array($result)){
        $response['status'] = 0;
    }
    else {
        $sql = "insert into users(username,passwd) values ('$username','$passwd');";
        mysqli_query($conn,$sql);
        $response['status'] = 1;
    }
    echo(json_encode($response));
    //释放结果集
    mysqli_free_result($result);
    //关闭数据库
    mysqli_close($conn);
?>
