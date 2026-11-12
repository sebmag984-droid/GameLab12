import javax.swing.*;
import java.awt.*;

	public class GameGui extends JFrame {

		private static JTextArea outputArea;

		private JTextField inputField;

		public GameGui() {
			setTitle("Text Adventure Game");
			setSize(650, 500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			setLayout(new BorderLayout(10, 10));


			outputArea = new JTextArea();
			outputArea.setEditable(false);
			outputArea.setLineWrap(true);
			outputArea.setWrapStyleWord(true);
			outputArea.setFont(
					new Font("Monospaced", Font.PLAIN, 15));

			JScrollPane scrollPane =
					new JScrollPane(outputArea);

			add(scrollPane, BorderLayout.CENTER);


			JPanel bottomPanel =
					new JPanel(new BorderLayout(5, 5));

			bottomPanel.setBorder(
					BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JLabel promptLabel =
					new JLabel("Enter command:");

			inputField = new JTextField();

			JButton enterButton = new JButton("Enter");

			bottomPanel.add(promptLabel, BorderLayout.WEST);

			bottomPanel.add(inputField, BorderLayout.CENTER);

			bottomPanel.add(enterButton, BorderLayout.EAST);

			add(bottomPanel, BorderLayout.SOUTH);


			enterButton.addActionListener(e -> submitCommand());

			inputField.addActionListener(e -> submitCommand());

			setVisible(true);


			Game.runGameGUI();

			inputField.requestFocusInWindow();
		}

		private void submitCommand() {
			String command = inputField.getText().trim();

			if (command.isEmpty()) {
				return;
			}

			appendOutput("> " + command);

			inputField.setText("");

			Game.processGUICommand(command);

			inputField.requestFocusInWindow();
		}

		public static void appendOutput(String message) {
			if (outputArea == null) {
				System.out.println(message);
				return;
			}

			SwingUtilities.invokeLater(() -> {
				outputArea.append(message + "\n");

				outputArea.setCaretPosition(
						outputArea.getDocument().getLength());
			});
		}

		public static void main(String[] args) {
			SwingUtilities.invokeLater(
					() -> new GameGui());
		}
}
