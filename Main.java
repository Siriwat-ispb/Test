// Phukij 6581096 , Siriwat 6581098
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int Hour,Min,EndHour,EndMin;
        System.out.println("=== Start time ===");
        while(true){
            System.out.println("Enter hour digit (0 - 23) = ");
            Hour = input.nextInt();
            if (Hour >=0 && Hour <=23){
                break;
            }
        }
        while(true){
            System.out.println("Enter minute digit (0 - 59) = ");
            Min = input.nextInt();
            if (Min >=0 && Min <=59){
                break;
            }
        }

        System.out.println("\n=== End time ===");
        while(true){
            System.out.println("Enter hour digit (0 - 23) = ");
            EndHour = input.nextInt();
            if (EndHour >=0 && EndHour <=23){
                break;
            }
        }
        while(true){
            System.out.println("Enter minute digit (0 - 59) = ");
            EndMin = input.nextInt();
            if (EndMin >=0 && EndMin <=59){
                break;
            }
        }

        System.out.printf("start time = %02d:%02d, End time = %02d:%02d ",Hour,Min,EndHour,EndMin);
        if(EndHour==Hour && EndMin<Min || EndHour<Hour){System.out.println("(tomorrow)");}
        int FinalHour,FinalMin;
        FinalHour = EndHour-Hour;
        if (FinalHour < 0){FinalHour+=24;}
        FinalMin = EndMin-Min;
        if(FinalMin<0){FinalMin+=60; FinalHour-=1;}

        System.out.printf("Duration = %1d hours, %1d minutes\n",FinalHour,FinalMin);

        //System.out.println("Enter hour digit (0 - 59) = ");
        //int Min = input.nextInt();
    }
}