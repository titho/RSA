import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class GUIFrame extends JFrame {
    private int k = 0;
    private int t = 0;
    private int p = 0;
    private String outputFile = "";


    public GUIFrame() {
        super("E");
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e3) {
            e3.printStackTrace();
        }


        JTextArea console = new JTextArea();
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setEditable(false);
        JScrollPane scroll = new JScrollPane(console);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Box consoleBox = Box.createHorizontalBox();

        JTextField kField = new JTextField(30);
        JTextField tField = new JTextField(30);
        JTextField pField = new JTextField(30);
        pField.setText("10000");

        JTextField outputField = new JTextField(10);
        outputField.setEnabled(true);
        outputField.setText("result.txt");

        JButton run = new JButton("Run");
        run.addActionListener(e -> {
            try {
                if (outputField.getText() != null) {
                    outputFile = outputField.getText();
                }
            } catch (Exception e1) {
                console.append("Choose output file\n");
            }


            try {
                k = Integer.parseInt(kField.getText());
                t = Integer.parseInt(tField.getText());
                p = Integer.parseInt(pField.getText());

                if (t <= 0) {
                    throw new IllegalAccessException("Enter positive number of threads");
                }

            } catch (Exception e1) {
                console.append("Enter correct number values\n");
            }

            Main.executeInConsole(k, t, p, false, outputFile, console);

            console.append("\n");
        });

        Container container = super.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 3));

        Box sizesBox = Box.createHorizontalBox();
        sizesBox.add(Box.createRigidArea(new Dimension(5, 0)));
        sizesBox.add(new JLabel("k = "));
        sizesBox.add(kField);
        sizesBox.add(Box.createRigidArea(new Dimension(10, 0)));
        sizesBox.add(new JLabel("number of threads"));
        sizesBox.add(tField);
        sizesBox.add(Box.createRigidArea(new Dimension(10, 0)));
        sizesBox.add(new JLabel("precision"));
        sizesBox.add(pField);
        sizesBox.add(Box.createRigidArea(new Dimension(5, 0)));
        sizesBox.setBorder(BorderFactory.createLoweredBevelBorder());

        Box outputBox = Box.createHorizontalBox();
        outputBox.add(Box.createRigidArea(new Dimension(5, 0)));
        outputBox.add(new JLabel("File name:"));
        outputBox.add(Box.createRigidArea(new Dimension(5, 0)));
        outputBox.add(outputField);
        outputBox.add(Box.createRigidArea(new Dimension(5, 0)));
        outputBox.setBorder(BorderFactory.createLoweredBevelBorder());


        Box menuBox = Box.createHorizontalBox();
        menuBox.add(Box.createRigidArea(new Dimension(5, 0)));
        menuBox.add(sizesBox);
        menuBox.add(Box.createRigidArea(new Dimension(5, 0)));
        menuBox.add(outputBox);
        menuBox.add(Box.createRigidArea(new Dimension(5, 0)));


        Box box = Box.createVerticalBox();
        box.add(Box.createRigidArea(new Dimension(0, 5)));
        box.add(menuBox);
        box.add(Box.createRigidArea(new Dimension(0, 5)));
        box.add(run);
        box.add(Box.createRigidArea(new Dimension(0, 5)));

        menu.add(box);

        container.add(menu, BorderLayout.NORTH);
        container.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
