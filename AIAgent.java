import java.util.*;

public class AIAgent{
    Random rand;

    public AIAgent(){
        rand = new Random();
    }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

    public Move randomMove(Stack possibilities){
        int moveID = rand.nextInt(possibilities.size());
        System.out.println("Agent randomly selected move : "+moveID);
        for(int i=1;i < (possibilities.size()-(moveID));i++){
            possibilities.pop();
        }
        Move selectedMove = (Move)possibilities.pop();
        return selectedMove;
    }

    //Next Best move
    public Move nextBestMove(Stack whitePossibilities, Stack blackPossibilities){
        Stack randomMove = (Stack) whitePossibilities.clone();
        Stack blackStackM = (Stack) blackPossibilities.clone();
        Move bestMove = null;
        Move whiteMove;
        Move presentMove;
        Square blackPosition;
        int pieceValue = 0;
        int chosenPieceValue = 0;

        while (!whitePossibilities.empty()){
            whiteMove = (Move)whitePossibilities.pop();
            presentMove = whiteMove;
            /*
            Compare white landing positions to black positions, if a piece is available to take white will capture
            If not the AI will make a random move
            */
            while (!blackStackM.isEmpty()){
                pieceValue = 0;
                blackPosition = (Square) blackStackM.pop();
                if ((presentMove.getLanding().getXC() == blackPosition.getXC()) && (presentMove.getLanding().getYC() == blackPosition.getYC())){

                    //Assign value to all of the pieces
                    if (blackPosition.getPieceName().equals("BlackKing")){
                        pieceValue = 10;
                    }
                    else if (blackPosition.getPieceName().equals("BlackQueen")){
                        pieceValue = 9;
                    }
                    else if (blackPosition.getPieceName().equals("BlackRook")){
                        pieceValue = 5;
                    }
                    else if (blackPosition.getPieceName().equals("BlackBishup")){
                        pieceValue = 3;
                    }
                    else if (blackPosition.getPieceName().equals("BlackKnight")){
                        pieceValue = 3;
                    }
                    else if (blackPosition.getPieceName().equals("BlackPawn")){
                        pieceValue = 1;
                    }
                }
                //updating the best move
                if (pieceValue > chosenPieceValue){
                    chosenPieceValue = pieceValue;
                    bestMove = presentMove;
                }
            }
            //reloading the black positions
            blackStackM = (Stack) blackPossibilities.clone();
        }
        // Make the best move
        if (chosenPieceValue > 0){
            System.out.println("Selected AI Agent - Next best move: " +chosenPieceValue);
            return bestMove;
        }
        //Random move if there is no best move
        return randomMove(randomMove);
    }

    public Move twoLevelsDeep(Stack possibilities){
        Move selectedMove = new Move();
        return selectedMove;
    }

    //Method for choosing which agent to play against
    public Move AIType(int AIPlayStyle, Stack whitePossibilities, Stack blackPossibilities){
        Move move = new Move();
        if(AIPlayStyle == 0){
            move = randomMove(whitePossibilities);
            return move;
        }
        else if(AIPlayStyle == 1){
            move = nextBestMove(whitePossibilities, blackPossibilities);
            return move;
        }
        else{
            return move;
        }
    }
}