package pageRankVSM;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class VSM {
	public static Analyzer analyzer =  new EnglishAnalyzer(Version.LUCENE_46); 
	public static Directory directory = new RAMDirectory(); // store the index in main memory
	public static IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,analyzer);
	public static QueryParser parser = new QueryParser(Version.LUCENE_46, "content", analyzer);
	
	public double[] getSurf() throws IOException, ParseException{
		IndexWriter writer=new IndexWriter(directory,config);
		double[]surf=new double[10000];
		
		FieldType ft=new FieldType();
		ft.setTokenized(true);
		ft.setIndexed(true);
		ft.setStored(false);
		ft.setIndexOptions(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		
		File[] files = new File("./contents").listFiles();
		for(File f:files){
			Document doc = new Document();
			String docID=f.getName();
			doc.add(new Field("content",new FileReader(f),ft));
			doc.add(new StringField("docID",docID,Field.Store.YES));
			writer.addDocument(doc);
		}
		writer.close();
		
		IndexReader reader = DirectoryReader.open(directory);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    String queryStr="the relationship between China and Hong Kong";		
	    surf=queryProcess(searcher,queryStr);
		
		reader.close();
		directory.close();
		
		return surf;
	}
	
	private double[] queryProcess(IndexSearcher searcher, String queryStr) throws ParseException, IOException{
		Query query=parser.parse(queryStr);
		TopDocs hits = searcher.search(query, 10000);
		double[]surf=new double[10000];
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			String id=doc.get("docID");
			surf[Integer.parseInt(id)]=scoreDoc.score;
		}
		return surf;
	}
}
