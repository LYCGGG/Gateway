<?php
    $sql = "update LightDevice set switch = 0, havePassed = 0 where owner = '$username' and deviceSNum = '$deviceSNum';";
    if(mysqli_query($conn,$sql)){
        $response['status'] = 1;
    }
    else {
        $response['status'] = -2;
    }

?>