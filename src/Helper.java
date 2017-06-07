package fouryy4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper 
{
	private ArrayList<String> arrayList_node;

	/*	Caller of private find_indexes function.	*/
	public void find_indexes_instance(File dir, String inputAll)
	{
		find_indexes(dir, inputAll);
	}

	/*	Takes input and determine whether search type -w or -W and send them their function	*/
	private void find_indexes(File dir, String inputAll) 
	{
		int count_word=0;
		int count_file=0;
		int count_wordp =0;
		String input[] = inputAll.split(" ");
		String search = input[0];
		String search_type = input[1];					// Take search type whether -w (word) or -W (phrase).
		String search_word = input[2];					// Take the word which user wants to search.
		if(input.length != 3)							// If the search word is not single word then take it.				
			for(int i = 3 ; i < input.length ; i++)
				search_word += " " + input[i];
		
		for (File file : dir.listFiles()) 				// For all text files in the file.
		{
			count_file++;
			Scanner s = null;
			
			TernarySearchTree tst = new TernarySearchTree(); 
			ArrayList<String> arrayList_read = new ArrayList<>();
			ArrayList<Node> arrayList_node = new ArrayList<>();

			try 
			{
				s = new Scanner(file);
				arrayList_read = readFile1(file.toString());					// Read input text.

				createNodes(arrayList_read, arrayList_node);					// Create nodes.

				createTernarySearchTree(tst, arrayList_node);				// Create Ternary Search.

				if(search_type.equals("-w"))									// If search type -w (word)
				{
					tst.search_string_word(search_word.toLowerCase());
					count_word = printResult(file, tst, search_word, count_word);
				}

				else if(search_type.equals("-W"))														// If search type -W (phrase)
				{
					tst.search_string_phrase(search_word.toLowerCase(),file.getName(), search_word, count_file, count_wordp);
					if(input.length < 4)			// Print phrase with multiple words
					{
						printResult(file, tst, search_word, count_wordp);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			arrayList_read.clear();												// Clear all of them.
			arrayList_node.clear();
			tst.match_words_node.clear();
			tst.makeEmpty();
			tst.count = 1;
		}

		/**	Check whether search word is in the files	**/
		if(count_word == count_file)
			System.out.println("No result found;" + search_word);
	}

	/* Caller of private indexed function.	*/
	public File indexed_instance(String filePath)
	{
		return indexed(filePath);
	}

	/**	Search the file and print the files as an indexed	**/
	private File indexed(String filePath) 
	{
		File dir = new File(filePath);
		for (File file : dir.listFiles()) 
			System.out.println(file.getName() + " indexed");
		System.out.println("-----------");
		return dir;
	}
	
	/*	Caller of private printResult function	*/
	public void print_result_instance(File file, TernarySearchTree tst, String search_word, int count_word)
	{
		printResult(file, tst, search_word, count_word);
	}

	/**	Print the output	**/
	private static int printResult(File file, TernarySearchTree tst, String search_word, int count_word) 
	{
		String[] search_word_all = search_word.split(" ");
		if(tst.match_words_node.size() == 0)						// If the search word is not in the text file.
		{
			count_word++;
		}

		else
		{
			for(int i = 0 ; i < tst.match_words_node.size(); i++)
				for(int j = 0 ; j < tst.match_words_node.get(i).getIndex().size() ; j++)
				{
					for(int k = 0 ; k < search_word_all.length ; k++)
					{
						if(search_word_all[k].toLowerCase().equals(tst.match_words_node.get(i).data))
						{
							System.out.println(file.getName() + ";" + search_word_all[k] + ";" + tst.match_words_node.get(i).getIndex().get(j));
						}
					}
				}

		}
		return count_word;
	}

	/**	Getter of the arrayList	**/
	public ArrayList<String> get_arrayList_node()
	{
		return arrayList_node;
	}

	/**	Inserting words and creating Ternary Search Tree	**/
	private static void createTernarySearchTree(TernarySearchTree tst, ArrayList<Node> arrayList_node) 
	{
		for(int i = 0 ; i < arrayList_node.size() ; i++)
			tst.insert(arrayList_node.get(i).data.toLowerCase());
	}

	/**	Creating nodes	**/
	private static void createNodes(ArrayList<String> arrayList_read, ArrayList<Node> arrayList_node) 
	{
		for(int i = 0 ; i < arrayList_read.size() ; i ++)
		{
			Node abc = new Node(arrayList_read.get(i));
			arrayList_node.add(abc);
		}
	}

	/**	Reading File	**/
	private static ArrayList<String> readFile1(String text) 
	{
		ArrayList<String> lineArray = new ArrayList<String>();
		try 
		{
			Scanner scanner = new Scanner(new File(text));
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();

				line = line.replace(",", " ");
				String[] input = line.split("\\s+");
//				String[] input = line.split("\\s+ || \\,+");

				for(int i = 0 ; i < input.length ; i ++)
				{
					lineArray.add(input[i].trim());
				}		 
			}
			scanner.close();
		}
		catch (FileNotFoundException ex) 
		{
			System.out.println("There is no file.");
		}
		return lineArray;
	}
}
