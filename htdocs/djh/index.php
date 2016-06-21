<?
	define("HOST","127.0.0.1");
	define("USER","root");
	define("PASS","hedge816");

	$getData = json_decode(file_get_contents('php://input'), true); 
	$func = $getData['func'];

	if(trim($func) == 'register_event')
	{
		register_event($getData);
	}

	if(trim($func) == 'cancel_event')
	{
		cancel_event($getData);
	}

	if(trim($func) == 'like_event')
	{
		like_event($getData);
	}

	if(trim($func) == 'cancel_like')
	{
		cancel_like($getData);
	}

	if(trim($func) == 'get_like_num')
	{
		get_like_num($getData);
	}	




	function register_event($dataset)
	{
		$cultcode = create_cult_code();
		$newcode = $cultcode["newcode"];
		$attrs = array('cultcode' => $newcode,
						'userid' => $dataset['userid'],
						'title' => $dataset['title'],
						'start_date' => $dataset['start_date'],
						'end_date' => $dataset['end_date'],
						'time' => $dataset['time'],
						'place' => $dataset['place'],
						'org_link' => $dataset['org_link'],
						'main_img' => $dataset['main_img'],
						'use_fee' => $dataset['use_fee'],
						'inquiry' => $dataset['inquiry'],
						'contents' => $dataset['contents']);

		$result = do_insert("event", $attrs);
		echo json_encode($result);
	}

	function like_event($dataset)
	{
		$attrs = array('cultcode' => $dataset['cultcode'],
						'userid' => $dataset['userid'],
						'age' => $dataset['age']);

		$result = do_insert("liketable", $attrs);
		echo json_encode($result);
	}

	function get_like_num($dataset)
	{
		$cultcode = $dataset['cultcode'];
		$db = getDB();
		$cmd = "select age, COUNT(*) as num from liketable where cultcode=".$cultcode." group by cultcode, age;";
		$result = mysql_query($cmd);
		$response = array();

		$success = false;
		while( $row = @mysql_fetch_array($result) )
		{
			$response[$row["age"]] = $row["num"];
			$success = true;
		}
		$response["result"] = $success;
		echo json_encode($response);
	}




	function getDB()
	{
		$connect = mysql_connect(HOST,USER,PASS);
		$db = mysql_select_db("djh", $connect);
		return $db;
	}

	function do_insert($tablename, $arr)
	{
		$db = getDB();
		$cmd = "insert into ".$tablename." values(";
		$cnt = 0;		
		foreach ($arr as $key => $value) {
			$temp = $value;
			if($temp != NULL){
				$cmd.="'";
				$cmd.= $temp;
				$cmd.="'";	
			} 
			else
			{
				$cmd.="null";
			}
			if($cnt < sizeof($arr) - 1)
			{
				$cmd.=",";
			}
			$cnt ++;
		}
		$cmd.=");";

		//echo $cmd;
		$result = mysql_query($cmd);
		return array('result' => $result);
	}

	function create_cult_code()
	{
		$db = getDB();
		$cmd = "select cultcode from event where cultcode >= 1000000 order by cultcode desc limit 1;";
		$result = mysql_query($cmd);
		if(@mysql_num_rows($result) <= 0)
		{
			$newcode = 1000000;
		}
		else
		{
			$entity = mysql_fetch_array($result);
			$newcode = $entity["cultcode"] + 1;
		}
		//echo $cmd;
		return array('result'=>$result,
					'newcode'=>$newcode);
	}

?>