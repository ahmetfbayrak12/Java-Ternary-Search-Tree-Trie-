import java.util.ArrayList;


class TernarySearchTree
{
	private Node root;
	public ArrayList<Node> match_words_node;
	public int count = 1;
	Helper helper = new Helper();

	/** Constructor **/
	public TernarySearchTree()
	{
		root = null;
		match_words_node = new ArrayList<>();
	}

	/** Check whether is empty **/
	public boolean isEmpty()
	{
		return root == null;
	}

	/** Clear function	**/
	public void makeEmpty()
	{
		root = null;
	}

	/** Insert a word function **/
	public void insert(String word)
	{
		root = insert(root, word, 0);
		count++;						// For index of words.
	}

	/** Insert a word helper function **/
	public Node insert(Node r, String word, int digit)
	{
		if (r == null)
			r = new Node(String.valueOf(word.charAt(digit)));

		if (word.charAt(digit) < r.data.charAt(0))
			r.left = insert(r.left, word, digit);

		else if (word.charAt(digit) > r.data.charAt(0))
			r.right = insert(r.right, word, digit);

		else
		{
			if (digit + 1 < word.length())
				r.middle = insert(r.middle, word, digit + 1);
			else
			{
				r.isEnd = true;
				r.getIndex().add(count);
			}
		}
		return r;
	}

	/** Search function for -w type (word)	**/
	public void search_string_word(String search_word)
	{
		String[] search_word_all = search_word.split(" ");
		if(search_word_all.length == 1)						// If search word is a single word.
			search_string_word_single(search_word);
		else												// If search word is not a single word.
			search_string_word_multiple(search_word);
	}

	/**	Search function for -W type (phrase)	**/
	public void search_string_phrase(String search_word, String fileName, String search_word_original, int count_file, int countTempp)
	{
		String[] search_word_all = search_word.split(" ");
		if(search_word_all.length == 1)						// If search word is a single word.
			search_string_word_single(search_word);
		else												// If search word is not a single word.
			search_string_phrase_multiple(search_word, fileName, search_word_original, count_file, countTempp);
	}

	/** Search function for -w type and single word **/
	public boolean search_string_word_single(String search_word)
	{
		return search_string_word_single(root, search_word, 0);
	}

	/** Helper function for search -w type and single word **/
	private boolean search_string_word_single(Node r, String search_word, int digit)
	{
		if (r == null)
		{
			return false;
		}

		if (search_word.charAt(digit) < r.data.charAt(0))
			return search_string_word_single(r.left, search_word, digit);
		else if (search_word.charAt(digit) > r.data.charAt(0))
			return search_string_word_single(r.right, search_word, digit);
		else
		{
			if (r.isEnd && digit == search_word.length() - 1)
			{
				Node abc = new Node(search_word);				// If program finds the word which match with the search word, create new node.
				abc.setIndex(r.getIndex());						// Take indexes of the word.
				match_words_node.add(abc);						// Add them to the arrayList.
				return true;
			}
			else if (digit == search_word.length() - 1)
				return false;
			else
				return search_string_word_single(r.middle, search_word, digit + 1);
		}        
	}

	/**	Search function for -w type and multiple words	**/
	public void search_string_word_multiple(String search_word)
	{
		search_string_word_multiple(root, search_word, 0);
	}

	/** Helper function for search -w type and multiple words **/	
	private void search_string_word_multiple(Node r, String search_word, int digit)
	{
		String[] search_word_all = search_word.split(" ");
		for(int i = 0 ; i < search_word_all.length ; i++)
		{
			if (r == null)
				return;
			if (search_word_all[i].charAt(digit) < r.data.charAt(0))
				search_string_word_multiple(r.left, search_word_all[i], digit);
			else if (search_word_all[i].charAt(digit) > r.data.charAt(0))
				search_string_word_multiple(r.right, search_word_all[i], digit);
			else
			{
				if (r.isEnd && digit == search_word_all[i].length() - 1)
				{
					Node abc = new Node(search_word_all[i]);
					abc.setIndex(r.getIndex());
					match_words_node.add(abc);
				}
				else if (digit == search_word_all[i].length() - 1)
					//					System.out.println("false");
					continue;
				else
					search_string_word_multiple(r.middle, search_word_all[i], digit + 1);
			}  
		}
	}

	/**	Search function for -W type and multiple words	**/
	public void search_string_phrase_multiple(String search_word, String fileName, String search_word_original, int count_file, int countTempp)
	{
		search_string_word_multiple(search_word);
		search_string_phrase_multiple(root, search_word, 0, fileName, search_word_original, count_file, countTempp);
		if(countTempp == count_file)
			System.out.println("No result found;" + search_word_original);
	}

	/** Helper function for search -W type and multiple words **/
	private void search_string_phrase_multiple(Node r, String search_word, int digit, String fileName, String search_word_original, int count_file, int countTempp)
	{
		int search_word_index = 0;
		int first_index = 0;
		
		if(match_words_node.size() == 0)			// If the search word is not in the text
		{
			countTempp++;
		}
		else
		{
			for(int i = 0; i < match_words_node.get(0).getIndex().size(); i++)
			{
				first_index = match_words_node.get(0).getIndex().get(i);
				for(int j = 1 ; j < match_words_node.size(); j++)
				{
					first_index++;
					if(match_words_node.get(j).getIndex().contains(first_index))
					{
						search_word_index = first_index - match_words_node.size() + 1;
					}
				}
			}
		}

		/**	Print the result	*/
		if(search_word_index == 0)
			return;
		else
			System.out.println(fileName + ";" +search_word_original + ";" + search_word_index);
	}
}