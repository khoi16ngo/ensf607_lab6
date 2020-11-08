
/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: STUDENT(S) NAME
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */
    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several reords.
         * Loop should terminate when an EOFException is thrown.
         */
        
        try
        {
            while ( true )
            {
                record = (MusicRecord)input.readObject();

            	int year = record.getYear();
    			System.out.print(year + "  ");       // echo data read from text file
                
    			String songName = record.getSongName();
    			System.out.print(songName + "  ");  // echo data read from text file
                
    			String singerName = record.getSingerName();
    			System.out.print(singerName + "  "); // echo data read from text file
                
    			double price = record.getPurchasePrice();
    			System.out.println(price + "  ");    // echo data read from text file
                
            }   // END OF WHILE
        }
                // ADD NECESSARY catch CLAUSES HERE
        catch(ClassNotFoundException | IOException e) {
        	System.err.println( "Error reading file.");
        }
        
        try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }           // END OF METHOD 
    
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("allSongs.ser");
    }
}
