package de.uni_passau.fim.se2.sa.readability.features;

public class NumberLinesFeature extends FeatureMetric {

    /**
     * Computes the number of lines of the given code snippet.
     * Since we are interested in determining the readability of a code snippet, this also includes comments.
     *
     * @return source code lines of the given code snippet.
     */
    @Override
    public double computeMetric(String codeSnippet) {
        if (codeSnippet.length()==0 || codeSnippet == null ){
            return 0;
        }
        // ask llm about \n for different OSes
        return codeSnippet.split("\r\n|\r|\n").length;
            }

    @Override
    public String getIdentifier() {
        return "NumberLines";
    }
}
