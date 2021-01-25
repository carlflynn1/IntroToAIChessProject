import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

/*
	Carl Flynn
	x17347726
	BSHCDA4
	Practical 2
*/
 
public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;
	JPanel panels;
	JLabel pieces;

	AIAgent agent;
	private Boolean agentwins;
	private Boolean white2Move;
	Stack temporary;
	String winner;
	static int AIPlayStyle;


	//Setting up the board
    public ChessProject(){
        Dimension boardSize = new Dimension(600, 600);
 
        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane 
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
 
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );
 
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }
 
        // Setting up the Initial Chess board.
		for(int i=8;i < 16; i++){			
       		pieces = new JLabel( new ImageIcon("WhitePawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);	        
		}
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(0);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(1);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKnight.png") );
		panels = (JPanel)chessBoard.getComponent(6);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(2);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteBishup.png") );
		panels = (JPanel)chessBoard.getComponent(5);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteKing.png") );
		panels = (JPanel)chessBoard.getComponent(3);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
		panels = (JPanel)chessBoard.getComponent(4);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("WhiteRook.png") );
		panels = (JPanel)chessBoard.getComponent(7);
	    panels.add(pieces);
		for(int i=48;i < 56; i++){			
       		pieces = new JLabel( new ImageIcon("BlackPawn.png") );
			panels = (JPanel)chessBoard.getComponent(i);
	        panels.add(pieces);	        
		}
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(56);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(57);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKnight.png") );
		panels = (JPanel)chessBoard.getComponent(62);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(58);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackBishup.png") );
		panels = (JPanel)chessBoard.getComponent(61);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackKing.png") );
		panels = (JPanel)chessBoard.getComponent(59);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackQueen.png") );
		panels = (JPanel)chessBoard.getComponent(60);
	    panels.add(pieces);
		pieces = new JLabel( new ImageIcon("BlackRook.png") );
		panels = (JPanel)chessBoard.getComponent(63);
	    panels.add(pieces);


	    agent = new AIAgent();
	    white2Move = true;
	    agentwins = false;
	    temporary = new Stack();
    }

	// Checking if piece present on the board
	private Boolean piecePresent(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if(c instanceof JPanel){
			return false;
		}
		else{
			return true;
		}
	}
	// Checking if there is a black piece
	private Boolean checkWhiteOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();			
		if(((tmp1.contains("Black")))){
			oponent = true;
		}
		else{
			oponent = false; 
		}		
		return oponent;
	}
    // Checking if there is a white piece
	private Boolean checkBlackOponent(int newX, int newY){
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel)c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if(((tmp1.contains("White")))){
			oponent = true;
		}
		else{
			oponent = false;
		}
		return oponent;
	}
	//Find white pieces
	private Stack findWhitePieces() {
		Stack squares = new Stack();
		String icon;
		int x;
		int y;
		String pieceName;
		for (int i = 0; i < 600; i += 75) {
			for (int j = 0; j < 600; j += 75) {
				y = i/75;
				x = j/75;
				Component tmp = chessBoard.findComponentAt(j,i);
				if(tmp instanceof JLabel){
					chessPiece = (JLabel)tmp;
					icon = chessPiece.getIcon().toString();
					pieceName = icon.substring(0, (icon.length()-4));
					if(pieceName.contains("White")){
						Square stmp = new Square(x, y, pieceName);
						squares.push(stmp);
					}
				}
			}

		}
		return squares;
	}
	//Find Black Pieces
	private Stack findBlackPieces() {
		Stack squares = new Stack();
		String icon;
		int x;
		int y;
		String pieceName;
		for (int i = 0; i < 600; i += 75) {
			for (int j = 0; j < 600; j += 75) {
				y = i / 75;
				x = j / 75;
				Component tmp = chessBoard.findComponentAt(j, i);
				if (tmp instanceof JLabel) {
					chessPiece = (JLabel) tmp;
					icon = chessPiece.getIcon().toString();
					pieceName = icon.substring(0, (icon.length() - 4));
					if (pieceName.contains("Black")) {
						Square stmp = new Square(x, y, pieceName);
						squares.push(stmp);
					}
				}
			}
		}
		return squares;
	}
	//White attacking squares
	private Stack getWhiteAttackingSquares(Stack pieces){
		Stack piece = new Stack();
		while (!pieces.empty()){
			Square s = (Square) pieces.pop();
			String tmpString = s.getPieceName();
			if (tmpString.contains("Knight")){
				Stack tempK = getKnightMoves(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempK.empty()){
					Square tempKnight = (Square) tempK.pop();
					piece.push(tempKnight);
				}
			}
			else if (tmpString.contains("Pawn")){
				Stack tempP = getWhitePawnMoves(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempP.empty()){
					Square tempPawn = (Square) tempP.pop();
					piece.push(tempPawn);
				}
			}
			else if (tmpString.contains("Bishop")){
				Stack tempB = getBishopMoves(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempB.empty()){
					Square tempBishop = (Square) tempB.pop();
					piece.push(tempBishop);
				}
			}
			else if (tmpString.contains("Rook")){
				Stack tempR = getRookMoves(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempR.empty()){
					Square tempRook = (Square) tempR.pop();
					piece.push(tempRook);
				}
			}
			else if (tmpString.contains("Queen")){
				Stack tempQ = getQueenMoves(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempQ.empty()){
					Square tempQueen = (Square) tempQ.pop();
					piece.push(tempQueen);
				}
			}
			else if (tmpString.contains("King")){
				Stack tempK = getKingSquares(s.getXC(), s.getYC(), s.getPieceName());
				while (!tempK.empty()){
					Square tempKing = (Square) tempK.pop();
					piece.push(tempKing);
				}
			}
		}
		return piece;
	}
	//Colouring the squares that are available to move to
	private void colorSquares(Stack squares){
		Border greenBorder = BorderFactory.createLineBorder(Color.GREEN,3);
		while(!squares.empty()){
			Square s = (Square)squares.pop();
			int location = s.getXC() + ((s.getYC())*8);
			JPanel panel = (JPanel)chessBoard.getComponent(location);
			panel.setBorder(greenBorder);
		}
	}
	//Get white pawn squares
	private Stack getWhitePawnMoves(int x, int y, String piece){
		Square startingSquare = new Square(x, y, piece);
		Stack moves = new Stack();
		Stack pawnMove = new Stack();

		Square s = new Square(x, y + 1, piece);
		moves.push(s);
		Square s1 = new Square(x + 1, y + 1, piece);
		moves.push(s1);
		Square s2 = new Square(x - 1, y + 1, piece);
		moves.push(s2);
		Square s3 = new Square(x, y + 2, piece);
		moves.push(s3);

		for (int i = 0; i < 4; i++){
			Square tmp = (Square) moves.pop();
			Move tempMove = new Move(startingSquare, tmp);

			//  Keep on the board
			if ((tmp.getXC() < 0) || (tmp.getXC() > 7) || (tmp.getYC() < 0) || (tmp.getYC() > 7)){
			}
			//  Moving 1 Square Up
			else if (startingSquare.getYC() == 1){
				if ((tmp.getXC() > startingSquare.getXC() || tmp.getXC() < startingSquare.getXC())){
					if (piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))){
						if (piece.contains("White")){
							if (checkWhiteOponent(((tmp.getXC() * 75) + 20), ((tmp.getYC() * 75) + 20))){
								pawnMove.push(tempMove);
							}
						}
					}
				}
				else{
					if (!(piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20))))){
						if (tmp.getYC() - startingSquare.getYC() == 1){
							pawnMove.push(tempMove);
						}
						else if (!(piecePresent(((tmp.getXC() * 75) + 20), ((((tmp.getYC() - 1) * 75) + 20))))){
							pawnMove.push(tempMove);
						}
					}

				}
			}
			else{
				if ((tmp.getXC() > startingSquare.getXC() || tmp.getXC() < startingSquare.getXC())){
					if (piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))){
						if (piece.contains("White")){
							if (checkWhiteOponent(((tmp.getXC() * 75) + 20), ((tmp.getYC() * 75) + 20))){
								pawnMove.push(tempMove);
							}
						}
					}
				}
				else{
					if (!(piecePresent(((tmp.getXC() * 75) + 20), (((tmp.getYC() * 75) + 20)))) && (tmp.getYC() - startingSquare.getYC()) == 1){
						pawnMove.push(tempMove);
					}
				}
			}
		}
		return pawnMove;
	}
	//Checking if a king is in the surrounding squares
	private Boolean checkSurroundingSquares(Square s){
		Boolean possible = false;
		int x = s.getXC()*75;
		int y = s.getYC()*75;
		if(!((getPieceName((x+75), y).contains("BlackKing"))||(getPieceName((x-75), y).contains("BlackKing"))||(getPieceName(x,(y+75)).contains("BlackKing"))||(getPieceName((x), (y-75)).contains("BlackKing"))||(getPieceName((x+75),(y+75)).contains("BlackKing"))||(getPieceName((x-75),(y+75)).contains("BlackKing"))||(getPieceName((x+75),(y-75)).contains("BlackKing"))||(getPieceName((x-75), (y-75)).contains("BlackKing")))){
			possible = true;
		}
		return possible;
	}
	//Getting King Moves
	private Stack getKingSquares(int x, int y, String piece){
		Square startingSquare = new Square(x, y, piece);
		Stack moves = new Stack();
		Move validM, validM2, validM3, validM4;
		int tmpx1 = x+1;
		int tmpx2 = x-1;
		int tmpy1 = y+1;
		int tmpy2 = y-1;

		if(!((tmpx1 > 7))){
    /*
      This is the first condition where we will be working with the column where x increases.
      If we consider x increasing, we need to make sure that we don't fall off the board, so we use
      a condition here to check that the new value of x (tmpx1) is not greater than 7.

      From the grid above we can see in this column that there are three possible squares for us to check in
      this column:
      - were y decreases, y-1
      - were y increases, y+1
      - or were y stays the same

      The first step is to construct three new Squares for each of these possibilities.
      As the unchanged y value is already a location on the board we don't need to check the location and can simply
      make a call to checkSurroundingSquares for this new Square.

      If checkSurroundingSquares returns a positive value we jump inside the condition below:
        - firstly we create a new Move, which takes the starting square and the landing square that we have just checked with
          checkSurroundingSquares.
        - Next we need to figure out if there is a piece present on the square and if so make sure
          that the piece is an opponents piece.
        - Once we make sure that we are either moving to an empty square or we are taking our opponents piece we can push this
          possible move onto our stack of possible moves called "moves".

      This process is followed again for the other temporary squares created.

      After we check for all possoble squares on this column, we repeat the process for the other columns as identified above
      in the grid.
    */
			Square tmp = new Square(tmpx1, y, piece);
			Square tmp1 = new Square(tmpx1, tmpy1, piece);
			Square tmp2 = new Square(tmpx1, tmpy2, piece);
			if(checkSurroundingSquares(tmp)){
				validM = new Move(startingSquare, tmp);
				if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
					moves.push(validM);
				}
				else{
					if(checkWhiteOponent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
						moves.push(validM);
					}
				}
			}
			if(!(tmpy1 > 7)){
				if(checkSurroundingSquares(tmp1)){
					validM2 = new Move(startingSquare, tmp1);
					if(!piecePresent(((tmp1.getXC()*75)+20), (((tmp1.getYC()*75)+20)))){
						moves.push(validM2);
					}
					else{
						if(checkWhiteOponent(((tmp1.getXC()*75)+20), (((tmp1.getYC()*75)+20)))){
							moves.push(validM2);
						}
					}
				}
			}
			if(!(tmpy2 < 0)){
				if(checkSurroundingSquares(tmp2)){
					validM3 = new Move(startingSquare, tmp2);
					if(!piecePresent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
						moves.push(validM3);
					}
					else{
						System.out.println("The values that we are going to be looking at are : "+((tmp2.getXC()*75)+20)+" and the y value is : "+((tmp2.getYC()*75)+20));
						if(checkWhiteOponent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
							moves.push(validM3);
						}
					}
				}
			}
		}
		if(!((tmpx2 < 0))){
			Square tmp3 = new Square(tmpx2, y, piece);
			Square tmp4 = new Square(tmpx2, tmpy1, piece);
			Square tmp5 = new Square(tmpx2, tmpy2, piece);
			if(checkSurroundingSquares(tmp3)){
				validM = new Move(startingSquare, tmp3);
				if(!piecePresent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
					moves.push(validM);
				}
				else{
					if(checkWhiteOponent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
						moves.push(validM);
					}
				}
			}
			if(!(tmpy1 > 7)){
				if(checkSurroundingSquares(tmp4)){
					validM2 = new Move(startingSquare, tmp4);
					if(!piecePresent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
						moves.push(validM2);
					}
					else{
						if(checkWhiteOponent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
							moves.push(validM2);
						}
					}
				}
			}
			if(!(tmpy2 < 0)){
				if(checkSurroundingSquares(tmp5)){
					validM3 = new Move(startingSquare, tmp5);
					if(!piecePresent(((tmp5.getXC()*75)+20), (((tmp5.getYC()*75)+20)))){
						moves.push(validM3);
					}
					else{
						if(checkWhiteOponent(((tmp5.getXC()*75)+20), (((tmp5.getYC()*75)+20)))){
							moves.push(validM3);
						}
					}
				}
			}
		}
		Square tmp7 = new Square(x, tmpy1, piece);
		Square tmp8 = new Square(x, tmpy2, piece);
		if(!(tmpy1 > 7)){
			if(checkSurroundingSquares(tmp7)){
				validM2 = new Move(startingSquare, tmp7);
				if(!piecePresent(((tmp7.getXC()*75)+20), (((tmp7.getYC()*75)+20)))){
					moves.push(validM2);
				}
				else{
					if(checkWhiteOponent(((tmp7.getXC()*75)+20), (((tmp7.getYC()*75)+20)))){
						moves.push(validM2);
					}
				}
			}
		}
		if(!(tmpy2 < 0)){
			if(checkSurroundingSquares(tmp8)){
				validM3 = new Move(startingSquare, tmp8);
				if(!piecePresent(((tmp8.getXC()*75)+20), (((tmp8.getYC()*75)+20)))){
					moves.push(validM3);
				}
				else{
					if(checkWhiteOponent(((tmp8.getXC()*75)+20), (((tmp8.getYC()*75)+20)))){
						moves.push(validM3);
					}
				}
			}
		}
		return moves;
	}
	//Getting Queen Moves
	private Stack getQueenMoves(int x, int y, String piece){
		Stack completeMoves = new Stack();
		Stack tmpMoves = new Stack();
		Move tmp;
  /*
      The Queen is a pretty easy piece to figure out if you have completed the
      Bishop and the Rook movements. Either the Queen is going to move like a
      Bishop or its going to move like a Rook, so all we have to do is make a call to both of these
      methods.
  */
		tmpMoves = getRookMoves(x, y, piece);
		while(!tmpMoves.empty()){
			tmp = (Move)tmpMoves.pop();
			completeMoves.push(tmp);
		}
		tmpMoves = getBishopMoves(x, y, piece);
		while(!tmpMoves.empty()){
			tmp = (Move)tmpMoves.pop();
			completeMoves.push(tmp);
		}
		return completeMoves;
	}
	//Getting Rook Moves
	private Stack getRookMoves(int x, int y, String piece){
		Square startingSquare = new Square(x, y, piece);
		Stack moves = new Stack();
		Move validM, validM2, validM3, validM4;
  /*
    There are four possible directions that the Rook can move to:
      - the x value is increasing
      - the x value is decreasing
      - the y value is increasing
      - the y value is decreasing

    Each of these movements should be catered for. The loop guard is set to incriment up to the maximun number of squares.
    On each iteration of the first loop we are adding the value of i to the current x coordinate.
    We make sure that the new potential square is going to be on the board and if it is we create a new square and a new potential
    move (originating square, new square).If there are no pieces present on the potential square we simply add it to the Stack
    of potential moves.
    If there is a piece on the square we need to check if its an opponent piece. If it is an opponent piece its a valid move, but we
    must break out of the loop using the Java break keyword as we can't jump over the piece and search for squares. If its not
    an opponent piece we simply break out of the loop.

    This cycle needs to happen four times for each of the possible directions of the Rook.
  */
		for(int i=1;i < 8;i++){
			int tmpx = x+i;
			int tmpy = y;
			if(!(tmpx > 7 || tmpx < 0)){
				Square tmp = new Square(tmpx, tmpy, piece);
				validM = new Move(startingSquare, tmp);
				if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
					moves.push(validM);
				}
				else{
					if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
						moves.push(validM);
						break;
					}
					else{
						break;
					}
				}
			}
		}//end of the loop with x increasing and Y doing nothing...
		for(int j=1;j < 8;j++){
			int tmpx1 = x-j;
			int tmpy1 = y;
			if(!(tmpx1 > 7 || tmpx1 < 0)){
				Square tmp2 = new Square(tmpx1, tmpy1, piece);
				validM2 = new Move(startingSquare, tmp2);
				if(!piecePresent(((tmp2.getXC()*75)+20), (((tmp2.getYC()*75)+20)))){
					moves.push(validM2);
				}
				else{
					if(checkWhiteOponent(((tmp2.getXC()*75)+20), ((tmp2.getYC()*75)+20))){
						moves.push(validM2);
						break;
					}
					else{
						break;
					}
				}
			}
		}//end of the loop with x increasing and Y doing nothing...
		for(int k=1;k < 8;k++){
			int tmpx3 = x;
			int tmpy3 = y+k;
			if(!(tmpy3 > 7 || tmpy3 < 0)){
				Square tmp3 = new Square(tmpx3, tmpy3, piece);
				validM3 = new Move(startingSquare, tmp3);
				if(!piecePresent(((tmp3.getXC()*75)+20), (((tmp3.getYC()*75)+20)))){
					moves.push(validM3);
				}
				else{
					if(checkWhiteOponent(((tmp3.getXC()*75)+20), ((tmp3.getYC()*75)+20))){
						moves.push(validM3);
						break;
					}
					else{
						break;
					}
				}
			}
		}//end of the loop with x increasing and Y doing nothing...
		for(int l=1;l < 8;l++){
			int tmpx4 = x;
			int tmpy4 = y-l;
			if(!(tmpy4 > 7 || tmpy4 < 0)){
				Square tmp4 = new Square(tmpx4, tmpy4, piece);
				validM4 = new Move(startingSquare, tmp4);
				if(!piecePresent(((tmp4.getXC()*75)+20), (((tmp4.getYC()*75)+20)))){
					moves.push(validM4);
				}
				else{
					if(checkWhiteOponent(((tmp4.getXC()*75)+20), ((tmp4.getYC()*75)+20))){
						moves.push(validM4);
						break;
					}
					else{
						break;
					}
				}
			}
		}//end of the loop with x increasing and Y doing nothing...
		return moves;
	}
	//Getting Bishop Moves
	private Stack getBishopMoves(int x, int y, String piece){
		Square startingSquare = new Square(x, y, piece);
		Stack moves = new Stack();
		Move validM, validM2, validM3, validM4;
  /*
    The Bishop can move along any diagonal until it hits an enemy piece or its own piece
    it cannot jump over its own piece. We need to use four different loops to go through the possible movements
    to identify possible squares to move to. The temporary squares, i.e. the values of x and y must change by the
    same amount on each iteration of each of the loops.

    If the new values of x and y are on the board, we create a new square and a new move (from the original square to the new
    square). We then check if there is a piece present on the new square:
    - if not we add the move as a possible new move
    - if there is a piece we make sure that we can capture our opponents piece and we cannot take our own piece
      and then we break out of the loop

    This process is repeated for each of the other three possible diagonals that the Bishop can travel along.

  */
		for(int i=1;i < 8;i++){
			int tmpx = x+i;
			int tmpy = y+i;
			if(!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)){
				Square tmp = new Square(tmpx, tmpy, piece);
				validM = new Move(startingSquare, tmp);
				if(!piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
					moves.push(validM);
				}
				else{
					if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
						moves.push(validM);
						break;
					}
					else{
						break;
					}
				}
			}
		} // end of the first for Loop
		for(int k=1;k < 8;k++){
			int tmpk = x+k;
			int tmpy2 = y-k;
			if(!(tmpk > 7 || tmpk < 0 || tmpy2 > 7 || tmpy2 < 0)){
				Square tmpK1 = new Square(tmpk, tmpy2, piece);
				validM2 = new Move(startingSquare, tmpK1);
				if(!piecePresent(((tmpK1.getXC()*75)+20), (((tmpK1.getYC()*75)+20)))){
					moves.push(validM2);
				}
				else{
					if(checkWhiteOponent(((tmpK1.getXC()*75)+20), ((tmpK1.getYC()*75)+20))){
						moves.push(validM2);
						break;
					}
					else{
						break;
					}
				}
			}
		} //end of second loop.
		for(int l=1;l < 8;l++){
			int tmpL2 = x-l;
			int tmpy3 = y+l;
			if(!(tmpL2 > 7 || tmpL2 < 0 || tmpy3 > 7 || tmpy3 < 0)){
				Square tmpLMov2 = new Square(tmpL2, tmpy3, piece);
				validM3 = new Move(startingSquare, tmpLMov2);
				if(!piecePresent(((tmpLMov2.getXC()*75)+20), (((tmpLMov2.getYC()*75)+20)))){
					moves.push(validM3);
				}
				else{
					if(checkWhiteOponent(((tmpLMov2.getXC()*75)+20), ((tmpLMov2.getYC()*75)+20))){
						moves.push(validM3);
						break;
					}
					else{
						break;
					}
				}
			}
		}// end of the third loop
		for(int n=1;n < 8;n++){
			int tmpN2 = x-n;
			int tmpy4 = y-n;
			if(!(tmpN2 > 7 || tmpN2 < 0 || tmpy4 > 7 || tmpy4 < 0)){
				Square tmpNmov2 = new Square(tmpN2, tmpy4, piece);
				validM4 = new Move(startingSquare, tmpNmov2);
				if(!piecePresent(((tmpNmov2.getXC()*75)+20), (((tmpNmov2.getYC()*75)+20)))){
					moves.push(validM4);
				}
				else{
					if(checkWhiteOponent(((tmpNmov2.getXC()*75)+20), ((tmpNmov2.getYC()*75)+20))){
						moves.push(validM4);
						break;
					}
					else{
						break;
					}
				}
			}
		}// end of the last loop
		return moves;
	}
	//Getting knight moves
	private Stack getKnightMoves(int x, int y, String piece){
		Stack moves = new Stack();
		Stack attacking = new Stack();

		Square s = new Square(x+1, y+2);
		moves.push(s);
		Square s1 = new Square(x+1, y-2);
		moves.push(s1);
		Square s2 = new Square(x-1, y+2);
		moves.push(s2);
		Square s3 = new Square(x-1, y-2);
		moves.push(s3);
		Square s4 = new Square(x+2, y+1);
		moves.push(s4);
		Square s5 = new Square(x+2, y-1);
		moves.push(s5);
		Square s6 = new Square(x-2, y+1);
		moves.push(s6);
		Square s7 = new Square(x-2, y-1);
		moves.push(s7);
		for(int i=0; i < 8; i++){
			Square tmp = (Square)moves.pop();
			if((tmp.getXC() < 0)||(tmp.getXC() > 7)||(tmp.getYC() < 0)||(tmp.getYC() > 7)){
			}
			else if(piecePresent(((tmp.getXC()*75)+20), (((tmp.getYC()*75)+20)))){
				if(piece.contains("White")){
					if(checkWhiteOponent(((tmp.getXC()*75)+20), ((tmp.getYC()*75)+20))){
						attacking.push(tmp);
					}
					else{
						System.out.println("It's our own piece");
					}
				}
				else{
					if(checkBlackOponent(tmp.getXC(), tmp.getYC())){
						attacking.push(tmp);
					}
				}
			}
			else{
				attacking.push(tmp);
			}
		}
		Stack tmp = attacking;
		colorSquares(tmp);
		return attacking;
	}
	//Highlighting the moves available for the AI to move to
	private void getLandingSquares(Stack found){
		Move tmp;
		Square landing;
		Stack squares = new Stack();
		while(!found.empty()){
			tmp = (Move)found.pop();
			landing = (Square)tmp.getLanding();
			squares.push(landing);
		}
		colorSquares(squares);
	}
	//Resetting Borders
	private void resetBorders(){
		Border empty = BorderFactory.createEmptyBorder();
		for(int i=0;i < 64;i++){
			JPanel tmppanel = (JPanel)chessBoard.getComponent(i);
			tmppanel.setBorder(empty);
		}
	}
	//Printing possible moves for a piece
	private void printStack(Stack input){
		Move m;
		Square s, l;
		while(!input.empty()){
			m = (Move)input.pop();
			s = (Square)m.getStart();
			l = (Square)m.getLanding();
			System.out.println("The possible move that was found is : ("+s.getXC()+" , "+s.getYC()+"), landing at ("+l.getXC()+" , "+l.getYC()+")");
		}
	}
	//Getting piece names
	private String getPieceName(int x, int y){
		Component c = chessBoard.findComponentAt(x, y);
		if ((c instanceof JLabel))
		{
			JLabel awaitingPiece = (JLabel) c;
			return awaitingPiece.getIcon().toString();
		} else
		{
			return "";
		}
	}
	//Making the AI Move
	private void makeAIMove(){
  /*
    When the AI Agent decides on a move, a red border shows the square from where the move started and the
    landing square of the move.
  */
		resetBorders();
		layeredPane.validate();
		layeredPane.repaint();
		Stack white = findWhitePieces();
		Stack black = findBlackPieces();
		Stack completeMoves = new Stack();
		Move tmp;
		while(!white.empty()){
			Square s = (Square)white.pop();
			String tmpString = s.getPieceName();
			Stack tmpMoves = new Stack();
			Stack temporary = new Stack();
    /*
        We need to identify all the possible moves that can be made by the AI Opponent
    */
			if(tmpString.contains("Knight")){
				tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getPieceName());
			}
			else if(tmpString.contains("Bishop")){
				tmpMoves = getBishopMoves(s.getXC(), s.getYC(), s.getPieceName());
			}
			else if(tmpString.contains("Pawn")){
				tmpMoves = getWhitePawnMoves(s.getXC(), s.getYC(), s.getPieceName());
			}
			else if(tmpString.contains("Rook")){
				tmpMoves = getRookMoves(s.getXC(), s.getYC(), s.getPieceName());
			}
			else if(tmpString.contains("Queen")){
				tmpMoves = getQueenMoves(s.getXC(), s.getYC(), s.getPieceName());
			}
			else if(tmpString.contains("King")){
				tmpMoves = getKingSquares(s.getXC(), s.getYC(), s.getPieceName());
			}

			while(!tmpMoves.empty()){
				tmp = (Move)tmpMoves.pop();
				completeMoves.push(tmp);
			}
		}
		temporary = (Stack) completeMoves.clone();
		getLandingSquares(temporary);
		printStack(temporary);
/*
So now we should have a copy of all the possible moves to make in our Stack called completeMoves
*/
		if(completeMoves.size() == 0){
			//Stalemate
			JOptionPane.showMessageDialog(null, "Congratulations, you have placed the AI component in a Stale Mate Position");
			System.exit(0);

		}
		else{
  /*
    Okay, so we can make a move now. We have a stack of all possible moves and need to call the correct agent to select
    one of these moves. Lets print out the possible moves to the standard output to view what the options are for
    White. Later when you are finished the continuous assessment you don't need to have such information being printed
    out to the standard output.
  */
			System.out.println("=============================================================");
			Stack testing = new Stack();
			while(!completeMoves.empty()){
				Move tmpMove = (Move)completeMoves.pop();
				Square s1 = (Square)tmpMove.getStart();
				Square s2 = (Square)tmpMove.getLanding();
				System.out.println("The "+s1.getPieceName()+" can move from ("+s1.getXC()+", "+s1.getYC()+") to the following square: ("+s2.getXC()+", "+s2.getYC()+")");
				testing.push(tmpMove);
			}
			//AI selecting a move to make
			System.out.println("=============================================================");
			Border redBorder = BorderFactory.createLineBorder(Color.RED, 3);
			Move selectedMove = agent.AIType(AIPlayStyle, testing, black);
			Square startingPoint = (Square)selectedMove.getStart();
			Square landingPoint = (Square)selectedMove.getLanding();
			int startX1 = (startingPoint.getXC()*75)+20;
			int startY1 = (startingPoint.getYC()*75)+20;
			int landingX1 = (landingPoint.getXC()*75)+20;
			int landingY1 = (landingPoint.getYC()*75)+20;
			System.out.println("-------- Move "+startingPoint.getPieceName()+" ("+startingPoint.getXC()+", "+startingPoint.getYC()+") to ("+landingPoint.getXC()+", "+landingPoint.getYC()+")");

			Component c  = (JLabel)chessBoard.findComponentAt(startX1, startY1);
			Container parent = c.getParent();
			parent.remove(c);
			int panelID = (startingPoint.getYC() * 8)+startingPoint.getXC();
			panels = (JPanel)chessBoard.getComponent(panelID);
			panels.setBorder(redBorder);
			parent.validate();

			Component l = chessBoard.findComponentAt(landingX1, landingY1);
			if(l instanceof JLabel){
				Container parentLanding = l.getParent();
				JLabel awaitingName = (JLabel)l;
				String agentCaptured = awaitingName.getIcon().toString();
				if(agentCaptured.contains("King")){
					agentwins = true;
				}
				parentLanding.remove(l);
				parentLanding.validate();
				pieces = new JLabel( new ImageIcon(startingPoint.getPieceName()+".png") );
				int landingPanelID = (landingPoint.getYC()*8)+landingPoint.getXC();
				panels = (JPanel)chessBoard.getComponent(landingPanelID);
				panels.add(pieces);
				panels.setBorder(redBorder);
				layeredPane.validate();
				layeredPane.repaint();

				if(agentwins){
					JOptionPane.showMessageDialog(null, "The AI Agent has won!");
					System.exit(0);
				}
				//AI promoting white pawn to a queen
				if(startingPoint.getPieceName().contains("Pawn") && landingPoint.getYC() == 7){
					parentLanding.remove(0);
					pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
					landingPanelID = (landingPoint.getYC()*8) + landingPoint.getXC();
					panels = (JPanel) chessBoard.getComponent(landingPanelID);
					panels.add(pieces);
				}
			}
			else{
				pieces = new JLabel( new ImageIcon(startingPoint.getPieceName()+".png") );
				int landingPanelID = (landingPoint.getYC()*8)+landingPoint.getXC();
				panels = (JPanel)chessBoard.getComponent(landingPanelID);
				panels.add(pieces);
				panels.setBorder(redBorder);
				layeredPane.validate();
				layeredPane.repaint();
			}
			white2Move = false;
		}
	}
	//Mouse pressed method
    public void mousePressed(MouseEvent e){
        chessPiece = null;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel) 
			return;
 
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX()/75);
		startY = (e.getY()/75);
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }
	//Mouse dragged method
    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
         chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
     }
	//Mouse released method
    public void mouseReleased(MouseEvent e) {
        if(chessPiece == null) return;

        chessPiece.setVisible(false);
		Boolean success =false;
		Boolean progression =false;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length()-4));
		Boolean validMove = false;

		int landingX = (e.getX()/75);
		int landingY = (e.getY()/75);
		int xMovement = Math.abs((e.getX()/75)-startX);
		int yMovement = Math.abs((e.getY()/75)-startY);
		System.out.println("----------------------------");
		System.out.println("The piece that is being moved is :"+pieceName);
		System.out.println("The starting coordinates are : "+"("+startX+","+startY+")");
		System.out.println("The xMovement is : "+xMovement);
		System.out.println("The yMovement is : "+yMovement);
		System.out.println("The starting coordinates are : "+"("+landingX+","+landingY+")");
		System.out.println("----------------------------");

		Boolean possible = false;
		if(white2Move){
			if(pieceName.contains("White")){
				possible = true;
			}
		}
		else if(pieceName.contains("Black")){
			possible = true;
		}

		if(possible) {
			//White Pawn
			if (pieceName.equals("WhitePawn")) {
				if (startY == 1) {
					//1 or 2 Square move
					if (((xMovement == 0)) && ((yMovement == 1) || ((yMovement) == 2))) {
						if (yMovement == 2) {
							if ((!piecePresent(e.getX(), (e.getY()))) && (!piecePresent(e.getX(), (e.getY() - 75)))) {
								validMove = true;
							}
						}
						else if ((!piecePresent(e.getX(), (e.getY())))) {
							validMove = true;
						}
					}
					//Checking for opponent
					else if ((piecePresent(e.getX(), e.getY())) && (xMovement == yMovement) && (xMovement == 1) && (startY < landingY)) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
							if (startY == 6) {
								success = true;
							}
						}
					}
				}
				else if ((startX - 1 >= 0) || (startX + 1 <= 7)) {
					if ((piecePresent(e.getX(), e.getY())) && (xMovement == yMovement) && (xMovement == 1)) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
							if (startY == 6) {
								success = true;
							}
						}
					}
					else if (!piecePresent(e.getX(), (e.getY()))) {
						if (((xMovement == 0)) && ((e.getY() / 75) - startY) == 1) {
							if (startY == 6) {
								success = true;
							}
							validMove = true;
						}
					}
				}
			}//End White Pawn
			// Knight
			else if (pieceName.contains("Knight")) {
				if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || landingY > 7)) {
					validMove = false;
				} else {
					// L shaped movements for the knight
					if (((landingX == startX + 1) && (landingY == startY + 2)) || ((landingX == startX - 1) && (landingY == startY + 2)) || ((landingX ==
							startX + 2) && (landingY == startY + 1)) || ((landingX == startX - 2) && (landingY == startY + 1)) || ((landingX == startX + 1) &&
							(landingY == startY - 2)) || ((landingX == startX - 1) && (landingY == startY - 2)) || ((landingX == startX + 2) && (landingY ==
							startY - 1)) || ((landingX == startX - 2) && (landingY == startY - 1))) {
						if (piecePresent(e.getX(), (e.getY()))) {
							//Checking if you can take an opponent piece
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
								}
								else {
									validMove = false;
								}
							}
							else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if(getPieceName(e.getX(), e.getY()).contains("King")){
										winner = "Black is the winner";
									}
								}
								else {
									validMove = false;
								}
							}
						}
						else {
							validMove = true;
						}
					}
					else {
						validMove = false;
					}
				}
			}//End Knight
			//Bishop or "Bishup"
			else if (pieceName.contains("Bishup")) {
				//Checking if there is a piece in the bishops path
				Boolean inTheWay = false;
				int distance = Math.abs(startX - landingX);
				if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
					validMove = false;
				} else {
					validMove = true;
					// Bishops diagonal movements
					if (Math.abs(startX - landingX) == Math.abs(startY - landingY)) {
						if ((startX - landingX < 0) && (startY - landingY < 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX > 0) && (startY - landingY > 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
									inTheWay = true;
								}
							}
						}

						if (inTheWay) {
							validMove = false;
						} else {
							if (piecePresent(e.getX(), (e.getY()))) {
								if (pieceName.contains("White")) {
									if (checkWhiteOponent(e.getX(), e.getY())) {
										validMove = true;
									} else {
										validMove = false;
									}
								} else {
									if (checkBlackOponent(e.getX(), e.getY())) {
										validMove = true;
										if(getPieceName(e.getX(), e.getY()).contains("King")){
											winner = "Black is the winner";
										}
									}
									else {
										validMove = false;
									}
								}
							}
							else {
								validMove = true;
							}
						}
					}
					else {
						validMove = false;
					}
				}
			}//End Bishop
			//Rook
			else if (pieceName.contains("Rook")) {
				Boolean intheway = false;
				if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
					validMove = false;
				} else {
					//Rooks linear movements
					if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)) || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
						if (Math.abs(startX - landingX) != 0) {
							int xMovementRook = Math.abs(startX - landingX);
							if (startX - landingX > 0) {
								for (int i = 0; i < xMovement; i++) {
									if (piecePresent(initialX - (i * 75), e.getY())) {
										intheway = true;
										break;
									} else {
										intheway = false;
									}
								}
							} else {
								for (int i = 0; i < xMovement; i++) {
									if (piecePresent(initialX + (i * 75), e.getY())) {
										intheway = true;
										break;
									} else {
										intheway = false;
									}
								}
							}
						} else {
							int yMovementRook = Math.abs(startY - landingY);
							if (startY - landingY > 0) {
								for (int i = 0; i < yMovement; i++) {
									if (piecePresent(e.getX(), initialY - (i * 75))) {
										intheway = true;
										break;
									} else {
										intheway = false;
									}
								}
							} else {
								for (int i = 0; i < yMovement; i++) {
									if (piecePresent(e.getX(), initialY + (i * 75))) {
										intheway = true;
										break;
									} else {
										intheway = false;
									}
								}
							}
						}

						if (intheway) {
							validMove = false;
						} else {
							if (piecePresent(e.getX(), (e.getY()))) {
								if (pieceName.contains("White")) {
									if (checkWhiteOponent(e.getX(), e.getY())) {
										validMove = true;
									} else {
										validMove = false;
									}
								} else {
									if (checkBlackOponent(e.getX(), e.getY())) {
										validMove = true;
										if(getPieceName(e.getX(), e.getY()).contains("King")){
											winner = "Black is the winner";
										}
									} else {
										validMove = false;
									}
								}
							} else {
								validMove = true;
							}
						}
					} else {
						validMove = false;
					}
				}
			}//End Rook
			//Queen
			else if (pieceName.contains("Queen")) {
				Boolean inTheWay = false;
				Boolean intheway = false;
				int distance = Math.abs(startX - landingX);
				if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
					validMove = false;
				}
				//Queens movements are a combination of rook and bishop
				else if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)) || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
					if (Math.abs(startX - landingX) != 0) {
						int xMovementRook = Math.abs(startX - landingX);
						if (startX - landingX > 0) {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX - (i * 75), e.getY())) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						} else {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX + (i * 75), e.getY())) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						}
					} else {
						int yMovementRook = Math.abs(startY - landingY);
						if (startY - landingY > 0) {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY - (i * 75))) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						} else {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY + (i * 75))) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						}
					}

					if (intheway) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), (e.getY()))) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									if(getPieceName(e.getX(), e.getY()).contains("King")){
										winner = "Black is the winner";
									}
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}
				} else {
					validMove = true;
					if (Math.abs(startX - landingX) == Math.abs(startY - landingY)) {
						if ((startX - landingX < 0) && (startY - landingY < 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX > 0) && (startY - landingY > 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
									inTheWay = true;
								}
							}
						} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
							for (int i = 0; i < distance; i++) {
								if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
									inTheWay = true;
								}
							}
						}

						if (inTheWay) {
							validMove = false;
						} else {
							if (piecePresent(e.getX(), (e.getY()))) {
								if (pieceName.contains("White")) {
									if (checkWhiteOponent(e.getX(), e.getY())) {
										validMove = true;
									} else {
										validMove = false;
									}
								} else {
									if (checkBlackOponent(e.getX(), e.getY())) {
										validMove = true;
										if(getPieceName(e.getX(), e.getY()).contains("King")){
											winner = "Black is the winner";
										}
									} else {
										validMove = false;
									}
								}
							} else {
								validMove = true;
							}

						}
					} else {
						validMove = false;
					}

				}

			}//End King
			//King
			else if (pieceName.contains("King")) {
				if ((xMovement == 0) && (yMovement == 0)) {
					validMove = false;
				}
				else if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
					validMove = false;
				}
				else if ((xMovement > 1) || (yMovement > 1)) {
					validMove = false;
				}
				//Invalid move if the King tries to move within one square of the enemy king
				else if ((getPieceName((e.getX() + 75), e.getY()).contains("King")) || (getPieceName((e.getX() - 75), e.getY()).contains("King")) ||
						(getPieceName((e.getX()), (e.getY() + 75)).contains("King")) || (getPieceName((e.getX()), (e.getY() - 75)).contains("King")) ||
						(getPieceName((e.getX() + 75), (e.getY() + 75)).contains("King")) || (getPieceName((e.getX() - 75), (e.getY() + 75)).contains("King")) ||
						(getPieceName((e.getX() + 75), (e.getY() - 75)).contains("King")) || (getPieceName((e.getX() - 75), (e.getY() - 75)).contains("King"))) {
					validMove = false;
				}
				else if (piecePresent(e.getX(), e.getY())) {
					if (pieceName.contains("White")) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
						}
					}
					else if (checkBlackOponent(e.getX(), e.getY())) {
						validMove = true;
					}
				}
				else {
					validMove = true;
				}
			}//End King
			//Black Pawn
			else if (pieceName.equals("BlackPawn")) {
				// Checking for 1 or 2 space move
				if ((startY == 6) && (startX == landingX) && (((startY - landingY) == 1) || (startY - landingY) == 2)) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
					} else {
						validMove = false;
					}

				} else if ((Math.abs(startX - landingX) == 1) && (((startY - landingY) == 1))) {
					if (piecePresent(e.getX(), e.getY())) {
						//Checking for opponent
						if (checkBlackOponent(e.getX(), e.getY())) {
							validMove = true;
							if(getPieceName(e.getX(), e.getY()).contains("King")){
								winner = "Black is the winner";
							}
							if (landingY == 0) {
								// If progression is true the black pawn changes to a black queen
								progression = true;
							}
						} else {
							validMove = false;
						}
					} else {
						validMove = false;
					}
				}
				/* Checking if the piece is only moving 1 space after it's first turn*/
				else if ((startY != 6) && ((startX == landingX) && (((startY - landingY) == 1)))) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
						if (landingY == 0) {
							progression = true;
						}
					} else {
						validMove = false;
					}
				} else {
					validMove = false;
				}
			}//End Black Pawn
			// Valid Moves
		}
		if(!validMove){		
			int location=0;
			if(startY ==0){
				location = startX;
			}
			else{
				location  = (startY*8)+startX;
			}
			String pieceLocation = pieceName+".png"; 
			pieces = new JLabel( new ImageIcon(pieceLocation) );
			panels = (JPanel)chessBoard.getComponent(location);
		    panels.add(pieces);			
		}
		//Showing Valid Move
		else{
			if(white2Move){
				white2Move = false;
			}
			else{
				white2Move = true;
			}
			//Progression for changing black pawn to black queen
			if (progression) {
				int location = 0 + (e.getX() / 75);
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				} else {
					Container parent = (Container) c;
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				}
				if(winner !=null){
					JOptionPane.showMessageDialog(null, winner);
					System.exit(0);
				}
			}
			//Success for changing white pawn to white queen
			else if(success){
				// Success for changing white pawn to white queen
				int location = 56 + (e.getX()/75);
				if (c instanceof JLabel){
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
					parent.add(pieces);
				}
				else{
					Container parent = (Container)c;
					pieces = new JLabel( new ImageIcon("WhiteQueen.png") );
					parent = (JPanel)chessBoard.getComponent(location);
					parent.add(pieces);
				}
			}
			else{
				if (c instanceof JLabel){
	            	Container parent = c.getParent();
	            	parent.remove(0);
	            	parent.add( chessPiece );
	        	}
	        	else {
	            	Container parent = (Container)c;
	            	parent.add( chessPiece );
	        	}
	    		chessPiece.setVisible(true);
				if(winner !=null){
					JOptionPane.showMessageDialog(null, winner);
					System.exit(0);
				}
			}
		}
		makeAIMove();
    }
 	//Other methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e) {}
	//Main Method
    public static void main(String[] args) {
        JFrame frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
		Object[] AIOptions = {"Random Moves","Best Next Move"};
		//Giving Player option of which AI to play against
		int i = JOptionPane.showOptionDialog(frame,"Choose your AI opponent","Chess Project", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,AIOptions,AIOptions[1]);
		AIPlayStyle = i;
	}
}


