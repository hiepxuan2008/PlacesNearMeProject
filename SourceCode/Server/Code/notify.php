<?php

   

    $url = "https://fcm.googleapis.com/fcm/send";
    $headers = array (
        "Authorization: key=AIzaSyCM3JQubRSFpkbXHT0HR4zp1UJulpoSFTw",
        "Content-Type: application/json",
        "Accept: application/json"
    );
    $data = "{\"notification\":{\"title\":\"Title\",\"text\":\"Message\"},\"registration_ids\":[\"\"]}";
    //$res = HttpRequest($url, $data, "", $headers);
    //echo $res;

    $data = array(
      "notification" => array(
            "title" => "Title",
            "text" => "Message"
        ),
        "registration_ids" => array(
            "eGMF2wjuO8I:APA91bExbPMj9i7_DkkqUiTyqrX7S6MmeVnLDXIzRwl-Fis3b4L8T_ZCJ5v39BB1gzl2G4ti1MAP_dOesJ7c42ZNZrCGNQkI5jSp1tGJBccsJnbeZ_912HN3QbdE4xgzaUAZlBx4j4pf",
            "dATNcVUFnVw:APA91bE42SK4OXtOygCU6fxyr6Src_G8GQ9ghfKwdsICTXyXnRxJcGlhmzX_23-0dxUOBT69BZ0SSOsIXxIg3hCftFipuLczdV2WZ5p9A-Ip0sYfPyi-en51LRw38lHU_x3Ugbeh5xon"
        )
    );
    echo json_encode($data);
?>