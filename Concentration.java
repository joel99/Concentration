//Team Fern - Joel Ye, Bayle Smith-Salzberg
//APCS1 pd10
//HW36 -- Some Folks Call it a Memory
//2015-11-23

//import cs1.Keyboard;  //if comfortable with Scanner, you may comment this out
import java.util.Scanner;
		
public class Concentration {

    //instance variables
    private static Tile[][] _board;     //storage for 4x4 grid of Tiles.
    private int _numberFaceUp;   //count of Tiles with faces visible
    private String[] _words;     //list of unique Strings used for Tile vals
    private static int _numRows = 4;
    private int gamePhase = 1;
    private int score = 1008;

    //insert constructor and methods here

    		
    public Concentration(){
	_board = new Tile[4][4];
	_words = new String[] {"a","b","c","d","e","f","g","h"};

	for (int i = 0; i < _board.length; i++)
	    for (int j = 0; j < _board[i].length; j++)
			_board[i][j] = new Tile(_words[(i * _board[0].length +j) % 8]);
	shuffle();
    }

    
    public static String print() {
	String ret = "";
	ret += "Col: \t 0   1   2   3\n";
	for (int i = 0; i< _board.length; i++){
	    ret += "row " + i + ": \t";
	    for (int x = 0; x < _board[i].length;x++){
		ret += _board[i][x] + " ";
		if (x == _board[i].length-1)
		    ret += "\n";
	    }
	}
	System.out.println(ret);
	return ret;
    }
	
    public Tile[][] getBoard(){
		return _board;
    }

    private void swap( int x1, int y1, int x2, int y2 ) {
	Tile temp = getBoard()[x1][y1];
		_board[x1][y1] = getBoard()[x2][y2];
		_board[x2][y2] = temp;			
    }

	//Iterates through array. Swaps current Tile with one at point further down.
    private void shuffle(){
	int len1 = _board.length;
	int len2 = _board[0].length;
	for (int i = 0; i < len1; i++)
	    for (int j = 0; j < len2; j++){
			int rowAdd = (int)(Math.random()*(len1 - i));
			int colNum = 0;
			if (rowAdd == 0)
				colNum = j + (int)(Math.random()*(len2-j));
			else
				colNum = (int)(Math.random()*len2);
			swap (i, j, i + rowAdd,  colNum);
		}
    }	

	public boolean isEndGame(){
		boolean end = true;
		for (int i = 0; i < _board.length; i++)
			for (int j = 0; j < _board[i].length; j++)
				if (!_board[i][j].isFaceUp()){
					end = false;
					return end;}
		return end;
	}
		
    public void play(){
	Scanner in = new Scanner(System.in);
	System.out.println("Welcome to the card swapper! Type 'quit' to quit.");
	int x1 = 0; int y1 = 0; int x2 = 0; int y2 = 0;
	String str = "";
	print();
	while (!isEndGame()){
		try{
		if (gamePhase == 1)
		    System.out.println("Please input row of first card.");
		else if (gamePhase == 2)
		    System.out.println("Please input column of first card.");
		else if (gamePhase == 3)		
		    System.out.println("Please input row of second card.");
		else if (gamePhase == 4)		
		    System.out.println("Please input column of second card.");
		str = in.nextLine();		
		if (str.toLowerCase().equals("quit")){
			score = 0;
		    break; 
		}
		if (gamePhase == 1)
		    x1 = Integer.parseInt(str);
		else if (gamePhase == 2)
		    y1 = Integer.parseInt(str);
		else if (gamePhase == 3)
		    x2 = Integer.parseInt(str);
		else if (gamePhase == 4){
		    y2 = Integer.parseInt(str);
		}
		if (gamePhase == 2){
			if (_board[x1][y1].isFaceUp()){
				System.out.println("That card has already been revealed.");
				gamePhase = 1;}
			else
				gamePhase += 1;
		}
		else if (gamePhase == 4){
			if (_board[x2][y2].isFaceUp()){
				System.out.println("That card has already been revealed.");
				gamePhase = 3;}
			else if (x1 == x2 && y1 == y2){
				System.out.println("That card was your first selection.");
				gamePhase = 3;
			}
			else
				gamePhase += 1;
		}
		else
			gamePhase += 1;
		}
	    catch(Exception e){
		gamePhase = 0;
		System.out.println("Try again");
	    }
		
		if (gamePhase == 5){
			_board[x1][y1].flip();
			_board[x2][y2].flip();
			print();
			if(_board[x1][y1].equals(_board[x2][y2]))
				System.out.println("Match!");
			else{
				System.out.println("No match.");
				_board[x1][y1].flip();
				_board[x2][y2].flip();
			}
				
			score -= 1;
			gamePhase = 1;
		}
		if (gamePhase > 5 || gamePhase <= 0){
			System.out.println("Game has crashed. :(");
			score = 0;
			break;
		}

	}
	System.out.println("The game is over.");
	System.out.println("Your score is " + score + ".");	
    }


    //DO NOT MODIFY main()
    public static void main(String[] args){			   
	Concentration game = new Concentration();
	game.play(); 
    }

}//end class Concentration

