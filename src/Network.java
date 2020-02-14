


import java.io.*;
import java.util.*;
import java.io.OutputStreamWriter;


public class Network {
                //<String, Node> means  the keys has type "String" and the values has type "Node"
    Hashtable<String,Node> Nodelist;       //we use hashtable as nodelist, which is similar to dictionary in Python, mapping keys to values; reason why we use
                                            // it is that hashtable only allow unique keys which are nodes in our case, so there will be no duplicate nodes
                                            // in our Nodelist
    ArrayList<Edge> Edgelist;       //ArrayList is a dynamic array in Java, dynamic means that it has variable length so we can add elements to the list
        //<Edge> means the element in Edgelist has type "Edge"

    ArrayList<String> hublist;     //Arraylist of all nodes with maximum degree

    Hashtable<Integer, Integer> Degreelist;   //key: all possible degree, value:  all the nodes with that degree




    public Network(){                                       //this is a default constructor which creates an empty network
        this.Nodelist = new Hashtable<String, Node>();     //empty bracket
        this.Edgelist = new ArrayList<>();
        this.hublist = new ArrayList<>();
        this.Degreelist = new Hashtable<Integer, Integer>();
    }

    public void readfile(String file_name) throws FileNotFoundException {        //function reading the file, in bracket (String file_name), String is the
                                                                            // type of variable, "file_name" is the name of variable
        File f = new File(file_name);       //File class imported through package "java.io.File", f is the name
        Scanner scanner = new Scanner(f);   //Scanner class imported through package "java.util.Scanner", it reads the data from file f

        while (scanner.hasNextLine()) {           //while loop with condition ".hasNextLine" which checks if there is another line of data
            String line = scanner.nextLine();
            String[] s = line.split("\t");   //split the line
            Node node1 = new Node(s[0]);         // assigning the first node
            Node node2 = new Node(s[1]);         // assigning the second node

            Edge edge = new Edge(node1,node2);  //assigning the edge

            if (!this.Nodelist.containsKey(node1.returnNode())) {      //Duplicate interactions should only be included once
                this.Nodelist.put(node1.returnNode(),node1);            //! symbol means negation, if the current node is not contained in the list,
            }                                                           // then we add it to the list. Here we can only compare the String value of the
                                // current node with all the keys in Nodelist because in java we can only decide whether two nodes are identical by
                                // looking at their String value, otherwise even if two nodes have the same String value they are not "exactly" the same
                                // because they are stored in different memory in the computer, so we can only compare "node1.returnNode()" which return
                                // String value of node1 and all the keys in "Nodelist" which are also String values

            if (!this.Nodelist.containsKey(node2.returnNode())) {
                this.Nodelist.put(node2.returnNode(),node2);
            }

            if (containEdge(edge)) {   //here we compare the edges, if same edge is already contained in the list then we skip it, so we need an extra
                return;                // function to compare the current edge and all the existing edges in the list
            }

            this.Edgelist.add(edge);

        }
    }
                                //"Edge suibian" is the input current edge, "Edge" is the class/type and "suibian" is the name
    public boolean containEdge(Edge suibian){      //this is the function comparing the edges, the return value of the function is boolean so "public boolean"
        for (int i = 0; i < this.Edgelist.size(); i++){     //for loop, written in Python as: "for i in range(len(Edgelist)):"
            Edge edge = this.Edgelist.get(i);  //indexing the i^th element in the Edgelist
            if (sameEdge(suibian,edge)){       //extratra function deciding whether i^th element is identical as current edge "suibian"
                return true;            //if identical , return true
            }
        }

        return false;       // if all keys are not identical as the current edge, return False

    }

    public boolean sameEdge(Edge e1, Edge e2){   //extratra fucntion deciding if two input edges are identical in terms of their String value
                                                // same edge cases :  "A---B" = "A---B" ; "A---B" = "B---A"
        String n11 = e1.nodes[0].returnNode();  //String value of first node in first input edge
        String n21 =  e1.nodes[1].returnNode(); //String value of second node in first input edge

        String n12 = e2.nodes[0].returnNode();  //String value of first node in second input edge
        String n22 = e2.nodes[1].returnNode();  //String value of second node in second input edge

        return (n11.compareTo(n12) == 0 && n21.compareTo(n22) == 0) || (n11.compareTo(n22) == 0 && n21.compareTo(n12) == 0);        //if (first nodes are the same and second nodes are the same as well) or (two edges are in reverse order), then true
    }


    public void addEdge(String node1, String node2) throws IllegalArgumentException {    //this function manually add edge to the network, the void means no return, the input should be
                                                    //two nodes: node1 and node2 with type/class String


        Node n1 = new Node(node1);    //defining two nodes , class/type: "Node", name: "n1" and "n2"
        Node n2 = new Node(node2);
        Edge edge1 = new Edge(n1, n2);  //defining edge

        if (!this.Nodelist.containsKey(n1.returnNode())){
            this.Nodelist.put(n1.returnNode(), n1);    //if node1 is not contained in the nodelist, then add it to the list
        }

        if (!this.Nodelist.containsKey(n2.returnNode())){   //if node2 is not contained in the nodelist then add it as well
            this.Nodelist.put(n2.returnNode(), n2);
        }

        if (containEdge(edge1)){
            return;
        }
                                                //if edge1 is not contained in the edgelist the add it
        this.Edgelist.add(edge1);

    }


    public int find_d(String anynode){   //function finding the degree of input, input: anynode
        if (!this.Nodelist.containsKey(anynode)){    //does not contain the node, exception
            throw new IllegalArgumentException("No node found");
        }

        int c = 0;    //count of degree

        for (int i = 0 ; i < this.Edgelist.size(); i++){    //navigate along the edgelist
            Edge e = this.Edgelist.get(i);   //one edge

            if (e.nodes[0].returnNode().compareTo(anynode)==0 || e.nodes[1].returnNode().compareTo(anynode)==0 ){
                //comparing two strings lexicographically, 0 if both strings are identical
                c ++;    //in this case, self-connected is counted
                if (e.nodes[0].returnNode().compareTo(e.nodes[1].returnNode())==0){
                    c ++;           //when self-connected!!!!!
                }
            }
        }
        return c;    //return the degree of anynode
    }



    public float find_avgdegree(){     //function computing the average degree of all nodes
        float total_degree = 0.0f;    //declare float

        for (Map.Entry<String, Node> entry : this.Nodelist.entrySet()){    //navigate along the whole notes list
            String key = entry.getKey();     //get the string value of each node
            total_degree += find_d(key);     //compute the degree of each node and add it to total_degree
        }                                   // or we can use formula avgDegree = 2* (# of edges)/ (# of nodes)

        return (total_degree/(this.Nodelist.size()));
    }


    public int find_hub(){    //method finding the maximum degree and all the nodes with that value of degree
        int max_D = 0;
        for (Map.Entry<String, Node> entry : this.Nodelist.entrySet()){
            String key = entry.getKey();

            if (find_d(key) == max_D){      //if has equal degree, add the node to hublist
                this.hublist.add(key);
            }
            if (find_d(key) > max_D){       //if has larger degree, update max_D and hublist
                max_D = find_d(key);
                this.hublist = new ArrayList<>();
                this.hublist.add(key);
            }
        }
        return max_D;
    }

    public void distribution(){                 //generate a full distribution of all degrees, key: degree, value: # of nodes with that degree
        for (Map.Entry<String, Node> entry : this.Nodelist.entrySet()){
            String key = entry.getKey();
            int degree  = find_d(key);


            if (!this.Degreelist.containsKey(degree)){
                this.Degreelist.put(degree, 1);
            }else {
                this.Degreelist.put(degree, this.Degreelist.get(degree) + 1);
                //this.Degreelist.put(degree, (this.Degreelist.get(degree)) + key + ";");
            }
        }
    }

    public void write(OutputStreamWriter out) throws IOException {  //method writting the Degreelist locally
        out.write("Degrees  |"+ "   Number of Nodes\n");

        for (Integer key: this.Degreelist.keySet()) {
            out.write(Integer.toString(key));
            out.write("        |    ");
            out.write(String.valueOf(this.Degreelist.get(key)));
            out.write("\n");
        }
        out.flush();
    }



    public String[] printlist(){                //function that print the Nodelist and Edgelist

        String[] plist = new String[2];         //string array which contain nodelist and edgelist
        StringBuilder nodelist = new StringBuilder();  //concatenate the string of nodes
        String edgelist = "";

        System.out.println("Notes: ");
        for(Map.Entry<String, Node> entry : this.Nodelist.entrySet()){    //this is how you print Hashtable, from internet
            String key = entry.getKey();
            nodelist.append(key).append("; ");
            //String value = entry.getValue().returnNode();
            System.out.print(key + " ");
        }
        plist[0] = nodelist.toString();         //assign the nodelist to plist[0]

        System.out.println("\nEdges: ");                    //print listarray
        for (int i =0; i < this.Edgelist.size(); i++){
            System.out.print(this.Edgelist.get(i).nodes[0].returnNode() + "----");  //need to transfer to String value
            System.out.println(this.Edgelist.get(i).nodes[1].returnNode());
            edgelist = edgelist + "\n" + this.Edgelist.get(i).nodes[0].returnNode() + "---" + this.Edgelist.get(i).nodes[1].returnNode() ;
        }
        plist[1] = edgelist;
        //for (Edge num : this.Edgelist){
          //  System.out.println(num.nodes[0].returnNode());
        //}
        return plist;
    }

    public void printdegree(String anynode){      //print the degree of a particular node
        System.out.println("Degree of node " + anynode + ": " +find_d(anynode));
    }

    public String printAvgdegree(){           //print the average of all nodes
        System.out.println("The averaged degree of all " + this.Nodelist.size() + " nodes is: " + find_avgdegree());
        //System.out.println(this.Edgelist.size());
        return Float.toString(find_avgdegree());
    }

    public String  printHub(){
        System.out.println("The maximum degree is: " + find_hub());
        String out_str = "";
        for (int i = 0; i < this.hublist.size(); i++){
            System.out.println("Node(s) with maximum degree is(are): " + this.hublist.get(i));
            out_str = out_str + this.hublist.get(i) + "; " ;
        }
        return out_str;
    }

    public void printDistribution() throws IOException {    //method printing the distribution and save it locally as .txt file
        distribution();
        System.out.println("The full distribution of all nodes are: ");
        for (Map.Entry<Integer, Integer> entry : this.Degreelist.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Degree: " + key + " ; # of Nodes: " + value);
        }
        //FileOutputStream fos = new FileOutputStream("DegreeDistribution.xml");
        //XMLEncoder e = new XMLEncoder(fos);
        //e.writeObject(this.Degreelist);
        //e.close();
        File file = new File("Distribution.txt");
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file)) ;
        write(out);
    }

    public static void main(String[] args) throws IOException {
        Network network = new Network();
        String filename = "/Users/yansong/Desktop/Java/PPInetwork.txt";
        try{
            network.readfile(filename);
        }catch (FileNotFoundException e){
            System.out.println("File " + filename +" is missing, please check file name.");
        }


        //String node3_str = "Q";
        //String node4_str = "R";
        //String node5_str = "Q";
        //String node6_str = "T";
        //String node1_str = "Q";
        //String node2_str = "W";

        //network.addEdge(node1_str, node2_str);

        //network.addEdge(node1_str, node2_str);
        //network.addEdge(node3_str, node4_str);
        //network.addEdge(node5_str, node6_str);
        network.printlist();
        network.printAvgdegree();
        network.printHub();
        network.printDistribution();
        network.printdegree("P61765");
    }
}
