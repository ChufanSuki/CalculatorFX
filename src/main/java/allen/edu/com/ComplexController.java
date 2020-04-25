package allen.edu.com;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ComplexController implements Initializable {
    public Menu mSimple;
    public Button Equal;
    public Button Left;
    public Button AC;
    public Button Inverse;
    public Button Percent;
    public Button Divide;
    public Button Right;
    public Button Seven;
    public Button Eight;
    public Button Nine;
    public Button Multiply;
    public Button Four;
    public Button Five;
    public Button Six;
    public Button Minus;
    public Button One;
    public Button Two;
    public Button Three;
    public Button Plus;
    public Button Sin;
    public Button Zero;
    public Button Dot;
    public Label output;
    public Button Sqrt;
    public Button Tan;
    public Button Cos;

    Stage stage;

    @FXML
    public MenuItem simple;

    @FXML
    public AnchorPane root;

    public void handleMenuAction(Event event) throws IOException {
        if (event.getSource() == simple) {
            stage = (Stage) root.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(" main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void handleButtonAction(Event event) throws ScriptException {

        if (event.getSource() == One) {
            output.setText(setNumber("1"));
        } else if (event.getSource() == Two) {
            output.setText(setNumber("2"));
        } else if (event.getSource() == Three) {
            output.setText(setNumber("3"));
        } else if (event.getSource() == Four) {
            output.setText(setNumber("4"));
        } else if (event.getSource() == Five) {
            output.setText(setNumber("5"));
        } else if (event.getSource() == Six) {
            output.setText(setNumber("6"));
        } else if (event.getSource() == Seven) {
            output.setText(setNumber("7"));
        } else if (event.getSource() == Eight) {
            output.setText(setNumber("8"));
        } else if (event.getSource() == Nine) {
            output.setText(setNumber("9"));
        } else if (event.getSource() == Zero) {
            output.setText(setNumber("0"));
        } else if (event.getSource() == Inverse) {
            output.setText("-" + output.getText());
        } else if (event.getSource() == AC) {
            AC.setText("AC");
            output.setText("0");
        } else if (event.getSource() == Divide) {
            output.setText(output.getText() + "/");
        } else if (event.getSource() == Plus) {
            output.setText(output.getText() + "+");
        } else if (event.getSource() == Minus) {
            output.setText(output.getText() + "-");
        } else if (event.getSource() == Multiply) {
            output.setText(output.getText() + "*");
        } else if (event.getSource() == Equal) {
            output.setText(""+eval(output.getText()));
        } else if (event.getSource() == Left) {
            if (output.getText().equals("0"))
                output.setText("(");
            else output.setText( output.getText()+ "(" );
        } else if (event.getSource() == Right) {
            output.setText(output.getText() + ")");
        } else if (event.getSource() == Percent) {
            double data = eval(output.getText());
            data /= 100;
            output.setText(data + "");
        } else if (event.getSource() == Sin) {
            if (output.getText().equals("0"))
                output.setText("sin");
            output.setText(output.getText() + "sin");
        } else if (event.getSource() == Cos) {
            if (output.getText().equals("0"))
                output.setText("sin");
            output.setText(output.getText() + "cos");
        } else if (event.getSource() == Tan) {
            if (output.getText().equals("0"))
                output.setText("sin");
            output.setText(output.getText() + "tan");
        }
    }

    private String setNumber(String append) {
        if (output.getText().equals("0"))
            return append;
        else
            return output.getText()+append;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

//    private String eval(String expression) throws ScriptException {
//        ScriptEngineManager mgr = new ScriptEngineManager();
//        ScriptEngine engine = mgr.getEngineByName("JavaScript");
//        System.out.println(engine.eval(expression));
//        return (String) engine.eval(expression);
//    }

//    public static double eval(String op, double val1, double val2) {
//        if (op.equals("+")) return val1 + val2;
//        if (op.equals("-")) return val1 - val2;
//        if (op.equals("/")) return val1 / val2;
//        if (op.equals("*")) return val1 * val2;
//        throw new RuntimeException("Invalid operator");
//    }
//
//    public double evalHelpper(String expression) {
//        TreeMap<String, Integer> precedence = new TreeMap<>();
//        precedence.put("(", 0);   // for convenience with algorithm
//        precedence.put(")", 0);
//        precedence.put("+", 1);   // + and - have lower precedence than * and /
//        precedence.put("-", 1);
//        precedence.put("*", 2);
//        precedence.put("/", 2);
//
//        Stack<String> ops  = new Stack<String>();
//        Stack<Double> vals = new Stack<Double>();
//
//        while (!expression.isEmpty()) {
//
//            // read in next token (operator or value)
//            String s = StdIn.readString();
//
//            // token is a value
//            if (!precedence.containsKey(s)) {
//                vals.push(Double.parseDouble(s));
//                continue;
//            }
//
//            // token is an operator
//            while (true) {
//
//                // the last condition ensures that the operator with higher precedence is evaluated first
//                if (ops.isEmpty() || s.equals("(") || (precedence.get(s) > precedence.get(ops.peek()))) {
//                    ops.push(s);
//                    break;
//                }
//
//                // evaluate expression
//                String op = ops.pop();
//
//                // but ignore left parentheses
//                if (op.equals("(")) {
//                    assert s.equals(")");
//                    break;
//                }
//
//                // evaluate operator and two operands and push result onto value stack
//                else {
//                    double val2 = vals.pop();
//                    double val1 = vals.pop();
//                    vals.push(eval(op, val1, val2));
//                }
//            }
//        }
//
//        // finished parsing string - evaluate operator and operands remaining on two stacks
//        while (!ops.isEmpty()) {
//            String op = ops.pop();
//            double val2 = vals.pop();
//            double val1 = vals.pop();
//            vals.push(eval(op, val1, val2));
//        }
//
//        // last value on stack is value of expression
//        return vals.pop();
//        assert vals.isEmpty();
//        assert ops.isEmpty();
//    }
}
