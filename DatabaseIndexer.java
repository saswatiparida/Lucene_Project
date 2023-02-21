package com.test.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class DatabaseIndexer {
    public static void main(String[] args) throws CorruptIndexException, IOException {
        String indexDir = "C:\\Saswati_Lucene\\Index Directory"; // change to your index directory
        String dbUrl = "jdbc:sqlserver://WIN2012\\SQLEXPRESS;databaseName=ANIMALS;integratedSecurity=true;encrypt=false;"; // change to your database URL
        String username = "sa"; // change to your database username
        String password = "dbserver"; // change to your database password

        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);  
        Directory directory = null;
        try {
        	Path indexPath = Paths.get(indexDir);
            directory = FSDirectory.open(indexPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);

        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, config);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM PAGE_DETAILS");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Document doc = new Document();
                doc.add(new Field("Name", rs.getString("Name"), Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("Author", rs.getString("Author"), Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("Pages", rs.getString("Pages"), Field.Store.YES, Field.Index.ANALYZED));
                doc.add(new Field("Date", rs.getString("Date"), Field.Store.YES, Field.Index.ANALYZED));
                writer.addDocument(doc);
                System.out.println("Sucessfully Indexed!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}