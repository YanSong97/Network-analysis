
public class Edge {    //class Edge

    Node[] nodes;     //here Node[] define an array in which all elements has class "Node" and name "nodes"


    public Edge(Node node1, Node node2){   //function Edge with two input: node1 and node2, assigning two input to our edge array
        this.nodes = new Node[2];    // new array with 2 elements
        this.nodes[0] = node1;      // assign first element
        this.nodes[1] = node2;      // assign second element

    }

    public Node[] returnEdge(){    // return Edge array, return type "Node[]", so "Node[]" after "public"
        return this.nodes;  //
    }

    public void printEdge(){      //since we cannot print the array straightaway, so we need a function printing the edge
        Node[] node_list = returnEdge();                // assign the edge
        String node1 = node_list[0].returnNode();       // node.returnNode() function returning string value of single node
        String node2 = node_list[1].returnNode();

        System.out.println("Edge: " + node1 + "--------"  + node2);   //print the edge
    }

    public static void main(String[] args){
        Node node1 = new Node("A");         //input first node
        Node node2 = new Node("B");         //input second node
        Edge a = new Edge(node1, node2);            //construct edge
        a.printEdge();                              //print edge
    }


}
