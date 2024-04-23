import java.util.Scanner;

public class mazeRunner {

    public static void main(String[] args) {
        Maze myMap = new Maze();
        int movesCount = 0;

        intro(myMap);

        while (!myMap.didIWin()) {

            if (checkValidMoves(movesCount)) { // if no more valid moves left
                break;
            }
            else {
                movesCount = playGame(myMap, movesCount); // playGame, updates moves count, prints map
            }
        }

        endGame(myMap, movesCount);
    }

    public static void intro(Maze myMap) {
        System.out.println("Hello! Welcome to Maze Runner!");
        System.out.println("Here is your current position:");
        myMap.printMap();
    }

    public static int playGame(Maze myMap, int movesCount) {

        String moveDirection = getMoveDirection();


        if (myMap.isThereAPit(moveDirection)){ // checks for pit
            movesCount = checkPit(myMap, moveDirection, movesCount); //alerts user and asks if user wants to jump
        }
        else if (moveDirection.equals("R") && myMap.canIMoveRight()) {
            myMap.moveRight();
            movesCount += 1;
            myMap.printMap();
        }
        else if (moveDirection.equals("L") && myMap.canIMoveLeft()) {
            myMap.moveLeft();
            movesCount += 1;
            myMap.printMap();
        }
        else if (moveDirection.equals("U") && myMap.canIMoveUp()) {
            myMap.moveUp();
            movesCount += 1;
            myMap.printMap();
        }
        else if (moveDirection.equals("D") && myMap.canIMoveDown()) {
            myMap.moveDown();
            movesCount += 1;
            myMap.printMap();
        }
        else {
            switch (moveDirection) {
                case "R":
                    System.out.println("Sorry, you've hit a wall on your right.");
                    break;
                case "L":
                    System.out.println("Sorry, you've hit a wall on your left.");
                    break;
                case "U":
                    System.out.println("Sorry, you've hit a wall above you.");
                    break;
                case "D":
                    System.out.println("Sorry, you've hit a wall below you.");
                    break;
            }
        }
        System.out.println();
        return movesCount;
    }

    public static String getMoveDirection() {
        Scanner input = new Scanner(System.in);
        System.out.print("Where would you like to move? (R, L, U, D) ");
        String moveDirection = input.next();
        System.out.println("you chose to move " + moveDirection);
        System.out.println();


        moveDirection = moveDirection.toUpperCase(); //for ease of comparison

        while ((!moveDirection.equals("R"))
                && (!moveDirection.equals("L"))
                && (!moveDirection.equals("U"))
                && (!moveDirection.equals("D"))) {
            System.out.println("That was not a valid entry, please enter only the letters R or L or U or D");
            System.out.print("Where would you like to move? (R, L, U, D) ");
            moveDirection = input.next().toUpperCase();
        }

        return moveDirection;
    }

    public static int checkPit(Maze myMap, String moveDirection, int movesCount){

        if (navigatePit()){ // if user wants to jump
            if (myMap.canIJump(moveDirection)){ // if no obstacle
                    myMap.jumpOverPit(moveDirection);
                    movesCount += 1;
                    myMap.printMap();
            }
            else { // if there is an obstacle
                    System.out.println("Sorry, there's a wall.");
            }
        }
        else { // else if user does not want to jump
            System.out.println("You chose not to jump");
        }

        return movesCount;
    }

    public static boolean navigatePit() {
        Scanner input = new Scanner(System.in);
        System.out.print("Watch out! There's a pit ahead, jump it? ");
        String jump = input.next().substring(0,1).toUpperCase();
        System.out.println();

        return jump.equals("Y");
    }

    public static boolean checkValidMoves(int movesCount){
        boolean allMovesUsed = movesMessages(movesCount);
        if (allMovesUsed) {
            System.out.println("Sorry, but you didn't escape in time - you lose!");
        }
        return allMovesUsed;
    }

    public static boolean movesMessages(int movesCount) {

        boolean allMovesUsed = false;

        //System.out.println("You have taken " + moves + "moves");
        if (movesCount == 50) {
            System.out.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
        } else if (movesCount == 75) {
            System.out.println("Alert! You have made 75 moves, you only have 25 moves left to escape.");
        } else if (movesCount == 90) {
            System.out.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
        } else if (movesCount == 100) {
            System.out.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[");
            allMovesUsed = true;
        }

        return allMovesUsed;

    }

    public static void endGame(Maze myMap, int movesCount){
        // at end of game
        if (myMap.didIWin()) {
            System.out.println("Congratulations, you won!");
            System.out.println("You made it out in " + movesCount + " moves");
        } // if win
        else {
            System.out.println("Game Over");
        } // if lose

    }

}



