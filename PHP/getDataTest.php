<?php
    include('conn.php');
    // $username = ($_POST['username']);
    $username = 'test';
    $sql = "select * from TempDevice where owner = '$username';";
    $result = mysqli_query($conn, $sql);
	$count = 1;
    if(mysqli_num_rows($result) > 0) {
        // 输出数据
        while($row = mysqli_fetch_assoc($result)) {
		
            $array_result[$count] = array($row);
	$count += 1;
            // echo $row;
        }
	echo (json_encode($array_result));
    }
//    echo json_encode($array_result);
    // echo (json_encode(mysqli_fetch_all($result)));
    mysqli_close($conn);
?>
