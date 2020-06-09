<?php
    $sql = "update SocketDevice set switch = 0, data = 0 where owner = '$username' and deviceSNum = '$deviceSNum';";
    if(mysqli_query($conn,$sql)){
        $response['status'] = 1;
    }
    else {
        $response['status'] = -2;
    }

?>