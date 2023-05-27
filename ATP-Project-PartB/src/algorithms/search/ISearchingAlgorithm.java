package algorithms.search;
// interface for a searching algorithm
public interface ISearchingAlgorithm {
    public Solution solve(ISearchable domain);
    public String getName();
    public int getNumberOfNodesEvaluated();
}
