<?php
    include('conn.php');
    $sql = "create table AlarmDevice (deviceSNum varchar(255),havePassed bool ,switch bool, owner varchar(255));";
    if(mysqli_query($conn, $sql)){
        echo "true";
    }
    else {
        echo "false";
    }
    
    mysqli_close($conn);
?>
