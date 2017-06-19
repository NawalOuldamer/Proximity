/**
 * 
 */
package own.thesis.proximity;

import java.io.File;
/**
 * @author ould
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import own.thesis.w2v.LoadTextVector;

/**
 * @author ould
 *
 */
public class InitProximity {

	public String WORD2VEC_MODEL_PATH;
	public String PATH_DOCUMENTS_MODELS;
	public String PATH_TAGS_MODELS;
	private String PATH_DOCUMENT_TOPICS_DISTRIBUTION ="./data/SBS/initial_document_models/SLM";
	private String PATH_TAGS_TOPICS_DISTRIBUTION ="./data/SBS/initial_document_models/SLM";
	private String PATH_TERMES_TOPICS_DISTRIBUTION ="./data/SBS/initial_document_models/SLM";


	public HashMap<String,HashMap<String, Double> > DOCUMENTS_TAGS = new HashMap<>(); // document tags with their term frequency
	public HashMap<String,HashMap<String, Double> > DOCUMENTS_MODELS = new HashMap<>(); // document models (SLM, DIR,HIEM,BM25)
	private HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS = new HashMap<>(); // contains topics and terms distributions
	private HashMap<String, HashMap<Integer, Double>> DOCUMENTS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions
	private HashMap<String, HashMap<Integer, Double>> TAGS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions

	public WordVectors WORDS_VECTORS;


	// for TM
	public InitProximity(String WORD2VEC_MODEL_PATH,String PATH_TAGS_MODELS,String PATH_DOCUMENTS_MODELS){
		this.WORD2VEC_MODEL_PATH = WORD2VEC_MODEL_PATH;
		this.PATH_DOCUMENTS_MODELS = PATH_DOCUMENTS_MODELS;
		this.PATH_TAGS_MODELS = PATH_TAGS_MODELS;
	}

	// for LM
	public InitProximity(String PATH_TAGS_MODELS,String PATH_DOCUMENTS_MODELS){
		this.PATH_DOCUMENTS_MODELS = PATH_DOCUMENTS_MODELS;
		this.PATH_TAGS_MODELS = PATH_TAGS_MODELS;
	}

	// for LDA_MATRIX
	public InitProximity(String PATH_TAGS_TOPICS_DISTRIBUTION, String PATH_TAGS_MODELS, String PATH_DOCUMENTS_MODELS, String PATH_DOCUMENT_TOPICS_DISTRIBUTION, String PATH_TERMES_TOPICS_DISTRIBUTION ){
		this.PATH_DOCUMENTS_MODELS = PATH_DOCUMENTS_MODELS;
		this.PATH_TAGS_MODELS = PATH_TAGS_MODELS;
		this.PATH_DOCUMENT_TOPICS_DISTRIBUTION = PATH_DOCUMENT_TOPICS_DISTRIBUTION;
		this.PATH_TERMES_TOPICS_DISTRIBUTION = PATH_TERMES_TOPICS_DISTRIBUTION;
		this.PATH_TAGS_TOPICS_DISTRIBUTION  = PATH_TAGS_TOPICS_DISTRIBUTION;
	}



	public WordVectors loadW2VModel() throws IOException{
		InputStream stream = new FileInputStream(WORD2VEC_MODEL_PATH);
		LoadTextVector load_model = new LoadTextVector();
		WORDS_VECTORS = load_model.loadTxtVectors(stream, false);
		System.out.println("WORDS_VECTORS model load");
		return WORDS_VECTORS;
	}

	public HashMap<String,HashMap<String, Double> > loadDocumentModels() throws FileNotFoundException{
		Scanner s = new Scanner(new File(PATH_DOCUMENTS_MODELS));
		while (s.hasNextLine()) {
			String [] line =  s.nextLine().split(" ");
			HashMap<String, Double> document_model = new HashMap<>();
			for (int i = 1; i < line.length; i++) {
				String [] c =  line[i].split(":");
				document_model.put(c[0], Double.parseDouble(c[1]));
			}
			DOCUMENTS_MODELS.put(line[0], document_model);			
		}
		s.close();	
		return this.DOCUMENTS_MODELS;
	}

	public HashMap<String,HashMap<String, Double> > loadTagsModels() throws FileNotFoundException{
		Scanner s = new Scanner(new File(PATH_TAGS_MODELS));
		while (s.hasNextLine()) {
			String [] line =  s.nextLine().split(" ");
			HashMap<String, Double> tags_model = new HashMap<>();
			for (int i = 1; i < line.length; i++) {
				String [] c =  line[i].split(":");
				tags_model.put(c[0], Double.parseDouble(c[1]));
			}
			DOCUMENTS_TAGS.put(line[0], tags_model);			
		}
		s.close();		
		return this.DOCUMENTS_TAGS;
	}

	public HashMap<Integer,HashMap<String, Double>> loadTopicsTermsDistributions(){

		return TOPICS_TERMS_DISRIBUTIONS;
	}
	
	public HashMap<String,HashMap<Integer, Double>> loadTagsTopicsDistributions(){

		return TAGS_TOPICS_DISRIBUTIONS;
	}

	public HashMap<String,HashMap<Integer, Double>> loadDocumentsTopicsDistributions(){

		return DOCUMENTS_TOPICS_DISRIBUTIONS;
	}

}
