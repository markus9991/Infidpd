import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	
	public static String[] readTxt(int lines,String path) throws FileNotFoundException{
		String [] strings=new String[lines];
		int count=0;
		Scanner scan=new Scanner(new File(path));
		while (scan.hasNextLine()) {
			strings[count] =scan.nextLine();
			count++;
		}
		return strings;
	}

}
