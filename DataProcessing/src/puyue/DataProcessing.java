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

	//��������
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
			 
			//time string ת��int	
			 List<Integer> int_TIME0 = new ArrayList<Integer>();
			 for(String str : list_TIME0) {
				  int_TIME0.add(Integer.parseInt(str));
				}
			 
			 //10����
			 int a=0;
			 List<Integer> int_TIME = new ArrayList<Integer>();
			 for(int i=0;i<int_TIME0.size();i++){	
				int_TIME.add(((int_TIME0.get(i)/600000)*600000)) ;
			 } 
			 
			 //IDΨһ��
			 List<String> String_ID = new ArrayList<String>();//����һ������Ψһ��  Ԫ���ݲ���

			 for (int i=0; i<list_ID0.size(); i++) {  
			     if(!String_ID.contains(list_ID0.get(i))) {  
			    	 String_ID.add(list_ID0.get(i));  
			     }  
			 }  
			
			 
			 List<Integer> NUM = new ArrayList<Integer>();//����ÿһ�������ݵ�10����һ�����ٸ�����
			 int w=0;
			 int n=0;
			 for(int i = 0; i < int_TIME.size(); ){
				 while((int_TIME.get(i)).equals(0+600000*n)){
					 w++;
					 i++;
					 if(i==int_TIME.size()){break;}
			}
				 NUM.add(w);
				 w=0;
				 n++;
			 }
			 
			 List<Integer> NUM_SUM = new ArrayList<Integer>();//ǰn��ͣ�����֮���λ��
			 NUM_SUM.add(0);//���һλΪ��֮���n+1
			 int sum=0;
			 for(int i=0;i<NUM.size();i++){				
					sum+=NUM.get(i);
					NUM_SUM.add(sum);
				}

			 
			 for (int j = 0; j < NUM.size(); j++){System.out.print(NUM.get(j)+ " ");}
			 System.out.println();
			 for (int j = 0; j < NUM_SUM.size(); j++){System.out.print(NUM_SUM.get(j)+ " ");}
			 System.out.println();
			 
			 for (int i = 0; i < int_TIME.size(); i++){
				 System.out.print(list_ID0.get(i)+ " ");
				 System.out.println(int_TIME.get(i)+ " ");
			 }
			 
			 
			 
			 int m=0;
			 int[][]  ALL = new int[int_TIME.size()][String_ID.size()];
			 //д��
			 for (int y = 0; y < int_TIME.size(); y++) 
				{
					for (int q = NUM_SUM.get(m); q < NUM_SUM.get(m+1); q++)
					{
						for (int x = 0; x < String_ID.size(); x++)
						{
							if(int_TIME.get(q).equals(0+600000*m)&&String_ID.get(x).equals(list_ID0.get(q))){
									ALL[y][x] = 1;
							}
						}
					}m++;
				}
			 
			
			 System.out.println("list_TIME0.size()"+list_TIME0.size());
			 System.out.println("list_ID0.size()"+list_ID0.size());
			 System.out.println("String_ID.size()"+String_ID.size());
			 System.out.println("int_TIME.size()"+int_TIME.size());
			 
			 
			//��ӡ ALL������飬
				for (int i = 0; i < ALL.length; i++)//����
				{
					for (int j = 0; j < ALL[0].length; j++)//����
					{
						System.out.print(ALL[i][j]+ "	");
					}
					
					System.out.println();

				}
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				
				 int[][]  TIME_add = new int[int_TIME.size()][2];
		    	 int nn=0;//��һ��д��ʱ��
				 for(int i=0;i<int_TIME.size();i++){
					 TIME_add[i][0]=0+600000*nn;
					 nn++;
				 }
				 //����
				 for(int i=0;i<int_TIME.size();i++){
					 for(int j=0;j<String_ID.size();j++){
						 TIME_add[i][1]=ALL[i][j]+TIME_add[i][1];
					 }
				 }
				//��ӡ TIME_add������飬
					for (int i = 0; i < TIME_add.length; i++)//����
					{
						for (int j = 0; j < TIME_add[0].length; j++)//����
						{
							System.out.print(TIME_add[i][j]+ "	");
						}
						
						System.out.println();

					}
					System.out.println("##########################");
				 int[][]  TIME_change = new int[int_TIME.size()][2];
				 int pp=0;//��һ��д��ʱ��
				 for(int i=0;i<int_TIME.size();i++){
					 TIME_change[i][0]=0+600000*pp;
					 pp++;
				 }
				 //����
				TIME_change[0][1]=0;
				 for(int i=1;i<int_TIME.size();i++){
					 for(int j=0;j<String_ID.size();j++){
						 TIME_change[i][1]=Math.abs(ALL[i-1][j]-ALL[i][j])+TIME_change[i][1];
					 }
				 }
							 
					//��ӡ TIME_change������飬
					for (int i = 0; i < TIME_change.length; i++)//����
					{
						for (int j = 0; j < TIME_change[0].length; j++)//����
						{
							System.out.print(TIME_change[i][j]+ "	");
						}
						
						System.out.println();

					}
					//����
					int i,j;
					try
				    {
				      FileWriter writer = new FileWriter("C:/Users/PU YUE/Desktop/TIME_add.csv");   
				         for(i = 0; i < TIME_add.length; )
				         {
				            for (j=0; j<TIME_add[0].length; j++)
				             {
				                 writer.append(String.valueOf(TIME_add[i][j]));//int���string���ܱ���csv
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
					
					//����
					try
				    {
				      FileWriter writer = new FileWriter("C:/Users/PU YUE/Desktop/TIME_change.csv");   
				      
				         for(i = 0; i <TIME_change.length; )
				         {
				            for (j=0; j<TIME_change[0].length; j++)
				             {
				                 writer.append(String.valueOf(TIME_change[i][j]));//int���string���ܱ���csv
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
}