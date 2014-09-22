package ataosky.example.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
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
import org.apache.lucene.util.Version;
import java.io.*;
import java.lang.annotation.Documented;

/**
 * Created by tangning on 14-8-4.
 */
public class LuceneIndex {
    public static final String  INDEX_STORE_PATH= "D:\\source_code\\bigdata\\lucene";
    public static final String FILE_TO_INDEX_PATH = "D:\\source_code\\bigdata\\mahout-distribution-0.8\\core\\src\\main\\java\\org\\apache\\mahout\\fpm\\pfpgrowth\\fpgrowth";


    public static void addDocs(IndexWriter indexWriter) {
        File[] files = new File(FILE_TO_INDEX_PATH).listFiles();
        for (File f : files) {
            Document doc = new Document();
            String content = getContent(f);
            String name = f.getName();
            String path = f.getAbsolutePath();

            doc.add(new TextField("content", content, Field.Store.YES));
            doc.add(new TextField("name", name, Field.Store.YES));
            doc.add(new TextField("path", path, Field.Store.YES));

            try {
                indexWriter.addDocument(doc);
                System.out.println("add " + name + " to index");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    public static String getContent(File file) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            sb.append(line);
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void search(Query query) {

        try {
            Directory directory = FSDirectory.open(new File(INDEX_STORE_PATH));

            IndexReader indexReader = IndexReader.open(directory);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            TopDocs topDocs = indexSearcher.search(query, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for(ScoreDoc scoreDoc:scoreDocs)
            {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                System.out.println("result name:"+doc.getField("name")+" path:"+doc.getField("path"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        Directory directory = FSDirectory.open(new File(INDEX_STORE_PATH));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        //create index
        addDocs(writer);
        writer.close();

        //test search
        String s = "Pattern";
        QueryParser queryParser = new QueryParser(Version.LUCENE_43,"content",analyzer);
        Query query = queryParser.parse(s);
        search(query);


    }
}
