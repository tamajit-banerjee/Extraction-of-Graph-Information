import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A4_2019CS10408{
    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> index = new HashMap<String, Integer>();
        HashMap<Integer, String> inverted_index = new HashMap<Integer, String>();
        String input_nodes, input_edges;
        int temp = 0;
        graph g = new graph();
        BufferedReader node_input = new BufferedReader(new FileReader(args[0]));
        node_input.readLine();
        while((input_nodes = node_input.readLine()) != null) {
            String splits[] = input_nodes.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String label = splits[1].charAt(0) == '"' ? splits[1].substring(1, splits[1].length() - 1) : splits[1];
            index.put(label,temp);
            inverted_index.put(temp,label);
            g.putvertex(temp, label);
            temp++;
        }
        node_input.close();
        BufferedReader edge_input = new BufferedReader(new FileReader(args[1]));
        edge_input.readLine();
        while((input_edges = edge_input.readLine()) != null){
            String splits[] = input_edges.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String source = splits[0].charAt(0) == '"' ? splits[0].substring(1, splits[0].length() - 1 ) : splits[0];
            String target = splits[1].charAt(0)=='"' ? splits[1].substring(1, splits[1].length() - 1 ) : splits[1];
            int w = Integer.valueOf(splits[2]);
            g.putedge(index.get(source) , index.get(target) , w);
        }
        edge_input.close();
        if(args[2].compareTo("average") == 0) g.aver();
        else if(args[2].compareTo("rank") == 0) g.rank();
        else if(args[2].compareTo ("independent_storylines_dfs") == 0) g.independent_storylines_dfs();
    }
}

class Node{
    public int index;
    public ArrayList<Edge> edges;
    public String label;
    public int degree;

    Node(int in, String l){
        this.index = in;
        this.label = l;
        this.edges = new ArrayList<Edge>();
        this.degree = 0;
    }
}

class Edge{
    public int weight;
    public Node target;
    Edge(int w, Node t){
        this.weight = w;
        this.target = t;
    }
}


class graph {
    public static int V, E;
    public static ArrayList<Node> vertices;
    public static int num_of_comp;
    private int[] visited;
    graph(){
        V = 0;
        E = 0;
        vertices = new ArrayList<Node>();
    }
    public void putvertex(int index, String label){
        vertices.add(new Node(index, label));
        V = V + 1;
    }

    public void putedge(int s, int t, int w){
        Node u = vertices.get(s);
        Node v = vertices.get(t);
        u.degree = u.degree + w;
        v.degree = v.degree +  w;
        u.edges.add(new Edge(w , v));
        v.edges.add(new Edge(w , u));
        E = E + 1;
    }

    public void aver(){
        float num_vertices = (float) this.V;
        float num_edges = (float) this.E;
        float aver = (float) ( 2.0 * num_edges / num_vertices );
        System.out.printf("%.2f%n", aver);
    }

    static void rank() {
        ArrayList<Node> answer = new ArrayList<Node>();
        for (int i = 0; i < V ; i++) {
            Node x = vertices.get(i);
            answer.add (new Node(x.degree, x.label));
        }
        Sorting.msort(answer, 0, V-1);
        for(int i = 0; i < V-1 ; i++)
            System.out.print(answer.get(i).label + ',');
        System.out.print(answer.get(V-1).label);
        System.out.println();
    }

    void dfs(Node u, ArrayList<Node> store_comp ) {
        store_comp.add(u);
        this.visited[u.index] = 1;
        for (int i = 0; i < u.edges.size(); i++ ) {
            Edge e = u.edges.get(i);
            Node v = e.target;
            if(this.visited[v.index]==0)
                dfs(v,store_comp);
        }
    }

    void independent_storylines_dfs() {
        this.visited = new int[this.V];
        for(int i = 0; i<this.V; i++){
            this.visited[i] = 0;
        }
        HashMap<String,Integer> Lex_order = new HashMap<String,Integer>();
        ArrayList<Node> SortedAllComponents = new ArrayList<Node>();
        ArrayList<ArrayList<Node>> components = new ArrayList<ArrayList<Node>>();
        int cur = 0;
        for(int i=0;i<V;i++){
            if(this.visited[i] == 0){
                ArrayList<Node> store_comp = new ArrayList<Node>();
                ArrayList<Node> store_comp_for_sorting = new ArrayList<Node>();
                dfs(vertices.get(i),store_comp);
                String highest = store_comp.get(0).label;
                for(int j=0;j<store_comp.size();j++){
                    if(store_comp.get(j).label.compareTo(highest) > 0){
                        highest = store_comp.get(j).label;
                    }
                    store_comp_for_sorting.add(new Node(1,store_comp.get(j).label));
                }
                Lex_order.put(highest,cur);
                Sorting.msort(store_comp_for_sorting,0,store_comp.size()-1);
                SortedAllComponents.add(new Node(store_comp.size(),highest));
                components.add(store_comp_for_sorting);
                ++cur;
            }
        }
        num_of_comp = cur;
        Sorting.msort (SortedAllComponents , 0, num_of_comp - 1);
        for (int i = 0; i < num_of_comp ; ++i){
            int ind = (int) Lex_order.get(SortedAllComponents.get(i).label);
            for(int j=0;j<components.get(ind).size()-1;j++){
                System.out.print( components.get(ind).get(j).label);
                System.out.print( ",");
            }
            System.out.println( components.get(ind).get(components.get(ind).size()-1).label);
        }
    }
}

class Sorting {
    static void msort(ArrayList<Node> a, int left , int right ){
        if (left >= right) return;
        int mid = (left + right) / 2;
        msort(a,left, mid);
        msort(a,mid + 1, right );
        merge(a,left, mid, right );
    }
    static boolean compare(Node a , Node b){
        if(a.index > b.index){
            return true;
        }else if(b.index > a.index){
            return false;
        }else if(a.label.compareTo(b.label) > 0 ){
            return true;
        }else{
            return false;
        }
    }

    static void merge(ArrayList<Node> a, int l, int m, int r) {
        Node temp[] = new Node[r - l + 1];
        int i = l;
        int j = m + 1;
        int cur = 0;
        while (i < m + 1 && j < r + 1) {
            if(compare(a.get(i),a.get(j))){
                temp[cur] = a.get(i);
                i++;
            }else{
                temp[cur] = a.get(j);
                j++;
            }
            cur++;
        }
        while (i < m + 1) {
            temp[cur] = a.get(i);i++;cur++;
        }
        while (j < r + 1) {
            temp[cur] = a.get(j);j++;cur++;
        }

        for (int y = l; y <= r; y++)
            a.set(y,temp[y - l]);
    }

}


