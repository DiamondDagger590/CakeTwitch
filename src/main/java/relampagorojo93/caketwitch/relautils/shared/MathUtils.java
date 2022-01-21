package relampagorojo93.caketwitch.relautils.shared;

public class MathUtils {

    public static double formulaToResult(String formula) throws Exception {
        formula.replaceAll("[^0-9./*-+^]", "");
        int index = -1;
        while ((index = formula.lastIndexOf('(')) != -1) {
            int finalindex = -1;
			if ((finalindex = formula.indexOf(')', index)) == -1) {
				throw new Exception("Formula contains regions that aren't closed");
			}
            formula = formula.substring(0, index) + formulaToResult(formula.substring(index + 1, finalindex)) + formula.substring(finalindex + 1);
        }
        String operators = formula.replaceAll("[0-9.]{0,}", "");
        double[] numbers = new double[operators.length() + 1];
        index = 0;
        for (String s : formula.split("[/*-+^]{1}")) {
			if (s.charAt(0) == '#') {
				numbers[index++] = Math.sqrt(Double.parseDouble(s.substring(1)));
			}
			else {
				numbers[index++] = Double.parseDouble(s);
			}
        }
        index = -1;
        for (String op : new String[]{"^", "*", "/", "+", "-"}) {
            while ((index = operators.indexOf(op)) != -1) {
                double[] nnumbers = new double[numbers.length - 1];
				for (int i = 0; i < index; i++) {
					nnumbers[i] = numbers[i];
				}
				for (int i = index + 2; i < numbers.length; i++) {
					nnumbers[i - 1] = numbers[i];
				}
                switch (op) {
                    case "^":
                        nnumbers[index] = Math.pow(numbers[index], numbers[index + 1]);
                        break;
                    case "*":
                        nnumbers[index] = numbers[index] * numbers[index + 1];
                        break;
                    case "/":
                        nnumbers[index] = numbers[index] / numbers[index + 1];
                        break;
                    case "+":
                        nnumbers[index] = numbers[index] + numbers[index + 1];
                        break;
                    case "-":
                        nnumbers[index] = numbers[index] - numbers[index + 1];
                        break;
                    default:
                        break;
                }
                operators = operators.substring(0, index) + operators.substring(index + 1);
                numbers = nnumbers;
            }
        }
        return numbers[0];
    }

    public static int formulaToInt(String formula) throws Exception {
        return (int) formulaToResult(formula);
    }

    public static double round(double n, int z) {
        double p = Math.pow(10, z);
        return (double) ((int) (n * p)) / p;
    }
}
