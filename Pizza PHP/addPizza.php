<?php

$servername = "localhost";
$username = "root";
$password = "";
$database = "pizzabestellungen";

$id=$_POST["id"];
$name=$_POST["name"];
$preis=$_POST["preis"];

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


$sqls = "SELECT * FROM Pizza WHERE PizzaNr=$id;";

$result = $conn->query($sqls);

if ($result->num_rows > 0) {
   $sqlu="UPDATE Pizza SET Name='$name', Preis=$preis WHERE PizzaNr=$id";
   
   if ($conn->query($sqlu) === TRUE) {
		echo "Record updated successfully";
	} else {
		echo "Error: " . $sqlu . "<br>" . $conn->error;
	}
}
else{
	$sqli = "INSERT INTO Pizza (PizzaNr, Name, Preis)VALUES ('$id','$name','$preis')";

	if ($conn->query($sqli) === TRUE) {
		echo "New record created successfully";
	} else {
		echo "Error: " . $sqli . "<br>" . $conn->error;
	}
}

	
?>
	