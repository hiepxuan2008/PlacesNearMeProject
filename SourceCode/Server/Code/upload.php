<?php

// Path to move uploaded files
$target_path = "upload/";

// array for final json respone
$response = array();


if (isset($_FILES['image']['name'])) {
    $target_path = $target_path . time() . "_" . basename($_FILES['image']['name']);
    $response['file_name'] = basename($_FILES['image']['name']);

    // final file url that is being uploaded
    $file_path = "http://itshareplus.com/" . $target_path;

    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path)) {
            // make error flag true
            $response['error'] = true;
            $response['message'] = 'Could not move the file!';
        }

        // File successfully uploaded
        $response['message'] = 'File uploaded successfully!';
        $response['error'] = 0;
        $response['file_path'] = $file_path;
    } catch (Exception $e) {
        // Exception occurred. Make error flag true
        $response['error'] = 1;
        $response['error_message'] = $e->getMessage();
    }
} else {
    // File parameter is missing
    $response['error'] = 1;
    $response['error_message'] = 'Not received any file!';
}

// Echo final json response to client
echo json_encode($response);
?>