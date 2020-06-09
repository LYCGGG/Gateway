<?php
    include('conn.php');
    // $username = ($_POST['username']);
    $username = 'test';
    $sql = "select * from TempDevice where owner = '$username';";
    $result = mysqli_query($conn, $sql);
        $count = 1;
    $jarr = array();
    while($rows = mysqli_fetch($result)){
        $count = count($rows);
	var_dump($rows);
        for($i=0;$i<$count;$i++){
		unset($rows[$i]);
	}
echo "============\n";
	var_dump($rows);
	array_push($jarr,$rows);
    }
	var_dump($jarr);
    // print_r($jarr);
    $jobj = new stdclass();
    foreach($jarr as $key => $value){
        $jobj->$key = $value;
    }
    echo (json_encode($jobj));
    // if(mysqli_num_rows($result) > 0) {
    //     // 输出数据
    //     while($row = mysqli_fetch_assoc($result)) {

    //         $array_result[$count] = array($row);
    //     $count += 1;
    //         // echo $row;
    //     }
    //     echo (json_encode($array_result));
    // }
//    echo json_encode($array_result);
    // echo (json_encode(mysqli_fetch_all($result)));
    mysqli_close($conn);
    // 输出的结果为
    // [[{"test":"test",...}],[]]
    // 这玩意不好解析啊
    // 将其变为{"key":"{value}",...}
echo "\n";
?>
