package allen.edu.com;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController implements Initializable {
    @FXML
    private Button dot;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button AC;

    @FXML
    private Button minus;

    @FXML
    private Button plus;

    @FXML
    private Button percent;

    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button invert;

    @FXML
    private Label output;

    @FXML
    private Button zero;

    @FXML
    private Button equal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AC.setText("AC");
        output.setText("0");
        System.out.println("Success");

    }

    private double data = 0.0;
    private String operation = "AC";

    private double readData(String input) {
        if (input.indexOf(".") != input.length()-1) {
            return Double.parseDouble(input);
        }
        return Double.parseDouble(input.substring(0,input.length()-2));
    }

    private String setNumber(String append) {
        if (output.getText().equals("0"))
            return append;
        else
            return output.getText()+append;
    }


    @FXML
    public void handleButtonAction(ActionEvent event) {
        AC.setText("C");
        if (operation.equals("Equal")) {
            output.setText("0");
        }
//        if (operation.equals("Divide") || operation.equals("Multiply")
//            || operation.equals("Plus") || operation.equals("Minus")) {
//
//            if (event.getSource() == divide) {
//                operation = "Divide";
//            } else if (event.getSource() == multiply) {
//                operation = "Multiply";
//            } else if (event.getSource() == minus) {
//                operation = "Minus";
//            } else if (event.getSource() == plus) {
//                operation = "Plus";
//            }
//            return;
//        }

        if (event.getSource() == one) {
            output.setText(setNumber("1"));
        } else if (event.getSource() == two) {
            output.setText(setNumber("2"));
        } else if (event.getSource() == three) {
            output.setText(setNumber("3"));
        } else if (event.getSource() == four) {
            output.setText(setNumber("4"));
        } else if (event.getSource() == five) {
            output.setText(setNumber("5"));
        } else if (event.getSource() == six) {
            output.setText(setNumber("6"));
        } else if (event.getSource() == seven) {
            output.setText(setNumber("7"));
        } else if (event.getSource() == eight) {
            output.setText(setNumber("8"));
        } else if (event.getSource() == nine) {
            output.setText(setNumber("9"));
        } else if (event.getSource() == zero) {
            output.setText(setNumber("0"));
        } else if (event.getSource() == AC) {
            AC.setText("AC");
            output.setText("0");
            operation = "AC";
        } else if (event.getSource() == divide) {
            operation = "Divide";
            data = readData(output.getText());
            output.setText("0");
        } else if (event.getSource() == multiply) {
            operation = "Multiply";
            data = readData(output.getText());
            output.setText("0");
        } else if (event.getSource() == equal) {
            double Secondhand = readData(output.getText());
            switch (operation) {
                case "Multiply":
                    data *= Secondhand;
                    break;
                case "Minus":
                    data -= Secondhand;
                    break;
                case "Divide":
                    if (Secondhand == 0) throw new IllegalArgumentException("Argument 'divisor' is 0");
                    data /= Secondhand;
                    break;
                case "Plus":
                    data += Secondhand;
                    break;
            }
            operation = "Equal";
            output.setText(data+"");
        } else if (event.getSource() == plus) {
            operation = "Plus";
            data = readData(output.getText());
            output.setText("0");
        } else if (event.getSource() == minus) {
            operation = "Minus";
            data = readData(output.getText());
            output.setText("0");
        } else if (event.getSource() == percent) {
            operation = "Percent";
            data = readData(output.getText());
            data /= 100;
            output.setText(data+"");
        } else if (event.getSource() == dot) {
            output.setText(data+".");
        } else if (event.getSource() == invert) {
            output.setText("-"+output.getText());
        }

    }

}
