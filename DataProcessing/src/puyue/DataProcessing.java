package puyue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataProcessing {

public static void main(String [] args) throws IOException {  

		List<String> list_TIME0 = new ArrayList<String>();
		 List<String> list_ID0 = new ArrayList<String>();
		 int MARK=0;
		 try {
	 
     
       FileReader fr = new FileReader("C://Users/PU YUE/Desktop/in.csv");
       BufferedReader br = new BufferedReader(fr);

       
       String line;
       StringTokenizer token;
       while ((line = br.readLine()) != null) {

           token = new StringTokenizer(line, ",");

           while (token.hasMoreTokens()) {
              
               list_ID0.add(token.nextToken());
               
               list_TIME0.add(token.nextToken());
           }
          
       }
       br.close();

   } catch (IOException ex) {
     
   	   ex.printStackTrace();
   }
		 
 
		
		 int size_TIME0=list_TIME0.size(); 
		 String[] TIME = (String[])list_TIME0.toArray(new String[size_TIME0]); 
		 int size_ID0=list_ID0.size(); 
		 String[] ID = (String[])list_ID0.toArray(new String[size_ID0]); 
		 


List<String> list_TIME = new ArrayList<String>(); 
 for (int ii=0; ii<TIME.length; ii++) {  
     if(!list_TIME.contains(TIME[ii])) {  
         list_TIME.add(TIME[ii]);  
     }  
 }  

 int size_TIME=list_TIME.size(); 
 String[] array_TIME = (String[])list_TIME.toArray(new String[size_TIME]);  


     List<String> list_ID = new ArrayList<String>(); 
     for (int iii=0; iii<ID.length; iii++) {  
         if(!list_ID.contains(ID[iii])) {  
             list_ID.add(ID[iii]);  
         }  
     }    

     int size_ID=list_ID.size(); 
     String[] array_ID = (String[])list_ID.toArray(new String[size_ID]);  
     
 
	int[][]  ALL = new int[array_TIME.length][array_ID.length+2];
	
	System.out.println("###表示数组高"+array_TIME.length);
	System.out.println("###表示数组宽"+array_ID.length);

	//ID
	System.out.print("横坐标");
	for(int m=0;m<array_ID.length;m++){
		System.out.print(array_ID[m]+" ");
		}
	System.out.println();
	//TIME
	System.out.print("竖坐标");
	for(int m=0;m<array_TIME.length;m++){
		System.out.print(array_TIME[m]+" ");
		}
	System.out.println();
	//key in ALL
	
	for (int y = 0; y < array_ID.length; y++)
	{
		for (int x = 0; x < array_TIME.length; x++)
		{
			for (int q = 0; q < TIME.length; q++)
			{
				if (array_TIME[x].equals(TIME[q]))
				{
					if((array_ID[y].equals(ID[q])))
					{
					System.out.println("所有配对情况"+"x="+x+"y="+y+"q="+q);
					ALL[x][y] = 1;
					}
				}
				
			}
		}
	}
	
	
	ALL[0][array_ID.length+1]=00;
	ALL[0][array_ID.length]=ALL[0][0]+ALL[0][1];
	for(int i=1;i<ALL.length;i++){
		ALL[i][array_ID.length]=ALL[i][1]+ALL[i][2];
		ALL[i][array_ID.length+1]=Math.abs(ALL[i-1][1]-ALL[i][1])+Math.abs(ALL[i-1][2]-ALL[i][2]);
		
	}
	

	for (int i = 0; i < ALL.length; i++)
	{
		for (int j = 0; j < ALL[0].length; j++)
		{
			
			System.out.print(ALL[i][j]+ "	");
		}
		
		System.out.println();

	}
	


	int i,j;
	try
    {
      FileWriter writer = new FileWriter("C:/Users/PU YUE/Desktop/out.csv");   
      
         for(i = 0; i < ALL.length; )
         {
            for (j=0; j<ALL[0].length; j++)
             {
                 writer.append(String.valueOf(ALL[i][j]));
                 writer.append(',');
                 
             }
              
            
               writer.append('\n');
               i++;
               writer.flush();
         }
         writer.close();
      }        
    catch(Exception e)
    {
      e.printStackTrace();
    }
	

}

	private static int sqrt(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
