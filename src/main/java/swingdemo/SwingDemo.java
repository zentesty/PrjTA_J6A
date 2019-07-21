package swingdemo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SwingDemo {

    private JTree tree;

    public void calltest(){
        try{
            Thread.sleep(125);
        } catch(Exception e){

        }

    }


    public static void main(String[] args) {
        SwingDemo demo = new SwingDemo();
//        demo.calltest();


        System.out.println("ZEN: Initializing the Swing Frame loaded with the SwingInsector");

        //Creating the Frame
        final JFrame frame = new JFrame("MP Test APP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);


        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);


        // 1 ier
        JPanel panelTest = new JPanel();
        JLabel jLabel1 = new JLabel("test1");
        JLabel jLabel2 = new JLabel("test2");
        JLabel jLabel3 = new JLabel("test3");
        panelTest.add("MPPANEL1", jLabel1);
        panelTest.add("MPPANEL2", jLabel2);
        panelTest.add("MPPANEL3", jLabel3);

        JPanel panelTestIn = new JPanel();
        JLabel jLabelIn1 = new JLabel("Intest1");
        JLabel jLabelIn2 = new JLabel("Intest2");
        panelTestIn.add("INMPPANEL1", jLabelIn1);
        panelTestIn.add("INMPPANEL2", jLabelIn2);


        JPanel panelTestB = new JPanel();
        JLabel jLabel1B = new JLabel("test1B");
        JLabel jLabel2B = new JLabel("test2B");
        JLabel jLabel3B = new JLabel("test3B");
        panelTestB.add(jLabel1B);
        panelTestB.add(jLabel2B);
        panelTestB.add(jLabel3B);

        panelTest.add(panelTestIn);



        // 2 ieme

        JPanel panelTestInB = new JPanel();
        JLabel jLabelIn1B = new JLabel("Intest1B");
        JLabel jLabelIn2B = new JLabel("Intest2B");
        panelTestInB.add(jLabelIn1B);
        panelTestInB.add(jLabelIn2B);


        panelTestB.add(panelTestInB);

        // Add to main
        JPanel panelMain = new JPanel();
        panelMain.add(panelTest);
        panelMain.add(panelTestB);

        panelTest.setName("ZenPanel01");
        panelTestIn.setName("ZenPanel02");
        panelMain.setName("ZenCentral");
        panel.setName("ZenTOP_PANEL");

  

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ZEN: Button SEND pressed");

                JOptionPane.showMessageDialog(frame,
                        "Button Send was clicked",
                        "SEND",
                        JOptionPane.WARNING_MESSAGE);

            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ZEN: Button RESET pressed");

                JOptionPane.showMessageDialog(frame,
                        "Button Reset was clicked",
                            "RESET",
                        JOptionPane.WARNING_MESSAGE);
            }
        });


        // Text Area at the Center
        JTextArea ta = new JTextArea();

        JScrollPane jpane = demo.TreeDemo();


        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
//        frame.getContentPane().add(BorderLayout.CENTER, ta);
//        frame.getContentPane().add(BorderLayout.CENTER, jpane);
        frame.getContentPane().add(BorderLayout.CENTER, panelMain);

        frame.setVisible(true);
    }


    /**
     *  TREE DEMO
     *
     */
    public JScrollPane TreeDemo() {
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("The Java Series");
        createNodes(top);
        tree = new JTree(top);
        JScrollPane treeView = new JScrollPane(tree);

        return treeView;
    }


    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);

        //original Tutorial
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Tutorial: A Short Course on the Basics",
                        "tutorial.html"));
        category.add(book);

        //Tutorial Continued
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Tutorial Continued: The Rest of the JDK",
                        "tutorialcont.html"));
        category.add(book);

        //Swing Tutorial
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Swing Tutorial: A Guide to Constructing GUIs",
                        "swingtutorial.html"));
        category.add(book);

        //...add more books for programmers...

        category = new DefaultMutableTreeNode("Books for Java Implementers");
        top.add(category);

        //VM
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Virtual Machine Specification",
                        "vm.html"));
        category.add(book);

        //Language Spec
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Language Specification",
                        "jls.html"));
        category.add(book);
    }
}