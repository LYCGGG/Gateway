<?php
    $sql = "update TempDevice set temperature = 20, humidity = 50 where owner = '$username' and deviceSNum = '$deviceSNum';";
    if(mysqli_query($conn,$sql)){
        $response['status'] = 1;
    }
    else {
        $response['status'] = -2;
    }

?>
