<?php

$servername = "localhost";
$username = "root";
$password = "";
$database = "pizzabestellungen";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT * FROM Pizza;";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo  $row["PizzaNr"]. " Name: " . $row["Name"] . " Preis: " . $row["Preis"] . "<br>";
    }
}
$conn->close();
?>