package pageRank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

public class PageRank {
	public static double[]vec=new double[10000]; //probability vector
	public int [][]linkMatrix=new int[10000][10000]; // Link matrix
	public static double [][]probMatrix=new double[10000][10000]; // Transition probability matrix
	public static double[]surf=new double[10000];
	public static double alpha = 0.1;   //alpha=0,0.1
	public static Map<String,Integer> urlList=new HashMap<String,Integer>();
	
	public static void main(String[] args) throws IOException, ParseException{
		double error;
		int k=1;
		
		for(int i=0;i<10000;i++){
			surf[i]=1/10000.0;
			vec[i]=1/10000.0;
		}

		readFile();
		
		anchorText at=new anchorText();
		at.anchorReadFile();
		
		genProbMatrix();
		
		System.out.println("Start iterating: ");
		do{
			error=pageRank();
			System.out.printf("%2d [%.3f]: ",k++,error);
			for(int i=0;i<10000;i++){
				System.out.print(vec[i]+" ");
			}
			System.out.println();
		}while(error>0.001);
		
		//sort pagerank		
		Map<Integer, Double> rankList = new HashMap<Integer, Double>();
		for (int i = 0; i < 10000; i++) {
			rankList.put(i, vec[i]);
		}

		List<Map.Entry<Integer, Double>> arrayList = new ArrayList<Map.Entry<Integer, Double>>(rankList.entrySet());
		// Sorting
		Collections.sort(arrayList, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,Map.Entry<Integer, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Sorted
		for (int i = 0; i < 20; i++) {
			System.out.println(arrayList.get(i));
		}
	}
	
	public static void readFile() throws IOException{
		FileReader fr=new FileReader("./id2url");
		BufferedReader br=new BufferedReader(fr);
		String read="";
		int urlID=0;
			
		while((read=br.readLine())!=null){
			String []s=read.split(" ");
			urlID=Integer.parseInt(s[0].trim());
			urlList.put(s[1],urlID);
		}
		br.close();
	}
	
	public void linkMatrix(int pos_x,int pos_y){   
		probMatrix[pos_x][pos_y]=1; 
	}
	
	public int compare(String url){
		int id;
		if(urlList.containsKey(url)==true){
			id=urlList.get(url);
		}else 
			id=-1;
		return id;
	}
	
	public static void genProbMatrix(){
		for(int i=0;i<10000;i++){
			int sum=0;
			for (int j=0;j<10000;j++){
				sum+=probMatrix[i][j];
			}
			if(sum!=0){
				for (int j=0;j<10000;j++){
					probMatrix[i][j]=probMatrix[i][j]/sum;
					probMatrix[i][j]*=(1-alpha);     // modifying the matrix for teleporting
					probMatrix[i][j]+=alpha*surf[j];    // modifying the matrix for teleporting
				}
			}else{
				for(int j=0;j<10000;j++){
					probMatrix[i][j]=surf[j];    // modifying the matrix for teleporting
				}
			}
		}
	}
	
	private static double pageRank(){
		double[] newVec=new double[10000];
		
		for (int i=0;i<10000;i++){
			for (int j=0;j<10000;j++){
				newVec[i]+=vec[j]*probMatrix[j][i];
			}
		}
		
		double norm=0;
		double error=0;
		
		for (int i=0;i<10000;i++)
			norm+=newVec[i];		
		for (int i=0;i<10000;i++){
			error+=Math.abs(vec[i]-newVec[i]/norm);
			vec[i]=newVec[i]/norm;
		}		
		return error;
	}
}
