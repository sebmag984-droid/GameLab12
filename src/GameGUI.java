import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GameGUI extends JFrame {
	private static final long serialVersionUID = 1L;

    private JTextArea outputArea;
    private JTextField commandField;

    public GameGUI() {
        super("Blackwood Mansion");

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        commandField = new JTextField();
        JButton enterButton = new JButton("Enter");

        JPanel inputPanel =
                new JPanel(new BorderLayout(5, 5));

        inputPanel.add(commandField, BorderLayout.CENTER);
        inputPanel.add(enterButton, BorderLayout.EAST);

        setLayout(new BorderLayout(5, 5));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(650, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        Game.setGuiOutput(
                new java.util.function.Consumer<String>() {
                    public void accept(final String message) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                outputArea.append(message + "\n\n");

                                outputArea.setCaretPosition(
                                        outputArea.getDocument()
                                                .getLength());
                            }
                        });
                    }
                });

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                submitCommand();
            }
        });

        commandField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                submitCommand();
            }
        });

        setVisible(true);

        Game.print("Welcome to Blackwood Mansion!");
        Game.print(Game.getCurrentRoom());
    }

    private void submitCommand() {
        String command = commandField.getText();
        commandField.setText("");

        outputArea.append("> " + command + "\n");
        Game.processGUICommand(command);
    }
}
