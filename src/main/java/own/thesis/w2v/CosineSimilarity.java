package own.thesis.w2v;

public class CosineSimilarity {

	public double cosineSimilarity(double[] vectorA, double[] vectorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < vectorA.length; i++) {
	        dotProduct += vectorA[i] * vectorB[i];
	        normA += Math.pow(vectorA[i], 2);
	        normB += Math.pow(vectorB[i], 2);
	    }   
	    dotProduct =  (1/(1+Math.exp(- dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)))));	
	    return dotProduct ;
	}
}
