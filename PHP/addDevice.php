<?php
    include('conn.php');
    $deviceNum = ($_POST['deviceNum']);
    $username = ($_POST['username']);
    $deviceSNum = ($_POST['deviceSNum']);
    // $deviceType;
    //依据序号选择添加的设备
    if($deviceNum == 1){
        $deviceType = "TempDevice";
    } elseif($deviceNum == 2){
        $deviceType = "LightDevice";

    } elseif($deviceNum == 3){
        $deviceType = "AlarmDevice";
    } elseif($deviceNum == 4){
        $deviceType = "SocketDevice";
    } else {
        $response['status'] = 0;
        echo(json_encode($response));
    }
    $sql = "select * from '$deviceType' where owner = '$username' and deviceSNum = '$deviceSNum';";
    $query = mysqli_query($conn,$sql);
    $result = mysqli_fetch_array($query);
    // 如果设备已经存在在用户中
    if(is_array($result)){
        $response['status'] = 0;
    } else {
	//插入数据
        $sql = "insert into $deviceType (owner,deviceSNum) values ('$username','$deviceSNum');";
$response['test'] = $sql;
        if(mysqli_query($conn,$sql)){
	    $response['init'] = 1;
   	    // 设备初始化
	    switch($deviceNum){
                case 1:
                    include('initTempDevice.php');
                    break;
                case 2:
                    include('initLightDevice.php');
                    break;
                case 3:
                    include('initAlarmDevice.php');
                    break;
                case 4:
                    include('initSocketDevice.php');
                    break;
                default:
                    $response['status'] = -1;
            }
        } else {
	    $response['init'] = 0;
	}
    }
    echo(json_encode($response));
    // 释放结果集
    mysqli_free_result($result);
    // 关闭数据库
    mysqli_close($conn);

?>
