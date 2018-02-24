<?php

date_default_timezone_set("Asia/Ho_Chi_Minh");
class data_provider {
	var $servername = 'localhost';
	var $username = 'root';
	var $password = 'binhduongtnt';
	var $db_name = 'goshare';
	var $conn = null;
	// Create connection
	function connect() {
		$this->conn = new mysqli($this->servername, $this->username, $this->password, $this->db_name);

		mysqli_set_charset($this->conn, 'utf8');

		if ($this->conn->connect_error) {
			return 404;
		} 
		
		return 200;
	}

	//select a database to work with
	function insert_resource($lo, $la, $type, $link, $dc, $ds, $de, $note, $userid) {
		$query = "insert into `resource` values (NULL, '$lo', '$la', '$type', '$link', '$dc', '$ds', '$de', '$note', '$userid')";
		
		$this->conn->query($query);
		return 1;

		//$query = "select id from resource where link = '$link'";

		//$result = $this->conn->query($query);
		//$x = -1;
		//while($row =mysqli_fetch_array($result))
    	//{
    		//$x = $row["id"];
    	//}
		//return $x;
	}

	function addTags($id, $tags) {
		$query = "insert into `tags` values";
		for ($x = 0; $x < count($tags); $x++) {
			$query .= "('$id', '$tags[$x]', NULL)";

			if ($x < count($tags)-1)
				$query .= ",";
		}

		$this->conn->query($query);
	}



	function addUser($username, $password, $email) {
		$query = "insert into `user` values (NULL, '$username', '$password', '$email')";

		$this->conn->query($query);
	}

	function addFriend($user1, $user2) {
		$query = "insert into `friend` values ('$user1', '$user2')";

		echo $query;
		$this->conn->query($query);
	}

	function userAuthenticaton($username, $password) {
		$query = "select id from user where username = '$username' and password = '$password'";

		$result = $this->conn->query($query);
		$x = -1;
		while($row =mysqli_fetch_array($result))
    	{
    		$x = $row["id"];
    	}

		return $x;
	}

	function get_resources($lo, $la, $r) {
		$query = "select * from resource where (location_long-$lo)*(location_long-$lo) + (location_lat-$la)*(location_lat-$la) <= $r*$r";

		//echo $query;
		$result = $this->conn->query($query);
		//print_r($result);
		 $emparray = array();
    	while($row =mysqli_fetch_array($result))
    	{
        	$emparray[] = $row;

   		}
   		return $emparray;
	}


	function get_history($id) {
		$query = "select * from resource where user_id = '$id'";

		$result = $this->conn->query($query);
		
		 $emparray = array();
    	while($row =mysqli_fetch_array($result))
    	{
        	$emparray[] = $row;

   		}
   		return $emparray;
	}

	function searchTag($tag) {
		$query = "select rc . * from  `resource` rc join  `tags` t on rc.id = t.id_resource where t.tag like  '%$tag%'";

		$result = $this->conn->query($query);
		
		 $emparray = array();
    	while($row =mysqli_fetch_array($result))
    	{
        	$emparray[] = $row;

   		}
   		return $emparray;
	}


/*
	function get_resources($lo, $la, $r) {
		$query = "select id, link, note, type from resource where (location_long-$lo)*(location_long-$lo) + (location_lat-$la)*(location_lat-$la) <= $r*$r";

		//echo $query;
		$result = $this->conn->query($query);
		//print_r($result);
		 $emparray = array();
    	while($row =mysqli_fetch_array($result))
    	{
        	$emparray[] = Array('id' => $row['id'], 'link' => $row['link'], 'note' => $row['note'], 'type' => $row['type'], 'id' => $row['id'], 'id' => $row['id']);

   		}
   		return $emparray;
	}
	*/

	
}


?>



