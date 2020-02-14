public class Node {       //class Node, can also be understood as defining a new type "Node"

    String name_of_node;       //defining a variable with class/type "String", name: "name_of_node"

    public Node(){              //default constructor creating a node with empty name
        this.name_of_node = "";
    }

    public Node(String input_names){                  //this. means self. in Python ,   input is a variable with class String and name "input_name"
        this.name_of_node = input_names;
    }

    //public void setName(String name){
        //this.name_of_node = name;
    //}

    public String returnNode(){    //function returning the String value of node, important for later use
        return this.name_of_node;
    }


    public static void main(String[] args){   // testing function
        Node a = new Node();    //this line define a variable with class Node and name "a" using a default constructor, so it is a node with empty name
        //a.setName("Coco");
        Node b = new Node("A");  // this define a variable with class Node and name "b", which is an input node with name "A"

        System.out.println("Node:" + b.returnNode());  //print the node
    }
}

