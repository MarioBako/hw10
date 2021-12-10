
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

    public class Graph {
        public static final String FILE = "src/Slashdot0902.txt";
        public static final int VERTEX_COUNT = 82168;

        public static DirectedGraph buildFromText(String filepath) {
            DirectedGraph outGraph = new DirectedGraph(VERTEX_COUNT);

            try{

                Scanner edges = new Scanner(new File(filepath));
                while (edges.hasNext()) {

                    String edge = edges.nextLine();
                    String[] parts = edge.split("\t");

                    if (parts.length == 2) {
                        outGraph.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    }

                }
                edges.close();

            } catch (Exception e){
                e.printStackTrace();
            }

            return outGraph;
        }

        public static ArrayList<Integer> verticesDFT(DirectedGraph graph) {

            ArrayList<Integer> L = new ArrayList();
            int size = graph.numVertex;
            boolean[] visited = new boolean[size];

            for (int i = 0; i < size; i++) {
                visited[i] = false;
            }

            for (int v = 0; v < size; v++) {
                if (!visited[v]) {

                    ArrayList<Integer> history = new ArrayList<>();
                    history.add(v);

                    while (history.size() > 0) {

                        int current = history.get(history.size() - 1);
                        visited[current] = true;

                        ArrayList<Integer> children = graph.getNeighborList(current).getInList();

                        int nextChild = -1;
                        for (int i = 0; i < children.size() && nextChild < 0; i++) {
                            if (!visited[children.get(i)]) {
                                nextChild = children.get(i);
                            }
                        }

                        if (nextChild == -1) {

                            L.add(0, current);

                            history.remove(history.size() -1 );
                        } else {

                            history.add(nextChild);
                        }
                    }
                }
            }
            return L;
        }

        public static ArrayList<ArrayList<Integer>> findSCCs(DirectedGraph graph) {

            ArrayList<ArrayList<Integer>> stronglyConnectedComponents = new ArrayList<>();

            ArrayList<Integer> L = verticesDFT(graph);

            int size = graph.numVertex;
            boolean[] visited = new boolean[size];

            for (int i = 0; i < size; i++) {
                visited[i] = false;
            }

            for (int v : L) {
                if (!visited[v]) {

                    ArrayList<Integer> component = new ArrayList<>();

                    ArrayList<Integer> history = new ArrayList<>();
                    history.add(v);

                    while (history.size() > 0) {

                        int current = history.get(history.size() - 1);
                        visited[current] = true;

                        ArrayList<Integer> children = graph.getNeighborList(current).getOutList();

                        int nextChild = -1;
                        for (int i = 0; i < children.size() && nextChild < 0; i++) {
                            if (!visited[children.get(i)]) {
                                nextChild = children.get(i);
                            }
                        }
                        if (nextChild == -1) {
                            component.add(current);

                            history.remove(history.size() -1 );
                        } else {
                            history.add(nextChild);
                        }

                    }

                    stronglyConnectedComponents.add(component);

                }
            }
            return stronglyConnectedComponents;

        }

        public static DirectedGraph reduced(ArrayList<ArrayList<Integer>> components, DirectedGraph g) {
            DirectedGraph out = new DirectedGraph(components.size());


            HashMap<Integer, Integer> newLabels = new HashMap<>();

            for (int i = 0; i < components.size(); i ++) {
                for (int vertex : components.get(i)) {
                    newLabels.put(vertex, i);
                }
            }

            for (int v1 = 0; v1 < components.size(); v1 ++) {
                for (int vertex : components.get(v1)) {
                    for (int neighbor : g.getNeighborList(vertex).getOutList()) {
                        int otherSCC = newLabels.get(neighbor);
                        out.addEdge(v1, otherSCC);
                    }
                }
            }

            return out;
        }

        public static void main(String[] args) {
            DirectedGraph graph = buildFromText(FILE);
            ArrayList<ArrayList<Integer>> components = findSCCs(graph);

            int maxSize = 0;

            for (ArrayList<Integer> component: components) {
                if (maxSize < component.size()) {
                    maxSize = component.size();
                }
            }
            DirectedGraph reduced = reduced(components, graph);
            int redEdgeCount = 0;
            for (int i = 0; i < reduced.numVertex; i ++) {
                redEdgeCount += reduced.getNeighborList(i).getOutDegree();
                if (reduced.getNeighborList(i).getOutDegree() > 0) {
                    System.out.println(reduced.getNeighborList(i).getOutDegree());
                }
            }

            System.out.println("\n\nCount: " + components.size() + "\nMax Size: " + maxSize);
            System.out.println("Reduced graph edges: " + redEdgeCount);

        }




    }
