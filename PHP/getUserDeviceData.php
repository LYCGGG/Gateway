<?php
    include('conn.php');
    $username = ($_POST['username']);
    $sql = "select * from TempDevice where owner = '$username';";
    $result = mysqli_query($conn, $sql);
    if(mysqli_num_rows($result) > 0) {
        // 输出数据
        while($row = mysqli_fetch_assoc($result)) {
	//$row是数组不能直接输出
            echo json_encode($row);
        }
    }
    mysqli_close($conn);
?>
