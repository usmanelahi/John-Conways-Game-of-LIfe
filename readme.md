--How to run project?
	1- Uncomment the JavaSwing and DB which you want to use in project (from main)
	   Select any one from both options given below:

		new MYSQLDB(game); (MYSQL DB)
        	new TextDB(game);  (Text Files DB)

		new GameOfLifeFrame(game); (Java swing)
        	new MainFrame(game); (Console JavaSwing)

	2- Compile the project and run main.java
		

--Game Of Life contains 2 JavaSwing Interfaces which are as following:
	1- GUI (Java Swing Interface)
	2- Console (Terminal Interface requires keyboard to interact with game)

        
--Game contains two DB's 
	1- MYSQL DB
	2- TEXT DB


--Project Details:
	1. Manually draw a pattern by selecting/deselecting cells
		Select any cell with updates the cell status
	2. Start
		Update the game status to start
		It creates new Thread named GameLoop and loop the game
	3. Next
		Update the current shape to next shape
	4. Stop
		Update the game status to stop.
		Stop GameLoop 
	5. Reset
		Reset the game to inital shape if game is running
		else clear the grid
	6. Save State
		Save current shape to connected DB
	7. Load State
		Load the last saved state into grid
	8. Speed Control
		You can change speed of the game by using speed bar 
	9. Grid Zoom
		You can change zoom by using zoom bar
		also you can move the grid by using up/down right/left arrow keys
	10. Counter
		Maintains the generation count
	11. View Saved States
		You can view saved states (of your choice) by clicking and selecting view states
	12. Delete a Saved State
		You can delete the last recent state from connected DB.

--MYSQL Connection Details:
	1- Execute these in MYSQL:
	create database gol;
	USE gol;
	CREATE TABLE `gol`.`CELLS` (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
	X_Axis int NOT NULL,
	Y_Axis int NOT NULL,
	Name char(30),
	PRIMARY KEY (id)
	);
	

	2- Change Host name & password in MYSQLDB.java according to your account(host name & password)
		String url = "jdbc:mysql://localhost/gol";
            	connect = DriverManager.getConnection(url, "root", "root");



--Game Of Life Sound:
	Set Path according to your PC:
		File file = new File("<your pc path where GameOfLife was stored>\\GameOfLife\\src\\com\\BL\\pinkPanther.wav");



