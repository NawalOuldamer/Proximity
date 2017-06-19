/**
 * 
 */
package own.thesis.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author ould
 *
 */
public class SaveFile {

	/**
	 * @param args
	 */
	private String PATH_SAVE_FILE;
	private String threshold;
	
	public SaveFile(String PATH_SAVE_FILE){
		this.PATH_SAVE_FILE = PATH_SAVE_FILE;
	}
	
	public void saveDocumentWeight(HashMap<String, Double> DocumentWeight, String NewQueryID) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"QueryDocumentWeight",true);
		file.write(NewQueryID+" ");
		for (Map.Entry<String, Double> entry : DocumentWeight.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue());
		}
		file.write("\n");
		file.close();
	}
	public void saveTag_LSI_Proximity(String docID, HashMap<String, Double> proximity_model, String Model, Double lambda_proximity) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"_"+Model+"_lambda_proximity_"+lambda_proximity,true);
		file.write(docID+" ");
		for (Map.Entry<String, Double> entry : proximity_model.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue());
		}
		file.write("\n");
		file.close();
	}
	public void saveLSI_Proximity(String docID, HashMap<String, Double> proximity_model, String Model) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"_"+Model,true);
		file.write(docID+" ");
		for (Map.Entry<String, Double> entry : proximity_model.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue());
		}
		file.write("\n");
		file.close();
	}
	public void saveDirProximity(String docID, HashMap<String, Double> proximity_model, String Model) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"_"+Model,true);
		file.write(docID+" ");
		for (Map.Entry<String, Double> entry : proximity_model.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue());
		}
		file.write("\n");
		file.close();
	}
	public void saveLDAProximity(String docID, HashMap<String, Double> proximity_model, String Model, Double lambda_proximity, int K, int K_D) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"_"+Model+"_lambda_proximity_"+lambda_proximity+"_K_"+K+"_K_D_"+K_D,true);
		file.write(docID+" ");
		for (Map.Entry<String, Double> entry : proximity_model.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue());
		}
		file.write("\n");
		file.close();
	}
	public void saveQueryModel(HashMap<String, Double> QueryModel, String QueryID) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"QueryModel",true);
		file.write(QueryID+" ");
		for (Map.Entry<String, Double> entry : QueryModel.entrySet()) {
		    file.write(entry.getKey()+":"+entry.getValue()+" ");
		}
		file.write("\n");
		file.close();
	}
	public void saveDocumentQueryModel(List<Map.Entry<String, Double>> QueryModel, String QueryID, String userID, String MODEL) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+"/"+MODEL+"_"+userID,true);
		file.write(QueryID+" ");
		for (Iterator<Entry<String, Double>> iterator = QueryModel.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
	public void saveQueryQuerySimilarity(String userID, String queryID1, String queryID2, String query1, String query2, Double score_nor,Double score_sig) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_SAVE_FILE+userID+"_QQSIM",true);
		file.write(userID+" "+queryID1+" "+ queryID2+" "+score_nor+" "+score_sig+"\n");
		file.close();
	}
	
	public void saveDynamicUserModel(List<Map.Entry<String, Double>> UserModel, String QueryID, String userID) throws IOException{
		if(!new File(this.PATH_SAVE_FILE+"/"+this.threshold).exists()){			
			new File(this.PATH_SAVE_FILE+"/"+this.threshold).mkdirs();
		}
		FileWriter file = new FileWriter(this.PATH_SAVE_FILE+"/"+this.threshold+"/"+userID,true);
		file.write(userID+" "+QueryID+" ");
		for (Iterator<Entry<String, Double>> iterator = UserModel.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
	public void saveQueryQueryUserModel(List<Map.Entry<String, Double>> UserModel, String QueryID, String userID, int top_k_queries) throws IOException{
		if(!new File(this.PATH_SAVE_FILE+"/"+top_k_queries).exists()){			
			new File(this.PATH_SAVE_FILE+"/"+top_k_queries).mkdirs();
		}
		FileWriter file = new FileWriter(this.PATH_SAVE_FILE+"/"+top_k_queries+"/"+userID,true);
		file.write(userID+" "+QueryID+" ");
		for (Iterator<Entry<String, Double>> iterator = UserModel.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
		public void saveQueryQueryUserModel(List<Map.Entry<String, Double>> UserModel, String QueryID, String userID, int top_k_queries,int top_k_documents) throws IOException{
			if(!new File(this.PATH_SAVE_FILE+"/"+top_k_queries+"/"+top_k_documents).exists()){			
				new File(this.PATH_SAVE_FILE+"/"+top_k_queries+"/"+top_k_documents).mkdirs();
			}
			FileWriter file = new FileWriter(this.PATH_SAVE_FILE+"/"+top_k_queries+"/"+top_k_documents+"/"+userID,true);
			file.write(userID+" "+QueryID+" ");
			for (Iterator<Entry<String, Double>> iterator = UserModel.iterator(); iterator.hasNext();) {
				Entry<String, Double> entry = iterator.next();
				file.write(entry.getKey()+":"+entry.getValue()+" ");				
			}
			file.write("\n");
			file.close();
	}
	public void saveDocumentModel(List<Map.Entry<String, Double>> DocumentModel,String docID) throws IOException{
		if(!new File(this.PATH_SAVE_FILE).exists()){			
			new File(this.PATH_SAVE_FILE).mkdirs();
		}
		FileWriter file = new FileWriter(this.PATH_SAVE_FILE+"Document_Models",true);
		file.write(docID+" ");
		for (Iterator<Entry<String, Double>> iterator = DocumentModel.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
	public void saveGlobalUserModel(List<Map.Entry<String, Double>> UserModel, String userID) throws IOException{
		if(!new File(this.PATH_SAVE_FILE+"/Global_Profile/").exists()){			
			new File(this.PATH_SAVE_FILE+"/Global_Profile/").mkdirs();
		}
		FileWriter file = new FileWriter(this.PATH_SAVE_FILE+"/Global_Profile/"+userID,true);
		file.write(userID+" ");
		for (Iterator<Entry<String, Double>> iterator = UserModel.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
}
