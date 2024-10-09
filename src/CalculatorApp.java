import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CalculatorApp extends JFrame implements ActionListener{
    // Gui Components
    final JFrame frame;
    JTextField output;
    SpringLayout springLayout;

    CalculatorService calculatorService = new CalculatorService();

    DecimalFormat formatOutput = new DecimalFormat("0.######");

    // Button names list
    String[] buttonNames = new String[]{"7","8","9","+","4","5","6","-","1","2","3","*","0",".","/","="};

    // Button logic
    private boolean equalsPressed = false;
    private boolean operatorPressed = false;

    public CalculatorApp() {
        springLayout = new SpringLayout();
        frame = new JFrame(CommonConstants.APP_NAME);
        frame.setLayout(springLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(CommonConstants.APP_WIDTH, CommonConstants.APP_HEIGHT);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        addGuiComponents();

        frame.setVisible(true);
    }

    private void addGuiComponents() {
        addOutput();

        addButtons();
    }

    private void addOutput() {
        JPanel outputPanel = new JPanel();

        output = new JTextField(CommonConstants.OUTPUT_LENGTH);
        output.setEditable(false);
        output.setFont(new Font("dialog", Font.PLAIN, CommonConstants.OUTPUT_FONT_SIZE));
        output.setText("0");
        output.setHorizontalAlignment(JTextField.RIGHT);

        output.setBackground(CommonConstants.OUTPUT_COLOR);

        outputPanel.setBackground(CommonConstants.BACKGROUND_COLOR);

        LineBorder outputBorder = new LineBorder(CommonConstants.OUTPUT_BORDER_COLOR, CommonConstants.OUTPUT_BORDER_SIZE);
//        EmptyBorder emptyBorder = new EmptyBorder(2, 13, 2, 13);

        output.setBorder(outputBorder);

        outputPanel.add(output);

        frame.getContentPane().add(outputPanel);
        springLayout.putConstraint(SpringLayout.NORTH, outputPanel, CommonConstants.OUTPUT_GAP_NORTH, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, outputPanel, CommonConstants.OUTPUT_GAP_EAST, SpringLayout.WEST, this);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(CommonConstants.BUTTON_ROWS, CommonConstants.BUTTON_COLS);

        buttonPanel.setLayout(gridLayout);
        buttonPanel.setBackground(CommonConstants.BACKGROUND_COLOR);

        for (int i = 0; i < CommonConstants.BUTTON_COUNT; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setFont(new Font("dialog", Font.PLAIN, CommonConstants.BUTTON_FONT_SIZE));
            button.setFocusable(false);
            buttonPanel.add(button);
            button.addActionListener(this);
            button.setBackground(CommonConstants.BUTTON_COLOR);

            LineBorder buttonBorder = new LineBorder(CommonConstants.BUTTON_BORDER_COLOR, CommonConstants.BUTTON_BORDER_SIZE);
            EmptyBorder emptyBorder = new EmptyBorder(2, 13, 2, 13); // Insets {top, left, bottom, right}

            button.setBorder(BorderFactory.createCompoundBorder(buttonBorder, emptyBorder));

        }

        gridLayout.setHgap(CommonConstants.BUTTON_HGAP);
        gridLayout.setVgap(CommonConstants.BUTTON_VGAP);


        frame.getContentPane().add(buttonPanel);
        springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, CommonConstants.BUTTON_GAP_NORTH, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, buttonPanel, CommonConstants.BUTTON_GAP_WEST, SpringLayout.WEST, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.matches("[0-9]")) {
            if (equalsPressed || operatorPressed || output.getText().equals("0")) {
                output.setText(command);
            }
            else {
                output.setText(output.getText() + command);
            }

            operatorPressed = false;
            equalsPressed = false;
        }
        else if (command.equals(".")) {
            if (!equalsPressed) {
                if (!output.getText().contains(".")) {
                    output.setText(output.getText() + ".");
                }
                operatorPressed = false;

            } else {
                output.setText("0.");

                operatorPressed = false;
                equalsPressed = false;
            }
        }
        else if (command.equals("=")) {
            if (operatorPressed) {
                return;
            }
            if (equalsPressed) {
                calculatorService.setNum1(calculatorService.result());
                output.setText(formatOutput.format(calculatorService.result()));
            } else {
                calculatorService.setNum2(Double.parseDouble(output.getText()));
                output.setText(formatOutput.format(calculatorService.result()));
            }

            operatorPressed = false;
            equalsPressed = true;
        }
        else { // operator pressed
            calculatorService.setNum1(Double.parseDouble(output.getText()));
            calculatorService.setOperator(command.charAt(0));
            output.setText(command); // display operator

            operatorPressed = true;
            equalsPressed = false;
        }
    }
}
