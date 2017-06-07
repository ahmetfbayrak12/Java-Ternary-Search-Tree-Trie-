package fouryy4;

import java.io.File;
import java.util.Scanner;

public class Exp4 
{
	public static void main(String[] args)
	{
		Scanner scn = new Scanner(System.in);
		Helper helper = new Helper();

		String filePath = args[0];							// Take directory path as an argument.
		File dir = helper.indexed_instance(filePath);

		while(true)
		{
			System.out.println("search <type> <key>");			// Take command from console.
			String inputAll = scn.nextLine();

			if(inputAll.equals("q"))							// Exit if user types q
			{
				System.out.println("Program is ended.");
				break;
			}
			else
				helper.find_indexes_instance(dir, inputAll);
		}
	}
}
