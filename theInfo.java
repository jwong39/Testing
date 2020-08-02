import java.io.Serializable;

public class theInfo implements Serializable{
	int size;
	
	int p1Wins;
	int p2Wins;
	
	// Since gameInfo different per client, the client wont
	// Know what number client he is lol, only the server knows
	int Guess;
	int GuessTotal;
	
	int p1Guess;
	int p1GuessTotal;
	boolean p1Answered;
	
	int p2Guess;
	int p2GuessTotal;
	boolean p2Answered;
	
	int sum;
	
	String message;
	
	//default constructor
	public theInfo() {
		size = 0;
		p1Wins = 0;
		p2Wins = 0;
		
		Guess = 0;
		GuessTotal = 0;
		
		p1Guess = 0;
		p1GuessTotal = 0;
		p1Answered = false;
		
		p2Guess = 0;
		p2GuessTotal = 0;
		p2Answered = false;
		
		sum = 0;
		message = " ";
	}
	// copy constructor
	public theInfo(theInfo other) {
		size = other.size;
		
		p1Wins = other.p1Wins;
		p2Wins = other.p2Wins;
		
		Guess = other.Guess;
		GuessTotal = other.GuessTotal;
		
		p1Guess = other.p1Guess;
		p1GuessTotal = other.p1GuessTotal;
		p1Answered = other.p1Answered;
		
		p2Guess = other.p2Guess;
		p2GuessTotal = other.p2GuessTotal;
		p2Answered = other.p2Answered;
		
		sum = other.sum;
		message = other.message;
	}
}
