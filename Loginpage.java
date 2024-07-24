import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

class MainFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public MainFrame() {
        setTitle("Air Metro");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 204));
        JLabel headerLabel = new JLabel("Welcome to Air Metro", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        inputPanel.add(new JLabel("User Name:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setBackground(new Color(224, 224, 224));

        JButton signUpButton = createStyledButton("Sign Up");
        JButton logInButton = createStyledButton("Log In");

        signUpButton.addActionListener(e -> {
            if (usernameField.getText().isEmpty() && passwordField.getPassword().length == 0) {
                new SignUpDialog(this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please clear the fields before signing up.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        logInButton.addActionListener(e -> logIn());

        buttonPanel.add(signUpButton);
        buttonPanel.add(logInButton);

        // Adding panels to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void logIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean loginSuccess = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username) && userDetails[4].equals(password)) {
                    loginSuccess = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while reading the user details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        if (loginSuccess) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 153, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }
}

class SignUpDialog extends JDialog {

    private JTextField usernameField, cnicField, bankingIdField, phoneField;
    private JPasswordField passwordField;

    public SignUpDialog(Frame owner) {
        super(owner, "Sign Up", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameField = new JTextField();
        cnicField = new JTextField();
        bankingIdField = new JTextField();
        phoneField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("User Name:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("CNIC:"));
        formPanel.add(cnicField);
        formPanel.add(new JLabel("Banking ID Number:"));
        formPanel.add(bankingIdField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton signUpButton = createStyledButton("Sign Up");
        signUpButton.addActionListener(e -> signUp());
        formPanel.add(new JLabel());
        formPanel.add(signUpButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void signUp() {
        String username = usernameField.getText();
        String cnic = cnicField.getText();
        String bankingId = bankingIdField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + cnic + "," + bankingId + "," + phone + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Sign Up Complete!");
            clearFields();
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving your details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        usernameField.setText("");
        cnicField.setText("");
        bankingIdField.setText("");
        phoneField.setText("");
        passwordField.setText("");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 153, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }
}
