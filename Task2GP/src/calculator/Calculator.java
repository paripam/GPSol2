package calculator;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Введите матиматическое выражение");
		String str = in.nextLine();
		String string = str.replaceAll(",", ".");

		Pattern pattern = Pattern.compile("[0-9]");
		Pattern pattern1 = Pattern.compile("\\*|\\/|\\+|\\-|\\^|\\(|\\)");
		char[] chars = string.toCharArray();
		int i = 0;
		for (i = 0; i < chars.length - 1; i++) {
			Matcher matcher = pattern.matcher(chars[i] + "");
			if (matcher.matches()) {
				Matcher matcher2 = pattern.matcher(chars[i + 1] + "");
				Matcher matcher3 = pattern1.matcher(chars[i + 1] + "");
				if (chars[i + 1] != '.' && !matcher2.matches() && !matcher3.matches()) {
					while (i < chars.length - 1) {
						matcher = pattern.matcher(chars[i + 1] + "");
						matcher3 = pattern1.matcher(chars[i + 1] + "");
						if (matcher.matches()) {
							System.out.println("Невалидная запись: пробел между цифрами");
							System.exit(0);
						}
						if (matcher3.matches()) {
							break;
						}
						i++;
					}

				}
			}
		}

		string = string.replaceAll(" ", "");
		StringBuilder result = makePolskaReverseString(string);
		System.out.println(result);
		if (calculate(result) == 1.0f / 0 || calculate(result) == -1.0f / 0) {
			System.out.println("Деление на 0");
			System.exit(0);
		}
		System.out.println(calculate(result));
	}

	public static StringBuilder makePolskaReverseString(String string) {
		char[] mas = string.toCharArray();
		Stack<Character> stack = new Stack<>();
		StringBuilder resultNumb = new StringBuilder();
		StringBuilder strPolskaResult = new StringBuilder();
		Float number = 0.f;
		Pattern pattern1 = Pattern.compile("[0-9]|\\.");
		Pattern pattern2 = Pattern.compile("\\*|\\/|\\+|\\-|\\^");
		Pattern pattern3 = Pattern.compile("\\(");
		Pattern pattern4 = Pattern.compile("\\)");

		for (int i = 0; i < mas.length; i++) {

			Pattern pattern = Pattern.compile("[a-zA-Z]+");
			Matcher matcher = pattern.matcher(mas[i] + "");
			if (matcher.matches()) {
				System.out.println("Невалидная запись, содержит буквы");
				System.exit(0);
			}

			if (mas[i] == '-' & i == 0) {
				resultNumb.append(mas[i]);
				i++;
			}

			Matcher matcher1 = pattern1.matcher(mas[i] + "");
			if (matcher1.matches()) {
				resultNumb.append(mas[i] + "");
			}

			Matcher matcher2 = pattern2.matcher(mas[i] + "");
			if (matcher2.matches()) {

				if (resultNumb == null || resultNumb.toString().equals("")) {
				} else {
					number = Float.parseFloat(String.valueOf(resultNumb));
					strPolskaResult.append(number + " ");
					resultNumb.setLength(0);
				}
				boolean b = true;
				while (b) {
					if (!stack.isEmpty()) {
						if (getPrioritet(mas[i]) <= getPrioritet(stack.peek())) {
							strPolskaResult.append(stack.pop() + " ");
						} else {
							stack.push(mas[i]);
							break;
						}
					} else {
						stack.push(mas[i]);
						break;
					}
					if (getPrioritet(mas[i]) > getPrioritet(stack.peek())) {
						stack.push(mas[i]);
						break;
					}
				}
				if (mas[i + 1] == '-') {
					resultNumb.append(mas[i + 1]);
					i++;
				}
			}

			Matcher matcher3 = pattern3.matcher(mas[i] + "");
			if (matcher3.matches()) {
				stack.push(mas[i]);
			}

			Matcher matcher4 = pattern4.matcher(mas[i] + "");
			if (matcher4.matches()) {
				number = Float.parseFloat(String.valueOf(resultNumb));
				strPolskaResult.append(number + " ");
				resultNumb.setLength(0);
				boolean d = true;
				while (d) {
					if (getPrioritet(stack.peek()) > 1) {
						strPolskaResult.append(stack.pop() + " ");
					}
					if (getPrioritet(stack.peek()) == 1) {
						stack.pop();
						break;
					}
				}
			}
		}
		while (!stack.empty()) {
			if (resultNumb == null || resultNumb.toString().equals("")) {

			} else {
				number = Float.parseFloat(String.valueOf(resultNumb));
				strPolskaResult.append(number + " ");
				resultNumb.setLength(0);
			}
			strPolskaResult.append(stack.pop() + " ");
		}
		return strPolskaResult;
	}

	public static int getPrioritet(char s) {
		int preoritet = 0;
		switch (s) {
		case '(':
			preoritet = 1;
			break;
		case '+':
			preoritet = 2;
			break;
		case '-':
			preoritet = 2;
			break;
		case '*':
			preoritet = 3;
			break;
		case '/':
			preoritet = 3;
			break;
		case '^':
			preoritet = 4;
			break;
		}
		return preoritet;
	}

	public static float calculate(StringBuilder stringBuilder) {
		float result;

		Stack<Float> numbers = new Stack<>();

		Pattern pattern1 = Pattern.compile("^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$");
		Pattern pattern2 = Pattern.compile("\\*|\\/|\\+|\\-|\\^");

		String polsStroka = String.valueOf(stringBuilder);
		String[] mas = polsStroka.split(" ");

		for (int i = 0; i < mas.length; i++) {

			Matcher matcher1 = pattern1.matcher(mas[i]);
			if (matcher1.matches()) {
				numbers.push(Float.valueOf(mas[i]));
			}

			Matcher matcher2 = pattern2.matcher(mas[i]);
			if (matcher2.matches()) {
				float a = numbers.pop();
				float b = numbers.pop();
				numbers.push(calc(mas[i], a, b));
			}
		}
		result = numbers.pop();
		return result;
	}

	public static float calc(String znak, float a, float b) {
		float c = 0.F;
		switch (znak) {
		case "+":
			c = a + b;
			break;
		case "-":
			c = a - b;
			break;
		case "*":
			c = a * b;
			break;
		case "/":
			c = a / b;
			break;
		case "^":
			c = (float) Math.pow(b, a);
			break;
		default:
			System.out.println("Неверные данные");
			System.exit(0);
			break;
		}
		return c;
	}

}