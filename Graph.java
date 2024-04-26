import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    LinkedList<Integer>[] adjList;
    int vertices;
    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new LinkedList[vertices];
        for (int i=0; i<vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
        adjList[v].add(u);
    }

    public List<Integer> bfs(int startNode, int destNode) {
        List<Integer> path = new ArrayList<>();
        path.add(startNode);

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(path);
        List<List<Integer>> possiblePaths = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> currPath = queue.poll();
            int currNode = currPath.get(currPath.size()-1);

            //System.out.println("===" + currPath);
            if (currNode == destNode) {
                //printPath(currPath);
                possiblePaths.add(currPath);
            }
            LinkedList<Integer> list = adjList[currNode];

            // visit the neighbours
            for (int i: list) {
                if (notVisited(i, currPath)) {
                    List<Integer> newPath = new ArrayList<>(currPath);
                    newPath.add(i);
                    queue.add(newPath);
                }
            }
        }

        // from all the listed path find the shortest path.
        int minSize = Integer.MAX_VALUE;
        List<Integer> minPath = null;
        for (List<Integer> possiblePath: possiblePaths) {
            //System.out.println(possiblePath);
            if (possiblePath.size() < minSize) {
                minPath = possiblePath;
                minSize = possiblePath.size();
            }
        }

        return minPath;

    }

    private void printPath(List<Integer> currPath) {
        for (int i=0; i<currPath.size(); i++) {
            System.out.print(currPath.get(i) + " ");
        }
        System.out.println();
    }

    private boolean notVisited(int visitNode, List<Integer> currPath) {
        for (int i=0; i<currPath.size(); i++) {
            if (currPath.get(i) == visitNode) {
                return false;
            }
        }

        return true;
    }

    public void test1() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(0, 3);
        graph.addEdge(1, 4);

        computeMinimumTime(graph, 3, 5, 0, 4);
    }

    public void test4() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(0, 3);
        graph.addEdge(1, 4);

        computeMinimumTime(graph, 4, 5, 0, 4);
    }

    public void test5() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(0, 3);
        graph.addEdge(1, 4);

        computeMinimumTime(graph, 1, 5, 0, 4);
    }
    public void test2() {
        Graph graph = new Graph(2);
        graph.addEdge(0, 1);

        computeMinimumTime(graph, 3, 5, 0, 1);
    }

    public void test3() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        computeMinimumTime(graph, 3, 5, 0, 2);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // String input
        String firstLine = sc.nextLine();
        String[] list = firstLine.split("\\s");

        int numVertices = Integer.parseInt(list[0]);
        int numPath = Integer.parseInt(list[1]);
        int T = Integer.parseInt(list[2]);
        int C = Integer.parseInt(list[3]);

        Graph graph = new Graph(numVertices);
        for (int i=1; i<= numPath; i++) {
            String[] strList = sc.nextLine().split("\\s");
            graph.addEdge(Integer.parseInt(strList[0]) -1, Integer.parseInt(strList[1]) -1);
        }
        String[] strList = sc.nextLine().split("\\s");
        int srcNode = Integer.parseInt(strList[0]) -1;
        int destNode = Integer.parseInt(strList[1]) -1;

        computeMinimumTime(graph, T, C, srcNode, destNode);
    }

    static class Result {
        int numOfNodes;
        List<Integer> minPath;
        int totalTime;

        public Result(int size, List<Integer> minPath, int totalTime) {
            this.numOfNodes = size;
            this.minPath = minPath;
            this.totalTime = totalTime;
        }
    }
    private static Graph.Result computeMinimumTime(Graph graph, int T, int C, int src, int dest) {
        List<Integer> minPath = graph.bfs(src, dest);
        //System.out.println(minPath);

        int size = minPath.size();

        // for display purpose
        List<Integer> convertedPath = minPath.stream().map(x -> x+1).collect(Collectors.toList());
        System.out.println(size);
        StringBuilder stringBuilder = new StringBuilder();
        for (int x: convertedPath) {
            stringBuilder.append(x + " ");
        }
        System.out.println(stringBuilder.toString().trim());

        //int T = 3; int C = 5;
        int i = 1;
        int y = 2*i* T;

        while (y < C) {
            i++;
            y = 2*i* T;
        }

        int totalTime = (size - 2)  * y + C;

        //System.out.println(totalTime);
        Graph.Result result = new Graph.Result(size, minPath, totalTime);

        return result;
    }


}