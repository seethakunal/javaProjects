// Kunal Seetha
import java.util.*;
// csds 233 project 6
// undirected unweighted graph
public class Graph {
    // contains all the vertices, stored in a Hashmap
    private final HashMap<String, Vertex> graph;
    // subclass for vertex
    public class Vertex {
        private final String vertexName; // name of vertex
        private final LinkedList<Edge> allEdges; // edges connected to this vertex
        private String parent; // name of parent vertex

        // constructor for vertex
        public Vertex(String vertexName) {
            this.vertexName = vertexName;
            this.allEdges = new LinkedList<Edge>();
        }
        // return vertex name
        public String getVertexName() {
            return vertexName;
        }
        // returns all the edges connected to the node
        public LinkedList<Edge> getAllEdges() {
            return allEdges;
        }
        // set parent of the vertex
        public void setParent (String parent) {
            this.parent = parent;
        }
        // get parent of vertex
        public String getParent() {
            return parent;
        }
        // returns the specific edge that connects this vertex to the vertex given in the input
        // throws exception if the vertex indicated by the name doesn't exist
        public Edge getEdge(String vertexName) throws StringIndexOutOfBoundsException{
            for (Edge edge: allEdges) {
                if (edge.getTo().getVertexName().equals(vertexName))
                    return edge;
            }
            throw new StringIndexOutOfBoundsException();
        }
    }
    // sub class for edge
    public class Edge {
        // the vertex that the edge ends at
        private Vertex to;
        // constructor for edge
        public Edge(Vertex to) {
            this.to = to;
        }
        // returns the vertex that the edge ends at
        public Vertex getTo() {
            return to;
        }
    }
    // constructor for graph
    public Graph() {
        graph = new HashMap<>();
    }
    // main method
    public static void main(String[] args){
        System.out.println("Program running");
    }
    // adds a vertex unless the vertex is already present
    public boolean addNode(String vertexName) {
        if (graph.containsKey(vertexName))
            return false;
        Vertex vertex = new Vertex(vertexName);
        graph.put(vertexName, vertex);
        return true;
    }
    // adds multiple vertices, if one them is already present, then adds none of them
    public boolean addNodes(String[] vertexNames) {
        for (String vertexName:vertexNames) {
            if (graph.containsKey(vertexName))
                return false;
        }

        for (String vertexName:vertexNames) {
            addNode(vertexName);
        }
        return true;
    }
    // adds an edge to the graph, edge starts at from vertex and ends at to vertex
    public boolean addEdge(String from, String to) {
        // if the vertices are the same then return and do nothing
        if (to.equals(from))
            return false;
        // the graph must contain both the from vertex and the to vertex in order for edge to be drawn
        // sets a new edge starting from and ending at to
        if (graph.containsKey(to) && graph.containsKey(from)) {
            Vertex fromVertex = graph.get(from);
            Vertex toVertex = graph.get(to);
            Edge edge = new Edge(fromVertex);
            toVertex.allEdges.add(edge);
            edge = new Edge(toVertex);
            fromVertex.allEdges.add(edge);
            return true;
        }
        // return false if the graph doesn't contain either one of the vertices in input
        return false;
    }
    // adds multiple edges, the to vertices are stored in string array
    public boolean addEdges(String from, String[] tos) {
        // for each to vertex in the string array, make sure that it exists, then add them individually using addEdge
        for (String to: tos) {
            if (from.equals(to) || !graph.containsKey(from) || !graph.containsKey(to))
                return false;
        }
        for (String to: tos) {
            addEdge(from, to);
        }
        return true;
    }
    // removes a vertex indicated by the input name
    public boolean removeNode(String vertexName) {
        // try to remove vertex, catch exception if the vertex doesn't exist in the graph
        // if the exception is caught then return false
        try {
            // check if graph contains the vertex
            if (!graph.containsKey(vertexName))
                return false;
            // removed holds the value of the removed vertex
            // all edges must be deleted first because the vertex in order to access its edges
            Vertex removed = graph.get(vertexName);
            for (Edge edge : removed.getAllEdges()) {
                Vertex to = edge.getTo();
                to.getAllEdges().remove(to.getEdge(vertexName));
            }
            graph.remove(vertexName);
        }
        catch (StringIndexOutOfBoundsException exception) {
            return false;
        }
        // returns true if the code executes succesfully
        return true;
    }
    // removes multiple nodes, simply calls removeNode on each individual node given in the input of the method
    public boolean removeNodes(String[] nodes) {
        for (String vertexName: nodes) {
            if (!graph.containsKey(vertexName))
                return false;
        }
        for (String vertexName: nodes) {
            removeNode(vertexName);
        }
        return true;
    }
    // prints the graph
    public void printGraph() {
        // allNodes contains all the vertices in the graph
        ArrayList<String> allNodes = new ArrayList<>(graph.keySet());
        Collections.sort(allNodes); // sorts alphabetically the given list
        // for each vertex, need to access all the edges to get the next vertex
        for(String vertex: allNodes) {
            // edges stores all the edges in the graph
            LinkedList<Edge> edges = graph.get(vertex).getAllEdges();
            // stores the names of the vertices
            ArrayList<String> toNames = new ArrayList<>();
            // for each edge adds the name of the starting (or "from" vertex) to the toNames list
            for (Edge from: edges) {
                toNames.add(from.getTo().getVertexName());
            }
            Collections.sort(toNames); // sorts alphabetically the names of the vertices
            // still in the for loop, prints the name of the vertex currently at
            System.out.print(vertex + " ");
            // second for loop prints the names of the ending vertex ("to" vertex) via the edge connection
            for (String to: toNames) {
                System.out.print(to + " ");
            }
            // print the result
            System.out.println();
        }
    }
    // depth first traversal
    public String[] DFS(String from, String to, String neighborOrder) {
        // empty string array in case the traversal doesn't have a result so I can just return the empty array
        String[] empty = new String[0];
        // if the graph doesn't contain either the to or from vertex or they are the same, then the traversal won't work
        // return empty array in this case because nothing needs to be traversed
        if (!graph.containsKey(from) || !graph.containsKey(to) || to.equals(from))
            return empty;
        // set parent to null
        graph.get(from).setParent("");
        // stores the visited nodes in into a list so that nodes aren't double counted
        ArrayList<String> visitedNodes = new ArrayList<String>();
        // use recursive helper method in order to do the traversal and set all of the parent nodes to proper values
        try {
            DFShelper(from, to, neighborOrder, visitedNodes);
        }
        // if the neighbor order is not one of the two options, prints the error message and returns empty array
        catch (IllegalArgumentException exception) {
            System.out.println("neighborOrder incorrect");
            return empty;
        }
        // traversed contains the traversal in the proper format
        return traversed(graph.get(to));
    }
    // helper method for the depth first search traversal, simply sets all the parent nodes for each of the nodes
    // traverse helper method traverses through all of the updated nodes via parent in order to achieve the traversal
    // void method
    public void DFShelper(String from, String to, String neighborOrder, ArrayList<String> visitedNodes) {
        // if the vertices are the same then return nothing
        if (from.equals(to)) {
            return;
        }
        // if the neighbor order is not one of the two values allowed, then return error
        if (!neighborOrder.equals("reverse") && !neighborOrder.equals("alphabetical"))
            throw new IllegalArgumentException();
        // contains all the edges in the graph
        LinkedList<Edge> edges = graph.get(from).getAllEdges();
        // contains all the "to" vertices based on the from vertex
        ArrayList<String> tos = new ArrayList<>();
        // for each edge, adds the ending vertex to the list of ending vertices
        for (Edge edge: edges) {
            tos.add(edge.getTo().getVertexName());
        }
        // if reverse, then sorts alphabetically the reverses the order in order to have it in reverse alphabetical order
        if (neighborOrder.equals("reverse")) {
            Collections.sort(tos); Collections.reverse(tos);
        }
        // if alphabetical, then sorts alphabetically
        else
            Collections.sort(tos);
        // for each vertex in the list of ending vertices, need to first check if its already visited
        // if the node has not already been visited, then sets the parent of the to vertex to the starting vertex
        // then recursively call the helper method on all the ending vertices
        for (String node: tos) {
            if (!visitedNodes.contains(node)) {
                graph.get(node).setParent(from);
                DFShelper(node, to, neighborOrder, visitedNodes);
            }
        }
    }
    // the parent node will be used to traverse through the nodes and return the traversal pattern
    // starts at the last generation and then traverses upwards via each parent node
    private String[] traversed(Vertex to) {
        // order stores the order of all the vertices in which it is traversed
        ArrayList<String> order = new ArrayList<>();
        // the only vertex with a null parent value is the starting vertex of the traversal
        // adds all the vertices in the traversal order to the order variable
        while (!to.getParent().isEmpty()) {
            order.add(to.getVertexName());
            to = graph.get(to.getParent());
        }
        // adds the final vertex, because the starting vertex has a null parent it hasn't yet been added
        order.add(to.getVertexName());
        // the list elements were added in reverse order, the inOrder variable is a seperate list in order to add the elements in proper order
        String[] inOrder = new String[order.size()];
        // start at the end of the list, and traverse backward while adding elements to put it into correct order
        int index = 1;
        for (String node: order) {
            inOrder[inOrder.length - index] = node;
            index++;
        }
        // returns the properly ordered traversal
        return inOrder;
    }
    // breadth first traversal
    public String[] BFS(String from, String to, String neighborOrder) {
        // empty string array variable is simply to return an empty array if the traversal is not possible
        String[] empty = new String[0];
        // if the starting vertex is equal to the ending vertex, then the traversal cannot go anywhere farther
        if (from.equals(to))
            return empty;
        // if the neighbor order is not one of the two proper values, then prints the error for the user and returns the empty array
        if (!neighborOrder.equals("reverse") && !neighborOrder.equals("alphabetical")) {
            System.out.println("neighborOrder incorrect");
            return empty;
        }
        // this traversal will require a queue, will use Java's built in queue
        // all of the next ending vertices ("to" vertices) will be stored in the queue
        Queue<String> nextTo = new ArrayDeque<>();
        // stores the nodes visited already
        ArrayList<String> visitedNodes = new ArrayList<>();
        // set the initial vertex's parent to null so that the traversal stops
        graph.get(from).setParent("");
        // adds the starting vertex to the queue and also the visited nodes
        nextTo.add(from);
        visitedNodes.add(from);
        // stores the traversal in the proper order
        String[] order = new String[0];
        // while the queue still has elements, remove each element and process it individually
        while (!nextTo.isEmpty()) {
            // thisFrom stores the vertex that is removed, it is named thisFrom because it is used as the starting vertex in the rest of this loop
            Vertex thisFrom = graph.get(nextTo.remove());
            // gets all the edges attached to the from vertex
            LinkedList<Edge> edges = thisFrom.getAllEdges();
            // stores the ending vertices ("to" vertices)
            ArrayList<String> tos = new ArrayList<String>();
            // for each edge, add the ending vertex to the queue
            for (Edge edge: edges) {
                tos.add(edge.getTo().getVertexName());
            }
            // sort appropriate way after each element is removed from the queue
            if (neighborOrder.equals("reverse")) {
                Collections.sort(tos); Collections.reverse(tos);
            }
            else
                Collections.sort(tos);
            // find the vertex that equals the current "to" vertex and set its parent to thisFrom
            for (String vertex: tos) {
                if (vertex.equals(to)) {
                    graph.get(vertex).setParent(thisFrom.getVertexName());
                    // return the final traversal
                    return traversed(graph.get(vertex));
                }
            }
        }
        // returns empty if the next vertex is empty, indicating that the graph has reached dead end
        return empty;
    }
    // returns the shortest path of between two given vertices
    public String[] shortestPath(String from, String to) {
        // the breadth first traversal automatically shows the shortest path between nodes
        return BFS(from, to, "alphabetical");
    }
    // returns the second shortest path between two given vertices
    public String[] secondShortestPath(String from, String to) {
        // empty string array in case needs to be returned
        String[] empty = new String[0];
        // store the shortest path
        String[] shortestPath = shortestPath(from, to);
        // adds any potential paths to a linked list string array containing all possible paths
        LinkedList<String[]> potentialPaths = new LinkedList<>();
        // add the shortest path into the potential paths list
        potentialPaths.add(shortestPath);
        // at each vertex in the shortest path, find a connecting vertex and find the shortest path from that vertex to the ending vertex
        // add all the possible paths to the potentialPaths variable
        int i = 0;
        while (i < shortestPath.length - 1) {
            String removeName = shortestPath[i+1];
            Edge remove = graph.get(shortestPath[i]).getEdge(removeName);
            graph.get(shortestPath[i]).getAllEdges().remove(remove);
            potentialPaths.add(shortestPath(from, to));
            addEdge(shortestPath[i], removeName);
            i++;
        }
        // if there is only 1 path, then return the empty array and print message for user
        if (potentialPaths.size() < 2) {
            System.out.println("only 1 path possible");
            return empty;
        }
        // the secondShortest is first set to the second element of the potentialPaths variable
        // the first element of the list is the shortestPath, so that one is to be skipped over
        String[] secondShortest = potentialPaths.get(1);
        // for each of the potential paths, check that it is the new second shortest path and update the variable accordingly
        for (String[] path: potentialPaths) {
            if (path.length <= secondShortest.length && path.length > shortestPath.length)
                secondShortest = path;
        }
        // returns the second shortest path after checking all the options
        return secondShortest;
    }
}