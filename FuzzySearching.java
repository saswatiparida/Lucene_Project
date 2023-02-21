package com.test.lucene;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class FuzzySearching {
	String indexDir = "C:\\Saswati_Lucene\\Index Directory";
	   String dataDir = "C:\\Saswati_Lucene\\Data Directory";
	   Indexer indexer;
	   Searcher searcher;

	   public static void main(String[] args) throws SQLException {
	      FuzzySearching tester;
	      try {
	         tester = new FuzzySearching();
	         tester.createIndex();
	         tester.searchUsingFuzzyQuery(" ");
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	   }

	   @SuppressWarnings("unused")
	private void createIndex() throws IOException, SQLException {
	      indexer = new Indexer(indexDir);
	      int numIndexed;
	      long startTime = System.currentTimeMillis();	
	      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
	      long endTime = System.currentTimeMillis();
	      indexer.close();
	      System.out.println(numIndexed+" File indexed, time taken: "
	         +(endTime-startTime)+" ms");		
	   }
	   private void searchUsingFuzzyQuery(String searchQuery)
			   throws IOException, ParseException {
			   searcher = new Searcher(indexDir);
			   long startTime = System.currentTimeMillis();
			   
			   //create a term to search file name
			   Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
			   //create the term query object
			   Query query = new FuzzyQuery(term);
			   //do the search
			   TopDocs hits = searcher.search(query);
			   long endTime = System.currentTimeMillis();

			   System.out.println(hits.totalHits +
			      " documents found. Time :" + (endTime - startTime) + "ms");
			   for(ScoreDoc scoreDoc : hits.scoreDocs) {
			      Document doc = searcher.getDocument(scoreDoc);
			      System.out.print("Score: "+ scoreDoc.score + " ");
			      System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
			   }
			   searcher.close();
			}
}
