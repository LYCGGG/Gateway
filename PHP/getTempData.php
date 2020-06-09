<?php
    $sql = "select deviceSNum,temperature,humidity from TempDevice where owner = '$username';";
    $query_result = mysqli_query($conn,$sql);
    $result_array = array();
    while($rows = mysqli_fetch_array($query_result,MYSQL_ASSOC)){
        array_push($result_array,$rows);
    }
    $resultTempData = new stdclass();
    foreach($result_array as $key => $value){
        $resultTempData -> $key = $value;
    }
    
?>
