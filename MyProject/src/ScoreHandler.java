import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScoreHandler {

	private int currentScore;
	private int highScore;
	
	public ScoreHandler() throws FileNotFoundException{
		this.currentScore = 0;
		this.highScore = ReadHighScore();
	}
	
	public void SaveHighScore() throws FileNotFoundException{
		PrintWriter p = new PrintWriter("HighScore.txt");
		p.println(this.currentScore);
		p.close();
	}
	
	public int ReadHighScore() throws FileNotFoundException{
		Scanner file = new Scanner(new File("HighScore.txt"));
		
		this.highScore = Integer.parseInt(file.nextLine());
		
		file.close();
		return this.highScore;
	}
	
	public void addCurrentScore(int points){
		this.currentScore = this.currentScore + points; 
	}
	
	public void setCurrentScore(int score){
		this.currentScore = score;
	}
	
	public int getScore(){
		return this.currentScore;
	}
	
	public int getHighScore(){
		return this.highScore;
	}
	
	public boolean CheckIfNewHighScore() throws FileNotFoundException{
		if(this.currentScore>this.highScore){
			SaveHighScore();
			return true;
		}
		return false;
	}
	
}
