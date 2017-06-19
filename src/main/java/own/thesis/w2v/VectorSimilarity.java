package own.thesis.w2v;



import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;

/**
 * @author ould
 *
 */
public class VectorSimilarity {

	public WordVectors WORDS_VECTORS;

	public VectorSimilarity(WordVectors WORDS_VECTORS){
		this.WORDS_VECTORS = WORDS_VECTORS;
	}
	
	public Double estimateSigmoidVectorSimilarity(INDArray VECTOR1, INDArray VECTOR2){
		double sim = Transforms.cosineSim(VECTOR1, VECTOR2);
		return  (1/(1+Math.exp(-sim)));	
	}

	public Double estimateNormalizeVectorSimilarity(INDArray VECTOR1, INDArray VECTOR2){
		double sim = Transforms.cosineSim(VECTOR1, VECTOR2);
		return  ((sim+1)/2);	
	}

	public double estimateSigmoidVectorSimilarity(String Term1, String Term2){
		double sim = 0.d;
		if(WORDS_VECTORS.hasWord(Term1) && WORDS_VECTORS.hasWord(Term2)){
			sim = WORDS_VECTORS.similarity(Term1, Term2);
			return (1/(1+Math.exp(-sim)));
		}
		return sim;
	}
	
	public double estimateNormalizeVectorSimilarity(String Term1, String Term2){
		double sim = 0.d;
		if(WORDS_VECTORS.hasWord(Term1) && WORDS_VECTORS.hasWord(Term2)){
			sim = WORDS_VECTORS.similarity(Term1, Term2);
			sim = ((sim+1.0)/2.0);
		}
		return sim;
	}
}