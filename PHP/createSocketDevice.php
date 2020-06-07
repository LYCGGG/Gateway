<?php
    include('conn.php');
    $sql = "create table SocketDevice (deviceSNum varchar(255),data int ,switch bool, owner varchar(255));";
    if(mysqli_query($conn, $sql)){
        echo "true";
    }
    else {
        echo "false";
    }
    
    mysqli_close($conn);
?>