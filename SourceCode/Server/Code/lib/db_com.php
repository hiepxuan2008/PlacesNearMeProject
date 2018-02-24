<?php

/**
 * @author HiepIT
 * @copyright 2015
 * @create 14:28, 20-6-2015
 * @lastupdate 14:28, 20-6-2015
 * @description Database Communication
 */ 

require_once 'medoo.php';
date_default_timezone_set("Asia/Ho_Chi_Minh");

class db_com {
    var $database = null;
    function __construct() {
        $this->database = new medoo();
    }

    function getNow() {
        return date('Y-m-d H:i:s');
    }

    function placeNearMeByProductName($lat, $lng, $search) {
        $search_quoted = $this->database->quote("%" . $search . "%");
        $query_string = "SELECT * FROM place WHERE products LIKE $search_quoted";
        $data = $this->database->query($query_string);
        $data = $data->fetchAll(PDO::FETCH_OBJ);
        return $data;
    }

    function createNewPlace($user_id, $keyword, $lat, $lng, $name, $address, $website, $phoneNumber, $internationalPhoneNumber, $description, $productData) {
        $data = $this->database->insert("place", [
           "creator" => $user_id,
            "lat" => $lat,
            "lng" => $lng,
            "name" => $name,
            "address" => $address,
            "website" => $website,
            "phone_number" => $phoneNumber,
            "inter_phone_number" => $internationalPhoneNumber,
            "description" => $description,
            "products" => $productData,
            "keyword" => $keyword
        ]);
        return $data;
    }

    function follow_place($user_id, $google_place_id, $type) {
        if (strlen($google_place_id) < 20)
            $place_id = $google_place_id;
        else
            $place_id = $this->googleplaceid2placeid($google_place_id);

        if ($place_id <= 0)
            return null;

        //Check exist follow place
        $follow_place_id = $this->check_exist_follow_place($user_id, $place_id);
        //echo $follow_place_id;
        if ($follow_place_id == null) {
            return $this->create_new_follow_place($user_id, $place_id, $type);
        }
        return $this->update_follow_place($follow_place_id, $type);
    }

    function create_new_follow_place($user_id, $place_id, $type) {
        $follow_place_id = $this->database->insert("follow_place", [
            "user_id" => $user_id,
            "place_id" => $place_id,
            "type" => $type,
            "created_at" => $this->getNow(),
            "modified_at" => $this->getNow()
        ]);
        return $follow_place_id;
    }

    function check_exist_follow_place($user_id, $place_id) {
        $data = $this->database->select("follow_place", "id", [
            "AND" => [
                "user_id" => $user_id,
                "place_id" => $place_id
            ]
        ]);
        if ($data != null)
            return $data[0];
        return null;
    }

    function update_follow_place($follow_place_id, $type) {
        $data = $this->database->update("follow_place", [
            "type" => $type
        ], [
            "id" => $follow_place_id
        ]);

        return 1;
    }

    function get_users_follow_place($google_place_id) {
        if (strlen($google_place_id) < 20)
            $place_id = $google_place_id;
        else
            $place_id = $this->googleplaceid2placeid($google_place_id);
        if ($place_id <= 0)
            return null;

        $data = $this->database->select("follow_place", "user_id", [
            "AND" => [
                "type" => 1,
                "place_id" => $place_id
            ]

        ]);
        return $data;
    }

    function is_following_place($user_id, $google_place_id) {
        if (strlen($google_place_id) < 20)
            $place_id = $google_place_id;
        else
            $place_id = $this->googleplaceid2placeid($google_place_id);

        if ($place_id <= 0)
            return null;

        $data = $this->database->select("follow_place", "type", [
            "AND" => [
                "user_id" => $user_id,
                "place_id" => $place_id
            ]
        ]);
        if ($data != null)
            return $data[0];
        
        return 0;
    }

	function nearby_search($lat, $lng, $keyword) {
        $like_keyword = $this->database->quote("%$keyword%");
		$data = $this->database->query("SELECT * FROM place WHERE google_place_id IS NULL AND keyword LIKE $like_keyword");
        //echo "SELECT * FROM place WHERE google_place_id IS NULL AND keyword LIKE $like_keyword";
        $data = $data->fetchAll(PDO::FETCH_OBJ);
		return $data;
	}

    function querynewfeeds($google_place_id) {
        if (strlen($google_place_id) < 20)
            $place_id = $google_place_id;
        else
            $place_id = $this->googleplaceid2placeid($google_place_id);
        if ($place_id <= 0)
            return new ArrayObject(); //null

        $data = $this->database->query("SELECT post.user_id, post.id as post_id, post.place_id, post.status, UNIX_TIMESTAMP(post.created_at) as created_at, UNIX_TIMESTAMP(post.modified_at) as modified_at, user.username, user.full_name, user.avatar FROM post JOIN user on post.user_id = user.id WHERE place_id = $place_id");
        $rows = $data->fetchAll(PDO::FETCH_OBJ);
        //var_dump($rows);
        for ($i = 0; $i < count($rows); $i++) {
            //photos in this post
            $photos = $this->database->query("SELECT url, UNIX_TIMESTAMP(created_at) as created_at FROM photo WHERE post_id = {$rows[$i]->post_id}");
            $photos = $photos->fetchAll(PDO::FETCH_OBJ);
            $rows[$i]->photos = $photos;

            //like in this post
            $likes = $this->database->query("SELECT user.full_name, user.username, user.id as user_id FROM like_post JOIN user ON like_post.user_id = user.id WHERE like_post.type = 1 AND like_post.post_id = {$rows[$i]->post_id}");
            $likes = $likes->fetchAll(PDO::FETCH_OBJ);
            $rows[$i]->likes = $likes;
        }
        return $rows;
    }

    //$user_ids is array of user_id
    function getdevicetokens($user_ids) {
        $data = $this->database->select("device_token", "token", [
            "user_id" => $user_ids //user_id in []
        ]);
        return $data;
    }

    function like_post($user_id, $post_id, $type) {
        //Check exist like
        $like_id = $this->check_exist_like_post($user_id, $post_id);
        if ($like_id == null) {
            return $this->create_new_like_post($user_id, $post_id, $type);
        }
        return $this->update_like_post($like_id, $type);
    }

    function create_new_like_post($user_id, $post_id, $type) {
        $like_post_id = $this->database->insert("like_post", [
            "user_id" => $user_id,
            "post_id" => $post_id,
            "type" => $type,
            "created_at" => $this->getNow(),
            "modified_at" => $this->getNow()
        ]);
        return $like_post_id;
    }

    function check_exist_like_post($user_id, $post_id) {
        $data = $this->database->select("like_post", "id", [
            "AND" => [
                "user_id" => $user_id,
                "post_id" => $post_id
            ]
        ]);
        if ($data != null)
            return $data[0];
        return null;
    }

    function update_like_post($like_id, $type) {
        $data = $this->database->update("like_post", [
            "type" => $type
        ], [
            "id" => $like_id
        ]);
        return $data;
    }

	function create_newfeed($user_id, $google_place_id, $status, $photos) {
        if (strlen($google_place_id) < 20)
            $place_id = $google_place_id;
        else
            $place_id = $this->googleplaceid2placeid($google_place_id);
        if ($place_id <= 0)
            return null;

        $last_id = $this->database->insert("post", [
            "user_id" => $user_id,
            "place_id" => $place_id,
            "status" => $status,
            "created_at" => $this->getNow(),
            "modified_at" => $this->getNow()
        ]);
        if ($photos)
        {
            foreach ($photos as $k => $v) {
                if (strlen($v) < 10)
                    continue;
                
                $this->database->insert("photo", [
                   "post_id" => $last_id,
                    "url" => $v,
                    "created_at" => $this->getNow(),
                    "modified_at" => $this->getNow()
                ]);
            }
        }

        return $last_id;
    }

    // Mapping google place id to place id
    function googleplaceid2placeid($google_place_id) {
        $param = $this->database->quote($google_place_id);
        $data = $this->database->select("place", "id", [
            "google_place_id" => $google_place_id
        ]);
        if (empty($data)) {
            return $this->create_newplaceid($google_place_id);
        }
        return $data[0];
    }

    // Create a new place_id for mapping google_place_id and place_id
    function create_newplaceid($google_place_id) {
        $data = $this->database->insert("place", [
           "google_place_id" => $google_place_id
        ]);
        return $data;
    }

    function insert_resource($lo, $la, $type, $link, $ds, $de, $note, $userid) {
    	//$query = "insert into `resource` values (NULL, '$lo', '$la', '$type', '$link', '$dc', '$ds', '$de', '$note', '$userid')";
		$last_resource_id = $this->database->insert("resource", [
			"location_long" => $lo,
			"location_lat" => $la,
			"type" => $type,
			"link" => $link,
			"date_creation" => date('Y-m-d H:i:s'),
			"date_start" => $ds,
			"date_end" => $de,
			"note" => $note,
			"user_id" => $userid
			]);
    	return $last_resource_id;
    }
	
	function insert_audio_detail($id, $title, $artist)
	{
		$res = $this->database->insert("audio_detail", [
			"audio_id" => $id,
			"title" => $title, 
			"artist" => $artist
		]);
		return $res;
	}

    function addTags($id, $tags) {
    }

    function addUser($username, $password, $email) {
    	//$query = "insert into `user` values (NULL, '$username', '$password', '$email')";
    	$last_user_id = $this->database->insert("user", [
    		"username" => $username,
    		"password" => $password,
    		"email" => $email
    	]);
    	//var_dump($this->database->error());

    	return $last_user_id;
    }

    function addFriend($user1, $user2) {
    }

    function userAuthenticaton($username, $password) {
        $username = $this->database->quote($username);
        $password = $this->database->quote($password);
    	$data = $this->database->query("SELECT id as user_id, avatar FROM user WHERE username=$username AND password=$password");
        $data = $data->fetchObject();
        
        return $data;
    }

    function get_resources($lo, $la, $r) {
    	//$query = "select * from resource where (location_long-$lo)*(location_long-$lo) + (location_lat-$la)*(location_lat-$la) <= $r*$r";

    	$datas = $this->database->select("resource", [
			"[>]audio_detail" => ["id" => "audio_id"],
			"[>]user" => ["user_id" => "id"]
		], [
			"resource.id(id)",
			"location_lat",
			"location_long",
			"note",
			"type",
			"link",
			"title",
			"artist",
			"date_creation",
			"full_name",
		]);
		//var_dump($datas);
		
    	return $datas;
    }

    function get_history($id) {
    }

    function searchTag($tag) {
    }
}
?>

<?php

/*

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
*/
?>
