import java.util.*;

public class Main{
    public static void main(String[] args){
        Controller control = new Controller();


        boolean run = true;
        while(run == true) {
            Scanner scanner = new Scanner(System.in);
            int Category;
            try{
                System.out.println("Welcome to the Universityr System");
                System.out.println("1.Enter\n2.Exit\nEnter your choice number");
                Category = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a nubmer");
                scanner.next();
                continue;
            }
            if(Category==2) break;
            run = control.login();
        }
    }
}

//example1@gmail.com
//Password: abc