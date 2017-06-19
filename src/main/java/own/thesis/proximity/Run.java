package own.thesis.proximity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import own.thesis.proximity.strategy.VectorEmbeddingProximity;
import own.thesis.utils.SortList;

public class Run {

	private static String PATH_SAVE_FILE = "./data/SBS/proximity_models/";
	private static String WORD2VEC_MODEL_PATH ="./data/SBS/aol-dataset.100.cbow.w8.neg25.it25.model";
	private static String PATH_TAGS_MODELS = "./data/SBS/tags_models/sbs_document_tags_cleaned";
	private static String PATH_DOCUMENTS_MODELS ="./data/SBS/initial_document_models/SLM";
	private static String MODEL_Prox;
	private static String MODEL_Doc;
	private static double sim_threshold;

	private static HashMap<String,HashMap<String, Double> > DOCUMENTS_TAGS = new HashMap<>(); // document tags with their term frequency
	private static HashMap<String,HashMap<String, Double> > DOCUMENTS_MODELS = new HashMap<>(); // document models (SLM, DIR,HIEM,BM25)
	private static WordVectors WORDS_VECTORS;


	public static void usage(){
		System.out.println("	--build-proximity-model [TM, LDA]  ");
		System.out.println("	--sim-threshold double  ");
		System.out.println("	--document-model [SLM,HIEMSTRA,DIRICHLET,BM25]  ");
		System.out.println("	--input-path-save-results-file [FILENAME]  ");
		System.out.println("	--input-path-tags-models-file [FILENAME]  ");
		System.out.println("	--input-path-document-models-file [FILENAME]  ");
		System.out.println("	--input-w2v-model [FILENAME]");

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length == 0 || args[0].equals("--help") ) {
			System.out.print("Usage: " ); usage();
		}
		else {
			for (int i = 0; i < args.length; i = i+2) {
				if(args[i].equals("--build-proximity-model")){
					MODEL_Prox = args[i+1];
				}
				if(args[i].equals("--sim-threshold")){
					sim_threshold = Double.parseDouble(args[i+1]);
				}
				
				if(args[i].equals("--document-model")){
					MODEL_Doc = args[i+1];
				}
				if(args[i].equals("--input-path-save-results-file")){
					PATH_SAVE_FILE = args[i+1];
				}
				if(args[i].equals("--input-path-tags-models-file")){
					PATH_TAGS_MODELS = args[i+1];
				}
				if(args[i].equals("--input-path-document-models-file")){
					PATH_DOCUMENTS_MODELS = args[i+1];
				}				
				if(args[i].equals("--input-w2v-model")){
					WORD2VEC_MODEL_PATH = args[i+1];
				}

			}
			
			
			if(MODEL_Prox.equals("TM")){
				System.out.println("init");
				InitProximity init = new InitProximity(WORD2VEC_MODEL_PATH, PATH_TAGS_MODELS, PATH_DOCUMENTS_MODELS);
				System.out.println("loadDocumentModels");
				DOCUMENTS_MODELS = init.loadDocumentModels();
				System.out.println("loadTagsModels");
				DOCUMENTS_TAGS = init.loadTagsModels();
				System.out.println("loadW2VModel");
				WORDS_VECTORS =init.loadW2VModel();
				estimate_Prox();
			}
			else {
				if(MODEL_Prox.equals("LDA")){
					
				}
			}
		}
		
	}

	public static void estimate_Prox() throws NumberFormatException, IOException{
		SortList sort = new SortList();
		for (Entry<String,HashMap<String, Double> > entry : DOCUMENTS_TAGS.entrySet()) {
			String docID = entry.getKey();
			HashMap<String, Double> Document_Tag = new HashMap<>();
			Document_Tag = entry.getValue();			
			HashMap<String, Double> Document_Model = DOCUMENTS_MODELS.get(docID);
			VectorEmbeddingProximity tm_prox = new VectorEmbeddingProximity(sim_threshold, Document_Model, Document_Tag, WORDS_VECTORS);
			tm_prox.estimate();
			HashMap<String, Double> Proximity = new HashMap<>(); 
			tm_prox.normalization();
			Proximity = tm_prox.getProximity();
			save_Model(docID,sort.sort(Proximity, Proximity.size()), sim_threshold, MODEL_Doc);
		}
	}


	public static void save_Model(String docID, List<Map.Entry<String, Double>> Proximity, double threshold, String MODEL) throws IOException{
		if(!new File(PATH_SAVE_FILE).exists()){			
			new File(PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+MODEL,true);
		file.write(docID+" ");
		for (Iterator<Entry<String, Double>> iterator = Proximity.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
}
