<?php

/**
 * @author HiepIT
 * @copyright 2015
 * @created_at 21:33, 19-6-2015
 * @modified_at 18:00, 29-04-2016
 */

define(ERROR_MESSAGE, "error_message");
define(ERROR, "error");

$response = array("error" => 1);

if (empty($_POST["cid"])) {
    $response["error_message"] = "Your command is invalid!";
    die(json_encode($response));
}

require_once("lib/db_com.php");
require_once("lib/json_beautiful.php");
$db_com = new db_com();
$cid = $_POST["cid"];
$response["cid"] = $cid;

switch ($cid) {
    case "nearbysearch": {
        nearby_search();
        break;
    }
    case "querynewfeeds": {
        querynewfeeds();
        break;
    }
    case "createnewfeed": {
        create_newfeed();
        break;
    }
    case "googleplaceid2placeid": {
        googleplaceid2placeid();
        break;
    }
    case "create_new_place": {
        create_new_place();
        break;
    }
    case "likepost": {
        like_post();
        break;
    }
    case "notify": {
        notify();
        break;
    }
    case "login": {
        login();
        break;
    }
    case "follow_place": {
        follow_place();
        break;
    }
    case "unfollow_place": {
        unfollow_place();
        break;
    }
    case "is_following_place": {
        is_following_place();
        break;
    }
    default:
        break;
}
echo indent(json_encode($response));

function create_new_place() {
    global $response, $db_com;
    if (check_miss_required_params(["user_id", "lat", "lng", "keyword"]) == 1) return;

    $user_id = $_POST["user_id"];
    $lat = $_POST["lat"];
    $lng = $_POST["lng"];
    $name = $_POST["name"];
    $address = $_POST["address"];
    $website = $_POST["website"];
    $phoneNumber = $_POST["phoneNumber"];
    $internationalPhoneNumber = $_POST["internationalPhoneNumber"];
    $description = $_POST["description"];
    $productData = $_POST["productData"];
    $keyword = $_POST["keyword"];

    $res = $db_com->createNewPlace($user_id, $keyword, $lat, $lng, $name, $address, $website, $phoneNumber, $internationalPhoneNumber, $description, $productData);
    if ($res == null) {
        $response["error"] = 1;
        $response["error_message"] = "Create a new place failed!";
    } else {
        $response["error"] = 0;
    }
}

function follow_place() {
    global $response, $db_com;
    if (check_miss_required_params(["user_id", "place_id"]) == 1) return;

    $user_id = $_POST["user_id"];
    $place_id = $_POST["place_id"];
    $res = $db_com->follow_place($user_id, $place_id, 1);
    if ($res == null) {
        $response["error"] = 1;
        $response["error_message"] = "Follow place failed!";
    } else {
        $response["error"] = 0;
    }
}

function unfollow_place() {
    global $response, $db_com;
    if (check_miss_required_params(["user_id", "place_id"]) == 1) return;

    $user_id = $_POST["user_id"];
    $place_id = $_POST["place_id"];
    $res = $db_com->follow_place($user_id, $place_id, 0);
    if ($res == null) {
        $response["error"] = 1;
        $response["error_message"] = "Unfollow place failed!";
    } else {
        $response["error"] = 0;
    }
}

function notify() {
    global $response, $db_com;

    $title = $_POST["title"];
    $message = $_POST["message"];
    $user_ids = [1, 2];
    $tokens = $db_com->getdevicetokens($user_ids);
    if ($tokens != null) {
        sendFCMMessage($title, $message, $tokens);
    }
}

function sendFCMMessage($title, $message, $tokens)
{
    require_once("lib/httprequest.php");

    $url = "https://fcm.googleapis.com/fcm/send";
    $headers = array (
        "Authorization: key=AIzaSyCM3JQubRSFpkbXHT0HR4zp1UJulpoSFTw",
        "Content-Type: application/json",
        "Accept: application/json"
    );

    $data = array(
        "notification" => array(
            "title" => $title,
            "text" => $message
        ),
        "registration_ids" => $tokens
    );
    //echo json_encode($data);

    $output = HttpRequest($url, json_encode($data), "", $headers);
    return $output;
}

function like_post()
{
    global $response, $db_com;

    $user_id = $_POST["user_id"];
    $post_id = $_POST["post_id"];
    $type = $_POST["type"]; //1: like; 0:unlike
    $res = $db_com->like_post($user_id, $post_id, $type);
    $response["error"] = 0;
    $response["result"] = $res;
}

function googleplaceid2placeid()
{
    global $response, $db_com;
    $google_place_id = $_POST["place_id"];
    $id = $db_com->googleplaceid2placeid($google_place_id);
    if ($id > 0) {
        $response["result"] = $id;
        $response["error"] = 0;
    } else {
        $response["result"] = -1;
    }
}

function create_newfeed()
{
    global $response, $db_com;
    if (check_miss_required_params(["user_id","place_id"]) == 1) return;
    $user_id = $_POST["user_id"];
    $place_id= $_POST["place_id"];
    $status = $_POST["status"];
    $photos = array();
    if (uploadImages($photos) != 0) return;

    $res = $db_com->create_newfeed($user_id, $place_id, $status, $photos);
    if ($res == null) {
        $response["error"] = 1;
        $response["result"] = null;
    } else {
        $response["error"] = 0;
        $response["result"] = $res;
        notify_post_status($user_id, $place_id);
    }
}

function notify_post_status($own_user_id, $google_place_id) {
    global $response, $db_com;

    $title = "PlaceShare";
    $message = "Some one has just posted a new feeling to the place that you are following!";
    $user_ids = $db_com->get_users_follow_place($google_place_id);
    //var_dump($user_ids);
    $tokens = $db_com->getdevicetokens($user_ids);
    //var_dump($tokens);
    if ($tokens != null) {
        sendFCMMessage($title, $message, $tokens);
    }
}

function is_following_place() {
    global $response, $db_com;

    if (check_miss_required_params(["user_id","place_id"]) == 1) return;

    $user_id = $_POST["user_id"];
    $place_id = $_POST["place_id"];
    $is_following = $db_com->is_following_place($user_id, $place_id);
    if ($is_following === null) {
        $response["error"] = 1;
        $response["error_message"] = "Failed to check follow place status";
    } else {
        $response["error"] = 0;
        $response["is_following"] = $is_following;
    }
}

function nearby_search()
{
    global $response, $db_com;
    $lat = $_POST["lat"];
    $lng = $_POST["lng"];
    $keyword = $_POST["keyword"];

    $data = $db_com->nearby_search($lat, $lng, $keyword);
    $response["next_page_token"] = null;
    $response["status"] = "OK";
    $response["results"] = $data;
}


function querynewfeeds()
{
    global $response, $db_com;
    if (check_miss_required_params(["place_id"]) == 1) return;

    $place_id = $_POST["place_id"];
    $data = $db_com->querynewfeeds($place_id);

    if ($data != null) {
        $response["status"] = "OK";
        $response["results"] = $data;
        $response["error"] = 0;
    } else {
        $response["error"] = 0;
        $response["results"] = array();
    }
}

function check_miss_required_params($arr)
{
    global $response;
    foreach ($arr as $k => $v) {
        if (empty($_POST["$v"])) {
            $response[ERROR_MESSAGE] = "Miss $v parameter!";
            $response[ERROR] = 1;
            return 1;
        }
    }
    return 0;
}

//Functions
function uploadImages(&$fileURLs)
{
    ini_set("memory_limit", "100M");
    global $response;
    $filePathRoot = "upload/";

    $fileURLs = array();
    $FileImages = $_FILES["images"];
    ob_start();
    var_dump($FileImages);
    $result = ob_get_clean();
    $response["var_dump"] = $result;

    for ($i = 0; $i < count($FileImages["name"]); $i++) {
        $fileName = time() . "_" . basename($FileImages['name'][$i]);
        $filePath = $filePathRoot . $fileName;
        $fileURL = "http://itshareplus.com/" . $filePath;

        if ($FileImages['error'][$i] !== UPLOAD_ERR_OK) {
            $response["error_message"] = "Upload failed with error code " . $FileImages['error'][$i];
            return 1;
        }

//        if (!isImage($FileImages['tmp_name'][$i])) {
//            $response["error_message"] = "The file type is invalid";
//            return 1;
//        }

        if(move_uploaded_file($FileImages['tmp_name'][$i], $filePath)) {
            array_push($fileURLs, $fileURL);
        }
    }
    $response["size"] = count($FileImages["name"]);
    $response["photos"] = $fileURLs;
    return 0;
}

function uploadAnImage(&$imageURL) {

}

//Functions
function uploadFile(&$file_link, &$file_type)
{
    global $response;

    ini_set("memory_limit", "100M");
    $now = time();
    $file_path = "upload/";
    $file_path =  $file_path . $now . "_" . basename( $_FILES['upload_file']['name']);

    $file_link = $file_path;

    if ($_FILES['upload_file']['error'] !== UPLOAD_ERR_OK) {
        $response["error_message"] = "Upload failed with error code " . $_FILES['upload_file']['error'];
        return 1;
    }

    if (isImage($_FILES['upload_file']['tmp_name']))
    {
        $file_type = "image";
    }
    else if (isAudio($_FILES['upload_file']['tmp_name']))
    {
        $file_type = "audio";
    }
    else
    {
        $response["error_message"] = "The file type is invalid";
        return 1;
    }

    if(move_uploaded_file($_FILES['upload_file']['tmp_name'], $file_path)) {
        return 0;
    }
    $response["error_message"] = "Upload failed!";

    return 1;
}

function isImage($img)
{
    return (bool)getimagesize($img);
}

function isAudio()
{
    return true;
}


function insert_resource()
{
    global $response, $db_com;

    $location_long = $_POST["location_long"];
    $location_lat = $_POST["location_lat"];
    $date_start = empty($_POST["date_start"]) ? null : $_POST["date_start"];
    $date_end = empty($_POST["date_end"]) ? null : $_POST["date_end"];
    $note = empty($_POST["note"]) ? null : $_POST["note"];
    $user_id = $_POST["user_id"];
    //$tags = $_POST["tags"];

    $result = [];
    if (isset($_FILES["upload_file"]) && $_FILES["upload_file"]["error"] != UPLOAD_ERR_NO_FILE)
    {
        if (uploadFile($link, $file_type) == 0)
        {
            $id = $db_com->insert_resource($location_long, $location_lat, $file_type, $link, $date_start, $date_end, $note, $user_id);

            //$db_com->addTags($id, $tags);
            $result["id"] = $id;
            $result["link"] = $link;

            if ($id == "0")
            {
                $response["error"] = 1;
                $response["error_message"] = "Can't insert data to server";
            }
            else
            {
                $response["error"] = 0;
                $response["result"] = $result;
                if ($file_type == "audio")
                {
                    $db_com->insert_audio_detail($id, $_POST["title"], $_POST["artist"]);
                }
            }
        }
        else
        {
            $response["error"] = 1;
            //error_message is set in uploadFile
        }
    }
    else
    {
        $file_type = "status";
        $id = $db_com->insert_resource($location_long, $location_lat, $file_type, null, $date_start, $date_end, $note, $user_id);

        //$db_com->addTags($id, $tags);

        $response["error"] = 0;

        $result["id"] = $id;
        $response["result"] = $result;
    }
}


function get_resource_dummycode()
{
    echo base64_decode("eyAiZXJyb3IiOjAsICJjaWQiOiJnZXRfcmVzb3VyY2UiLCAicmVzdWx0IjpbIHsgImlkIjoiMSIsICJsb2NhdGlvbl9sb25nIjoiMTA2LjY4MiIsICJsb2NhdGlvbl9sYXQiOiIxMC43NjMiLCAidHlwZSI6InN0YXR1cyIsICJsaW5rIjpudWxsLCAiZGF0ZV9jcmVhdGlvbiI6IjIwMTYtMDQtMjkgMTc6MjU6MjgiLCAiZGF0ZV9zdGFydCI6bnVsbCwgImRhdGVfZW5kIjpudWxsLCAibm90ZSI6IlRoaXMgaXMgYSBub3RlISIsICJ1c2VyX2lkIjoiMSIgfSwgeyAiaWQiOiIyIiwgImxvY2F0aW9uX2xvbmciOiIxMDYuNjgyIiwgImxvY2F0aW9uX2xhdCI6IjEwLjc2MyIsICJ0eXBlIjoiaW1hZ2UiLCAibGluayI6InVwbG9hZFwvMTQ2MTkyNTk2NF9oYWNrdGhvbi5qcGciLCAiZGF0ZV9jcmVhdGlvbiI6IjIwMTYtMDQtMjkgMTc6MzI6NDQiLCAiZGF0ZV9zdGFydCI6bnVsbCwgImRhdGVfZW5kIjpudWxsLCAibm90ZSI6IkhpZXAgSVQgaGF2ZSBqdXN0IGdvbmUgaGVyZSEiLCAidXNlcl9pZCI6IjEiIH0sIHsgImlkIjoiMyIsICJsb2NhdGlvbl9sb25nIjoiMTA2LjY4MiIsICJsb2NhdGlvbl9sYXQiOiIxMC43NjMiLCAidHlwZSI6ImF1ZGlvIiwgImxpbmsiOiJ1cGxvYWRcL2lQaG9uZShNZXRyb0dub21lUmVtaXgpLm1wMyIsICJkYXRlX2NyZWF0aW9uIjoiMjAxNi0wNS0wNSAwODo0NTo0OSIsICJkYXRlX3N0YXJ0IjpudWxsLCAiZGF0ZV9lbmQiOm51bGwsICJub3RlIjoiaVBob25lIFJpbmd0b24gUmVtaXgiLCAidXNlcl9pZCI6IjEiLCAidGl0bGUiOiJOaFx1MWVhMWMgY2h1XHUwMGY0bmcgaVBob25lIFJlbWl4IiwgImFydGlzdCI6IkFwcGxlIiB9LCB7ICJpZCI6IjYiLCAibG9jYXRpb25fbG9uZyI6IjEwNi42NjkiLCAibG9jYXRpb25fbGF0IjoiMTAuNzY5NiIsICJ0eXBlIjoiaW1hZ2UiLCAibGluayI6InVwbG9hZFwvMTQ2MjQxOTMzMF90aW5odGUuanBnIiwgImRhdGVfY3JlYXRpb24iOiIyMDE2LTA1LTA1IDEwOjM1OjMwIiwgImRhdGVfc3RhcnQiOm51bGwsICJkYXRlX2VuZCI6bnVsbCwgIm5vdGUiOiJDaGVjayBpbiBDYWZlIFRpbmhUZSIsICJ1c2VyX2lkIjoiMSIgfSwgeyAiaWQiOiI4IiwgImxvY2F0aW9uX2xvbmciOiIxMDYuNjgyIiwgImxvY2F0aW9uX2xhdCI6IjEwLjc2MyIsICJ0eXBlIjoiYXVkaW8iLCAibGluayI6InVwbG9hZFwvMTQ2MjQyMTA3MF9LaG9uZ1BoYWlEYW5nVnVhRGF1TmhhY0NodW9uZy1Tb25UdW5nTVRQLTM3NTU1NDEubXAzIiwgImRhdGVfY3JlYXRpb24iOiIyMDE2LTA1LTA1IDExOjA0OjMwIiwgImRhdGVfc3RhcnQiOm51bGwsICJkYXRlX2VuZCI6bnVsbCwgIm5vdGUiOiJNXHUxZWRiaSBzXHUwMGUxbmcgc1x1MWVkYm0gYlx1MWVhZHQgbmhcdTFlYTFjIFNcdTAxYTFuIFRcdTAwZjluZyBsXHUwMGVhbiBuZ2hlIHRoXHUxZWFkdCBsXHUwMGUwIFx1MDExMVx1MDBlMyIsICJ1c2VyX2lkIjoiMSIsICJ0aXRsZSI6IktoXHUwMGY0bmcgcGhcdTFlYTNpIGRcdTFlYTFuZyB2XHUxZWViYSBcdTAxMTFcdTAwZTJ1IiwgImFydGlzdCI6IlNcdTAxYTFuIFRcdTAwZjluZyBNVFAiIH0sIHsgImlkIjoiOSIsICJsb2NhdGlvbl9sb25nIjoiMTA2LjY4MiIsICJsb2NhdGlvbl9sYXQiOiIxMC43NjMiLCAidHlwZSI6ImltYWdlIiwgImxpbmsiOiJ1cGxvYWRcLzE0NjI0MjM3MjZfaW1hZ2VfMjAxNjA1MDVfMDA0ODQyLmpwZyIsICJkYXRlX2NyZWF0aW9uIjoiMjAxNi0wNS0wNSAxMTo0ODo0NiIsICJkYXRlX3N0YXJ0IjpudWxsLCAiZGF0ZV9lbmQiOm51bGwsICJub3RlIjoiSGVsbG8gd29scmQiLCAidXNlcl9pZCI6IjEiIH0gXSB9");
}

function get_resource()
{
    global $response, $db_com;

    if (isset($_POST["location_lat"]) && isset($_POST["location_long"]))
    {
        $location_lat = $_POST["location_lat"];
        $location_long = $_POST["location_long"];
        $radius = empty($_POST["radius"]) ? 20 : $_POST["radius"];

        $result = $db_com->get_resources($location_lat, $location_long, $radius);

        $response["error"] = 0;
        $response["result"] = $result;
    }
}

function get_history()
{
    global $response, $db_com;

    if (isset($_POST["id"]))
    {
        $id = $_POST["id"];

        $result = $db_com->get_history($id);

        $response["error"] = 0;
        $response["result"] = $result;
    }
}


function login()
{
    global $response, $db_com;

    if (check_miss_required_params(["username", "password"]) == 1)
        return;

    $username = $_POST["username"];
    $password = $_POST["password"];
    $result = $db_com->userAuthenticaton($username, $password);
    $response["error"] = 0;
    $response["result"] = $result;
}

function make_friend()
{
    global $response, $db_com;

    if (!empty($_POST["own_id"]) && !empty($_POST["friend_id"]))
    {
        $own_id = $_POST["own_id"];
        $friend_id = $_POST["friend_id"];

        $db_com->addFriend($own_id, $friend_id);
        $response["error"] = 0;
    }
}

function register()
{
    global $response, $db_com;

    if (!empty($_POST["username"]) && !empty($_POST["password"]) && !empty($_POST["email"]))
    {
        $username = $_POST["username"];
        $password = $_POST["password"];
        $email = $_POST["email"];
        $last_user_id = $db_com->addUser($username, $password, $email);

        if ($last_user_id == "0") {
            $response["error"] = 1;
            $response["error_message"] = "Register failed!";
        } else {
            $response["error"] = 0;
            $response["result"] = $last_user_id;
        }
    }
    else
    {
        $response["error_message"] = "Some parameters is missed!";
    }
}

function searchTag()
{
    global $response, $db_com;

    if (isset($_POST["tag"]))
    {
        $tag = $_POST["tag"];

        $result = $db_com->searchTag($tag);

        $response["error"] = 0;
        $response["result"] = $result;
    }
}

?>