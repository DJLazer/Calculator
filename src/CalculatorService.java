public class CalculatorService {
    private double num1;
    private double num2;
    private char operator;

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }
    
    public double result() {
        return switch(operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }
}

