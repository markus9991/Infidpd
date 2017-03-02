<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "pizzabestellungen";

$id=$_POST["id"];

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "DELETE FROM Pizza WHERE PizzaNr=".$id;

	if ($conn->query($sql) === TRUE) {
		echo "Record deleted successfully";
	} else {
		echo "Error: " . $sql . "<br>" . $conn->error;
	}
	
?>