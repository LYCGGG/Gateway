<?php
    include('conn.php');
    $sql = "create table TempDevice (deviceSNum varchar(255),temperature int,humidity int, owner varchar(255));";
    if(mysqli_query($conn, $sql)){
        echo "true";
    }
    else {
        echo "false";
    }
    
    mysqli_close($conn);
?>
