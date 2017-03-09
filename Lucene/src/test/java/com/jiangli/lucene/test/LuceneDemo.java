package com.jiangli.lucene.test;

import com.jiangli.common.utils.PathUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jiangli
 * @date 2017/3/9 11:49
 */
public class LuceneDemo {
    private long startTs;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }

    @Test
    public void test_official_demo() throws Exception {
        //indexDir is the directory that hosts Lucene's index files
//            File indexDir = new File("D:\\luceneIndex");
        //dataDir is the directory that hosts the text files that to be indexed
//            File   dataDir  = new File("D:\\luceneData");

        Directory directory = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();

        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("text");
        ScoreDoc[] hits = isearcher.search(query, 0).scoreDocs;
        assertEquals(1, hits.length);
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
        }
        ireader.close();
        directory.close();
    }

    String projectPathStr = PathUtil.getProjectPathStr("Lucene");
    String indexPath = PathUtil.buildPath(projectPathStr, "src");
    String searchPath = PathUtil.buildPath(projectPathStr, "target/search");

    @Test
    public void test_write() throws Exception {
        System.out.println(indexPath);
        System.out.println(searchPath);
        final Path docDir = Paths.get(indexPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
            System.exit(1);
        }
        //recipe1
        Path writePath = Paths.get(searchPath);
        Directory dir = FSDirectory.open(writePath);

        Analyzer analyzer = new StandardAnalyzer();
        //recipe2
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        // Create a new index in the directory, removing any
        // previously indexed documents:
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter writer = new IndexWriter(dir, iwc);

        //start write
        IndexFiles.indexDocs(writer,docDir);

        writer.close();
    }

    @Test
    public void test_search() throws Exception {
        String queryString = "apache";
        String field = "contents";

        //recipe3
        Analyzer analyzer = new StandardAnalyzer();

        //recipe2
        QueryParser parser = new QueryParser(field, analyzer);

        //recipe1
        Query query = parser.parse(queryString);
        System.out.println("Searching for: " + query.toString(field));

        Path readPath = Paths.get(searchPath);
        IndexReader reader = DirectoryReader.open(FSDirectory.open(readPath));
        IndexSearcher searcher = new IndexSearcher(reader);

        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 50);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        float maxScore = results.getMaxScore();
        System.out.println(numTotalHits + " total matching documents");
        System.out.println(maxScore + " maxScore");
        System.out.println(hits.length + " length");

        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            String path = doc.get("path");
            String title = doc.get("title");
            System.out.println("   path: " + path);
            System.out.println("   title: " + title);
        }
        reader.close();
    }
}
