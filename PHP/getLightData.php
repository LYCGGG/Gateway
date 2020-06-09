<?php
    $sql = "select * from LightDevice where owner = '$username';";
    $query_result = mysqli_query($conn,$sql);
    $result_array = array();
    while($rows = mysqli_fetch_array($query_result,MYSQL_ASSOC)){
        array_push($result_array,$rows);
    }
    $resultLightData = new stdclass();
    foreach($result_array as $key => $value){
        $resultLightData -> $key = $value;
    }
    
?>
