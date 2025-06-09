package de.uni_passau.fim.se2.sa.readability.features;

public abstract class FeatureMetric {

    /**
     * Computes the metric of the respective feature.
     *
     * @return feature metric value.
     */
    public abstract double computeMetric(String codeSnippet);

    /**
     * Returns a unique identifier for the concrete FeatureMetric.
     *
     * @return unique FeatureMetric identifier.
     */
    public abstract String getIdentifier();
}
