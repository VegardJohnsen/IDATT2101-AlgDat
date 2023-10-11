import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.io.BufferedReader;

/**
 * The Graph class represents a directed graph using adjacency list.
 * It supports the following operations:
 * 1. Add edge
 * 2. Breadth-first search
 * 3. Topological sort
 */

public class Graph {
    private int numVertices;
    private LinkedList<Integer> adjLists[];

    /**
     * Constructor for Graph
     * 
     * @param numVertices number of vertices in the graph
     */

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjLists = new LinkedList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            adjLists[i] = new LinkedList<>();
        }
    }

    /**
     * Adds an edge from source to destination.
     * 
     * @param src source vertex
     * @param dest destination vertex
     */

    public void addEdge(int src, int dest) {
        adjLists[src].add(dest);
    }


    /**
     * Breadth-first search from a start node.
     * 
     * @param startNode the start node
     */

    public void BFS(int startNode) {
        boolean visited[] = new boolean[numVertices];
        int[] distance = new int[numVertices];
        int[] predecessors = new int[numVertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(predecessors, -1);

        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[startNode] = true;
        distance[startNode] = 0;
        queue.add(startNode);

        while (queue.size() != 0) {
            int currentNode = queue.poll();

            for (int neighbor : adjLists[currentNode]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[currentNode] + 1;
                    predecessors[neighbor] = currentNode;
                    queue.add(neighbor);
                }
            }
        }

        System.out.println("NODE  FORGJENGER AVSTAND");
        for (int i = 0; i < numVertices; i++) {
            System.out.println(i + "     " 
            + (predecessors[i] == -1 ? "NA" : predecessors[i]) 
            + "          " + (distance[i] == Integer.MAX_VALUE ? "NA" : distance[i]));
        }
    }

    /**
     * Topological sort of the graph.
     * 
     * @param v the start node
     * @param visited array of visited nodes
     * @param stack stack of nodes
     */

    public boolean topologicalSortUtil(int v, VertexState[] state, 
    Stack<Integer> stack) {
        if (state[v] == VertexState.VISITING) {
            return true;
        }

        if (state[v] == VertexState.NOT_VISITED) {
            state[v] = VertexState.VISITING;
            for (Integer neighbor : adjLists[v]) {
                if (topologicalSortUtil(neighbor, state, stack)) {
                    return true;
                }
            }
            state[v] = VertexState.VISITED;
            stack.push(v);
        }
        return false;
    }
    
    /**
     * Enum for the state of a vertex.
     */

    private enum VertexState {
        NOT_VISITED, VISITING, VISITED
    }

    /**
     * Topological sort of the graph.
     */

    public void topoLogicalSort() {
        Stack<Integer> stack = new Stack<>();
        VertexState[] state = new VertexState[numVertices];
        Arrays.fill(state, VertexState.NOT_VISITED);
        for (int i = 0; i < numVertices; i++) {
            if (state[i] == VertexState.NOT_VISITED) {
                if (topologicalSortUtil(i, state, stack)) {
                    System.out.println("Topological sort is not possible");
                    return;
                }
            }
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    /**
     * Reads a file from URL and returns a Graph object.
     * 
     * @param url the URL
     * @return the graph
     */

    public static Graph readGraphFromUrl(String url) {
        try {
            var uri = new URI(url);
            URLConnection conn = uri.toURL().openConnection();
            InputStreamReader input = new InputStreamReader(conn.getInputStream());
            BufferedReader buffer = new BufferedReader(input);
            String line;

            String[] firstLine = buffer.readLine().split(" ");
            int numVertices = Integer.parseInt(firstLine[0]);



            Graph graph = new Graph(numVertices);
            while ((line = buffer.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("\\s+");
                int src = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);
                graph.addEdge(src, dest);
            }
            return graph;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a graph from a file.
     * 
     * @param path the path to the file
     */
    private static void graphGenerator(String path) {

        if (readGraphFromUrl(path) != null) {
            Graph graph = readGraphFromUrl(path);
            System.out.println("BFS for graph");
            graph.BFS(0);
            System.out.println("\nTopological sort for graph");
            graph.topoLogicalSort();
        }
    }


    public static void main(String[] args) {

            // Paths for the testfiles
            var path1 = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/%C3%B86g1";
            var path2 = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/%C3%B86g2";
            var path3 = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/%C3%B86g3";
            var path4 = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/%C3%B86g5";
            var path5 = "https://www.idi.ntnu.no/emner/idatt2101/uv-graf/%C3%B86g7";

            graphGenerator(path5);
        } 
    }
    

