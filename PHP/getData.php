<?php
    include('conn.php');
    $username = ($_POST['username']);
//    $username = 'test';
    include('getTempData.php');
    // json_encode($tempData);
    include('getAlarmData.php');
    // json_encode($alarmData);
    include('getLightData.php');
    // json_encode($lightData);
    include('getSocketData.php');
    // json_encode($socketData);
    $result = array(
        "temp" => ($resultTempData),
        "alarm" => ($resultAlarmData),
        "light" => ($resultLightData),
        "socket" => ($resultSocketData)
    );
    echo json_encode($result);
?>
