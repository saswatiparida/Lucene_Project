package com.test.lucene;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class DatabaseSearcher {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, ParseException {
        String indexDir = "C:\\Saswati_Lucene\\Index Directory"; // directory where the index is stored
        
        Directory indexDirectory = 
   	         FSDirectory.open(new File(indexDir));
       
        IndexSearcher searcher = new IndexSearcher(indexDirectory);
        
        QueryParser parser = new QueryParser(Version.LUCENE_36,"Name",
   	         new StandardAnalyzer(Version.LUCENE_36));
        Query query = parser.parse("The Blue Whale"); // the text you want to search for
        
        long startTime = System.currentTimeMillis();
	      TopDocs hits = searcher.search(query, 10);
	      long endTime = System.currentTimeMillis();
	   
	      System.out.println(hits.totalHits +
	         " documents found. Time :" + (endTime - startTime));
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.doc(scoreDoc.doc);
	            System.out.println("File: "
	            + doc.get(indexDir));
        searcher.close();
    }
}
}
