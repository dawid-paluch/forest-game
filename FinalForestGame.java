/* ***************************************
  @author    Dawid Paluch
  @date      9 December 2023
  @version   1

    Miniproject Level 8
    Explore the forest game - Records
    This program is a game about an mage
    going around a forest and healing the
    animals that are there using their magic.
   ****************************************/

   import java.util.Scanner; // Needed to make Scanner available
   import java.util.Random; // Needed to make Random available
   import java.io.*; // Needed to make input/output files available
   
   
   class ForestGame8
   {
       public static void main (String [] a) throws IOException
       {
           forestGameLevel8();
           return;
       } // END main
       
       //This method is used as a more efficient print method
       //
       public static void print (String printStatement){
           System.out.println(printStatement);
       }// END print
   
       //This method is used as a more efficient method for taking inputs
       //
       public static String input (String printStatement){
           Scanner scanner = new Scanner(System.in);
           print(printStatement);
   
           String userInput = scanner.next().toLowerCase();
           
           return userInput;
       }//END input
   
       //This method will be used for printing visible seperation between tasks for readability and formatting
       //
       public static void format(){
           print("\n======================================================================================\n");
           return;
       }
   
       //This method will be used to print a general message anytime the user has entered and invalid input
       //
       public static void printInvalidInput() {
           format();
           print("\t!!! YOU HAVE ENTERED AN INVALID INPUT !!! PLEASE TRY AGAIN !!!");
           format();
       }
   
       //This method will be used for creating a general menu used in different areas in the game that changes as appropriate
       //
       public static int generalMenuMethod (String statementToPrint, int upperLimit) {
           boolean validInput = false;
           int numberAsInt = upperLimit;
   
           while (!validInput) {
               boolean numeric = true;
               String possibleNumber = input(statementToPrint);
   
               for (int givenCharacter = 0; givenCharacter < possibleNumber.length(); givenCharacter++) {
                   char characterToCheck = possibleNumber.charAt(givenCharacter);
                   if (!Character.isDigit(characterToCheck)) {
                       numeric = false;
                   }
               }
   
               if (numeric) {
                   numberAsInt = Integer.parseInt(possibleNumber);
                   if (numberAsInt > 0 && numberAsInt <= upperLimit) {
                       validInput = true;
                   }
                   else {
                       printInvalidInput();
                   }
               } 
               else {
                   printInvalidInput();
               }
           }
           return numberAsInt;
       }
   
       //This method rolls a virtual die with a number of sides equal to the variable passed to it
       //
       public static int rollDie(int sides){
           Random random = new Random();
           int dieResult = 0;
           while (dieResult == 0){
               dieResult = random.nextInt(sides + 1);
           }
   
           return dieResult;
       }
   
       // Ask for the name of the user and save the value entered as a variable which gets returned by the method
       //
       public static String askName ()
       {
           String playerName;
       
           print("Before you begin your quest fellow traveller, I have a question to ask you.");
           playerName = input("Would you tell me the name that you go by?");
           
           format();
           return playerName;
       } // END askName
   
       // Prints a welcome message to the players screen that uses the players name to make it appropriate to the person
       //
       public static void introMessage ()
       {
           
           format();
           print("Welcome to, WanderingLizard Pines, fellow traveler.");
           print("Ah I see that you are one well versed in the magical arts.");
           print("How about you help some of the animals around this area.");
           print("I'm sure you'd find it quite beneficial.");
           print("How about you look around for now.");
           format();
       
           return;
       } // END introMessage
   
       //This method is used to combine a set of strings in an array into one statement making printing very easy
       //
       public static String combineStrings (String[] arrayOfStrings) {
           String finalString = "";
           
           for (int givenString = 0; givenString < arrayOfStrings.length; givenString++) {
               finalString += arrayOfStrings[givenString] + "\n";
           }
   
           return finalString;
       }
   
       //This method is used when the player encounters a creature while exploring the forest
       //
       public static String[] encounterWoundedCreature (int magicPoints, String species, int creatureCurrentHealth, int injuryLevel, int creatureFullHealth){
           int playerAction;
           String creatureHealed = "success";
           int[] healResults;
           
           print("You encounter a wounded " + species + " that has only " + creatureCurrentHealth +" health.");
   
           if (magicPoints == 0) {
               print("You have no magic and can't help the " + species + ".");
           }
   
           while (creatureFullHealth != creatureCurrentHealth && magicPoints != 0 && creatureHealed == "success"){
               print("\nYour magic points are at " + magicPoints + ".");
               print("The " + species + " is now at " + creatureCurrentHealth + " health.");
   
   
               String menuStatement1 = "Please enter the number for the option that you would like to choose to do:";
               String menuStatement2 = "\t1) Perform a 'Small Heal' on the " + species + ".";
               String menuStatement3 = "\t2) Run away from the " + species + ".";
               String menuStatement4 = "\nEnter Value: ";
               String[] menuStatements = {menuStatement1, menuStatement2, menuStatement3, menuStatement4};
               String menuStatementsString = combineStrings(menuStatements);
   
               format();
   
               playerAction = generalMenuMethod(menuStatementsString, 2);
   
               if (playerAction == 1) {
                   healResults = performSmallHeal(magicPoints, species, creatureCurrentHealth, creatureFullHealth, injuryLevel);
                   magicPoints = healResults[0];
                   creatureCurrentHealth = healResults[1];
               }
               else if (playerAction == 2) {
                   creatureHealed = "failure";
               }
               else {
                   printInvalidInput();
               }
           }
           print("The " + species + " is now at " + creatureCurrentHealth + " health.");
           print("Your magic points are now at " + magicPoints + ".");
   
           if (creatureCurrentHealth != creatureFullHealth){
               creatureHealed = "failure";
           }
           format();
   
           String[] returnValues = {creatureHealed,Integer.toString(magicPoints)};
           return returnValues;
       }
   
       //This method is used when the player tries to perform a small heal on a creature they find
       //
       public static int[] performSmallHeal (int magicPoints, String species, int targetHealth, int fullTargetHealth, int injuryLevel)
       {
           int[] returnValues = {0,0};
   
           if (magicPoints > 1){
               int result = rollDie(10); 
               if (result > injuryLevel){
                   magicPoints -= 2;
                   targetHealth += 5;
               }
               else{
                   magicPoints -= 5;
                   targetHealth += 2;
               }
               if (magicPoints < 0){
                   magicPoints = 0;
               }
           }
           else {
               print("You don't have enough magic points to perform a small heal.");
           }
   
           if (targetHealth > fullTargetHealth) {
               targetHealth = fullTargetHealth;
           }
   
           returnValues = new int[] {magicPoints,targetHealth};
   
           return returnValues;
       }// END performSmallHeal
   
       // This method sets up string to be used to create menu for loading a specific existing game
       //
       public static String[] existingGameNames() throws IOException {
           String returnString = "Please enter the number for the game that you would like to load:";
           int count = 1;
       
           BufferedReader playerFile = new BufferedReader (new FileReader("playerDetails.csv"));
   
           String givenPlayer = playerFile.readLine();
       
           while (givenPlayer != null) {
               String[] playerDetails = givenPlayer.split(",");
               String playerName = playerDetails[0];
   
               returnString += "\n\t" + count + ") " + playerName;
   
               count += 1;
               givenPlayer = playerFile.readLine();
           }
           playerFile.close();
   
           returnString += "\n\t" + count + ") Exit load existing game";
           returnString += "\nEnter value: ";
           String[] returnArray = {returnString,Integer.toString(count)};
   
           return returnArray;
       }
   
       // This method loads an existing game that the user has selected to play
       //
       public static PlayerDetails loadGivenGame (int playerIndex) throws IOException {
           BufferedReader playerFile = new BufferedReader (new FileReader("playerDetails.csv"));
           String lineData = "";
   
           for (int currentLine = 0; currentLine < playerIndex; currentLine++) {
               lineData = playerFile.readLine();
           }
           playerFile.close();
   
           print(lineData);
           String[] arrayData = lineData.split(",");
           PlayerDetails currentPlayer = createPlayer(arrayData[0], Integer.valueOf(arrayData[1]), Integer.valueOf(arrayData[2]), Integer.valueOf(arrayData[3]));
   
           return currentPlayer;
       }
   
       // This method is used for the load existing game menu
       //
       public static int[] loadGameMenu () throws IOException{
           String[] existingGamesArray = existingGameNames();
           String menuString = existingGamesArray[0];
           int count = Integer.valueOf(existingGamesArray[1]);
   
           int userChoice = generalMenuMethod(menuString, count);
           int[] returnArray = {userChoice, count};
   
           format();
           return returnArray;
       }
   
       // This is a method used to count the number of lines in a file
       //
       public static int linesOfFile (String fileName) throws IOException {
           BufferedReader openedFile = new BufferedReader (new FileReader(fileName));
           int lineCount = 0;
   
           while (openedFile.readLine() != null) {
               lineCount++;
           }
           openedFile.close();
   
           return lineCount;
       }
   
       // This method will be used for saving the player data when the user has finished playing so all data is saved
       //
       public static void saveData (PlayerDetails savingPlayer) throws IOException {
           int lines = linesOfFile("playerDetails.csv");
   
           BufferedReader playerFileRead = new BufferedReader (new FileReader("playerDetails.csv"));
   
           int savingPlayerIndex = 0;
           int linesForArray = 0;
   
           if (getLineIndex(savingPlayer) == -1) {
               savingPlayerIndex = lines;
               linesForArray = lines + 1;
           }
           else {
               savingPlayerIndex = getLineIndex(savingPlayer);
               linesForArray = lines;
           }
   
           String[] arrayOfPlayerData = new String[linesForArray];
   
           for (int givenPlayer = 0; givenPlayer<lines; givenPlayer++) {
               arrayOfPlayerData[givenPlayer] = playerFileRead.readLine();
           }
           playerFileRead.close();
   
           String name = getPlayerName(savingPlayer);
           String magicPoints = Integer.toString(getMagicPoints(savingPlayer));
           String score = Integer.toString(getPlayerScore(savingPlayer));
           String currentPlayerAsString = name + "," + magicPoints + "," + score + "," + savingPlayerIndex;
           arrayOfPlayerData[savingPlayerIndex] = currentPlayerAsString;
   
           PrintWriter playerFileWrite = new PrintWriter (new FileWriter("playerDetails.csv"));
           for (int givenPlayer = 0; givenPlayer<linesForArray; givenPlayer++) {
               playerFileWrite.println(arrayOfPlayerData[givenPlayer]);
           }
           playerFileWrite.close();
   
           return;
       }
   
       // This method creates a player for the ADT
       //
       public static PlayerDetails createPlayer (String name, int magicPoints, int score, int lineIndex) {
           PlayerDetails player = new PlayerDetails();
           player.name = name;
           player.magicPoints = magicPoints;
           player.score = score;
           player.lineIndex = lineIndex;
   
           return player;
       }
   
       // This method is used to get the name of the player in the ADT
       //
       public static String getPlayerName (PlayerDetails player) {
           String playerName = player.name;
   
           return playerName;
       }
   
       // This method sets the magic points of the player in the ADT
       //
       public static PlayerDetails setMagicPoints (PlayerDetails player, int magicPoints) {
           player.magicPoints = magicPoints;
   
           return player;
       }
   
       // This method gets the magic points of the player in the ADT
       //
       public static int getMagicPoints (PlayerDetails player) {
           int magicPoints = player.magicPoints;
   
           return magicPoints;
       }
   
       // This method increases the players score in the ADT
       //
       public static PlayerDetails addPoints (PlayerDetails player) {
           player.score += 5;
   
           return player;
       }
   
       // This method gets the score of the player in the ADT
       //
       public static int getPlayerScore (PlayerDetails player) {
           int playerScore =  player.score;
           
           return playerScore;
       }
   
       // This method gets the line index of the player in the ADT
       //
       public static int getLineIndex (PlayerDetails player) {
           int lineIndex = player.lineIndex;
   
           return lineIndex;
       }

       // This method sets the playingGame of the player in the ADT
       //
       public static PlayerDetails setPlayingGame (PlayerDetails player, boolean playingGame) {
            player.playingGame = playingGame;

            return player;
       } 

       // This method gets the playingGame of the player in the ADT
       //
       public static boolean getPlayingGame (PlayerDetails player) {
            boolean playingGame = player.playingGame;

            return playingGame;
       }
   
       // This method will be used to do the introduction sequence for the game at the start before the player actually begins
       //
       public static PlayerDetails startOfGame() throws IOException{
           boolean beginGame = false;
           boolean playGame = true;
           PlayerDetails player = new PlayerDetails();
   
           while (!beginGame) {
               String mainMenuStatement1 = "Please enter the number for the option that you would like to choose to do:";
               String mainMenuStatement2 = "\t1) Create a New Game";
               String mainMenuStatement3 = "\t2) Load an Existing Game";
               String mainMenuStatement4 = "\t3) Exit Game";
               String mainMenuStatement5 = "Enter Value: ";
               String[] mainMenuStatements = {mainMenuStatement1, mainMenuStatement2, mainMenuStatement3, mainMenuStatement4, mainMenuStatement5};
               String mainMenuStatementsString = combineStrings(mainMenuStatements);
   
               int mainMenuReply = generalMenuMethod(mainMenuStatementsString, 3);
               format();
   
               if (mainMenuReply == 1) {
                   player = createPlayer(askName(), 20, 0, -1);
                   beginGame = true;
               }
               else if (mainMenuReply == 2) {
                   int[] loadGameMenuResult = loadGameMenu();
                   int loadGameResult = loadGameMenuResult[0];
                   int cancelLoadIndex = loadGameMenuResult[1];
                   if (!(loadGameResult == cancelLoadIndex)) {
                       player = loadGivenGame(loadGameResult);
                       beginGame = true;
                   }
               }
               else {
                   beginGame = true;
                   playGame = false;
               }
           }
   
           player = setPlayingGame(player, playGame);
           return player;
       }
   
       // Forest game - main method that combines all other methods
       //
       public static void forestGameLevel8 () throws IOException {
           introMessage();
   
           String continueGame = "yes";
           String creatureHealed = "failure";
   
           PlayerDetails player = startOfGame();
           if (!getPlayingGame(player)) {
                continueGame = "no";
           }
           
           while(continueGame.equals("yes")){  
               String menuStatement1 = "Please enter the number for the option that you would like to choose to do:";
               String menuStatement2 = "\t1) Explore the Forest";
               String menuStatement3 = "\t2) Exit Game";
               String menuStatement4 = "\nEnter Value: ";
               String[] menuStatements = {menuStatement1, menuStatement2, menuStatement3, menuStatement4};
               String menuStatementsString = combineStrings(menuStatements);
   
               int gameMenuReply = generalMenuMethod(menuStatementsString, 2);
               format();
   
               if (gameMenuReply == 1) {
                   String[] woundedCreatureEncounterReturn = encounterWoundedCreature(player.magicPoints, "wolf", 3, 4, 20);
   
                   creatureHealed = woundedCreatureEncounterReturn[0];
                   player = setMagicPoints(player,Integer.parseInt(woundedCreatureEncounterReturn[1]));
   
                   if (creatureHealed.equals("success")) {
                       print("You successfully healed the creature.");
                       player = addPoints(player);
                   }
                   else {
                       print("You failed to heal the creature.");
                   }
               }
               else {
                   continueGame = "no";
                   saveData(player);
               }   
           }
           final String lastCreatureHealed = creatureHealed;
           final int finalMagicPoints = getMagicPoints(player);
           final int finalScore = getMagicPoints(player);
   
           return;
       } // END forestGameLevel8
   } // END class ForestGame8
   
   /*
This is a special class used to define an abstract data used as the player with operations:
       - create the player
       - get the name of the player
       - set the magic points of the player
       - get the magic points of the player
       - add five points to the score of the player
       - get the score of the player
       - get the lineIndex of the player
       - set the playingGame of the player
       - get the playingGame of the player
   */
   class PlayerDetails{
       String name;
       int magicPoints;
       int score;
       int lineIndex;
       boolean playingGame;
   }
