import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.attribute.AttributeView;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class Main {

    static Network network = new Network();    //put it on top and make it static since  we need to reinitialise it in button 6
    public static void main(String[] args) throws FileNotFoundException {

       // Network[] network = {new Network()};   //make it Arraylist since


        final JFrame frame = new JFrame("Network Analysis");        //main windows
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,700);                             //size of main windows
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));   //setting up layout

        JFrame input = new JFrame("Input new interaction");         //pop up window for inputting new reaction
        JPanel input_panel = new JPanel();
        JTextField xfield = new JTextField(10);             //two input textfield
        JTextField yfield = new JTextField(10);
        input_panel.add(new JLabel("Node 1: "));                //first textfield
        input_panel.add(xfield);
        input_panel.add(Box.createHorizontalStrut(15));
        input_panel.add(new JLabel("Node 2: "));
        input_panel.add(yfield);                                    //second textfiled
        input.add(input_panel);



        JPanel paneldown = new JPanel();                            //the bottom panel in the window which contains buttons

        JButton button1 = new JButton("Load example");                  //creating buttons
        JButton button2  = new JButton("Save Degree Distribution");
        JButton button3 = new JButton("Input new interaction");
        JButton button4 = new JButton("Show results");
        JButton button5 = new JButton("clear output");
        JButton button6 = new JButton("Reset network");
        JButton button7 = new JButton("Show Nodes");
        JButton button8 = new JButton("Show Edges");

        paneldown.add(button1);
        paneldown.add(button2);                         //adding the buttons to the bottom panel
        paneldown.add(button3);
        paneldown.add(button4);
        paneldown.add(button5);
        paneldown.add(button6);
        paneldown.add(button7);
        paneldown.add(button8);

        JTextArea text = new JTextArea("hello");                //textarea allows multiple lines of text，
        //text.setPreferredSize(new Dimension(500,500));
        JScrollPane sp = new JScrollPane(text);                 //scroll pane allows scrolling
        sp.setPreferredSize(new Dimension(500,500));    //set up the size of scroll pane, here we cannot set up the size of TextArea
                                                                        //otherwise the scroll would dispear
        //sp.setBounds(10,60,780,500);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //scrolling policy

        //panelup.add(sp);

        frame.add(sp);                  //then we add the panel to the frame(window)
        frame.add(paneldown);           //also the bottom panel

        frame.setVisible(true);         //make them visible

        button1.addActionListener(new ActionListener() {   //load example, this sector process when user click the corresponding button
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());   //File chooser
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    text.setText(text.getText() + "\n\nInput " + selectedFile.getAbsolutePath());   //text.setText reset the output text,
                    try {                                                   //text.setText(text.getText() + ...) can continue the output text
                        network.readfile(selectedFile.getAbsolutePath());   //.getAbsolutePath() gives the pathway the user selects
                    } catch (FileNotFoundException ex) {           //
                        ex.printStackTrace();
                    }
                }



                //String filename = "/Users/yansong/Desktop/Java/PPInetwork.txt";
                //try{
                  //  network.readfile(filename);
                //}catch (FileNotFoundException e1) {
                  //  JOptionPane.showMessageDialog(null, "File is missing", "Error", JOptionPane.WARNING_MESSAGE);
                //}

                //text.setText(text.getText()+ "\n\nYou have successfully loaded the example network.");
            }
        });

        button2.addActionListener(new ActionListener() {     //save degree distribution
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();                  //file chooser
                jfc.setDialogTitle("Specify a location to save");
                int userSelection = jfc.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION){
                    File fileToSave = jfc.getSelectedFile();                //path user choose to save the file
                    network.distribution();
                    try{
                        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileToSave));
                        network.write(out);
                        text.setText(text.getText() + "\n\nSave successfully to location: "+ fileToSave.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                //JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());   //File chooser




                //int returnValue = jfc.showOpenDialog(null);
                //if (returnValue == JFileChooser.APPROVE_OPTION){
                    //File selectedFile = jfc.getSelectedFile();
                    //network.distribution();
                    //try{
                        //OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(selectedFile.getAbsoluteFile()+"/Degree Distribution.txt"));
                        //network.write(out);
                      //  text.setText(text.getText() + "\n\nSave successfully to path : "+ selectedFile.getAbsoluteFile()+"/Degree Distribution.txt");
                    //} catch (IOException ex) {
                    //    ex.printStackTrace();
                  //  }
                //}

                //File file = new File("Degree Distribution.txt");     //define the name of saved file
                //network.distribution();                                         //compute the distribution using method in Network class
                //try {
                 //   OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file)) ;
                  //  network.write(out);
                   // text.setText(text.getText() + "\n\nSave successfully. The file name is \"Degree Distribution.txt\"");
                //} catch (IOException ex) {
                 //   ex.printStackTrace();
                //}

            }
        });

        button3.addActionListener(new ActionListener() {    //input new reaction
            @Override
            public void actionPerformed(ActionEvent e) {
                //String name = JOptionPane.showInputDialog(input, "please input two nodes");
                String result = String.valueOf(JOptionPane.showConfirmDialog(null, input_panel, "Input two nodes", JOptionPane.DEFAULT_OPTION));
                String node1_str = xfield.getText();                //first node
                String node2_str = yfield.getText();                //second node

                if (node1_str.equals("") || node2_str.equals("")){    //throws error when not both are non-empty
                    text.setText(text.getText() + "\nError: We need two input nodes.");
                }else{
                    network.addEdge(node1_str, node2_str);
                    text.setText(text.getText() + "\nAdding interaction: " + node1_str + "---" + node2_str);
                }
            }
        });


        button4.addActionListener(new ActionListener() {    //shows results, averaged degree, hub....
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText(text.getText() + "\n\nThe total averaged degree of all nodes are: " + network.printAvgdegree() + "\nThe maximum degree is: " + network.find_hub()
                        + "\nNode(s) with maximum degree is(are): " + network.printHub());
            }
        });


        button5.addActionListener(new ActionListener() {   //clear output, reset text
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("");
            }
        });

        button6.addActionListener(new ActionListener() {        //reset network
            @Override
            public void actionPerformed(ActionEvent e) {
                network = new Network();
                text.setText(text.getText() + "\n\nNetwork reset");
            }
        });

        button7.addActionListener(new ActionListener() {   //print notes list
            @Override
            public void actionPerformed(ActionEvent e) {
                String nodelist = network.printlist()[0];
                text.setText(text.getText() + "\n\nNodes list:\n" + nodelist);
            }
        });

        button8.addActionListener(new ActionListener() {  //print edge list
            @Override
            public void actionPerformed(ActionEvent e) {
                String edgelist = network.printlist()[1];
                text.setText(text.getText() + "\n\nEdges list: \n" + edgelist);
            }
        });

        //Network network = new Network();
        //network.readfile("/Users/yansong/Desktop/Java/PPInetwork.txt");
        //network.printlist();
        //network.printAvgdegree();
        //network.printHub();

    }
}
