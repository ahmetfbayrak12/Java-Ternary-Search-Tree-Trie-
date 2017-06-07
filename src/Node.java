import java.util.ArrayList;

public class Node 
{
	    String data;
	    boolean isEnd;
	    Node left, middle, right;
	    private ArrayList<Integer> index;				// For keep indexes of words.
	    public Node(String data)
	    {
	        this.data = data;
	        this.isEnd = false;
	        this.left = null;
	        this.middle = null;
	        this.right = null;
	        this.index = new ArrayList<Integer>();
	    }     
	    
	    public void setIndex(ArrayList<Integer> index) 
	    {
			this.index = index;
		}

		public ArrayList<Integer> getIndex()
	    {
	    	return index;
	    }
		
}
