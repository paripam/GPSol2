package find;

import java.util.*;
import java.util.regex.Pattern;

public class Find {

    static Vector<String> textLines = new Vector<>();
    static Pattern p;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;
        String argument;
        System.out.print("Enter argument: ");
        argument = sc.nextLine();
        String[] mas = argument.split(" ");
        if (mas.length > 1){
            String buf = "";
            for (int i = 0; i < mas.length; i++){
                buf += mas[i] + ((i < mas.length - 1) ? "|" : "");
            }
            argument = buf;
        }
        p = Pattern.compile(argument);
        System.out.println("Text lines:");
        while (true) {
            line = sc.nextLine();
            if (line.isEmpty())
                break;
            textLines.add(line);
        }
        System.out.println("Result:");
        for (int i = 0; i < textLines.size(); i++){
            for (int j = 0; j < textLines.get(i).split(" ").length; j++){

                if (checkWord(textLines.get(i).split(" ")[j].replace(";",""))){
                    System.out.println(textLines.get(i));
                    break;
                }else
                    continue;
            }
            continue;
        }
    }

    static boolean checkWord(String word){
        return  p.matcher(word).matches();
    }
}