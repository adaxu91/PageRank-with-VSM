package pageRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class anchorText {
	public static void anchorReadFile() throws IOException{
		File[] files=new File("./anchor").listFiles();
		FileReader fr=null;
		BufferedReader br=null;
		PageRank pr=new PageRank();
		
		for(File f:files){
			String doc[] = f.getName().split("_");
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			String read="";
			int pos_x=Integer.parseInt(doc[0]);
			 while((read=br.readLine())!=null){
				 String []results=read.split("\t");
				 if(results[1].startsWith("#")==false){
					 int pos_y=pr.compare(results[1]);
					 if(pos_y!=-1)
						 pr.linkMatrix(pos_x, pos_y);
					 else
						 continue;
				 }
				 else{
					 int pos_y=pos_x;
					 pr.linkMatrix(pos_x, pos_y);
				 }
			 }
		}
	}
}
