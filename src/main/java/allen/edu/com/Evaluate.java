package allen.edu.com;


import edu.princeton.cs.algs4.Stack;

public class Evaluate {
    public double eval(String expression) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();
        switch (expression) {
            case "(":
                ;
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "sqrt":
                ops.push(expression);
                break;
            case ")":
                String op = ops.pop();
                double v = vals.pop();
                switch (op) {
                    case "+":
                        v = vals.pop() + v;
                        break;
                    case "-":
                        v = vals.pop() - v;
                        break;
                    case "*":
                        v = vals.pop() * v;
                        break;
                    case "/":
                        v = vals.pop() / v;
                        break;
                    case "sqrt":
                        v = Math.sqrt(v);
                        break;
                }
                vals.push(v);
                break;
            default:
                vals.push(Double.parseDouble(expression));
                break;
        }
        return vals.pop();
    }
}

