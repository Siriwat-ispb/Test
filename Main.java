// Phukij 6581096 , Siriwat 6581098
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args){
        int Hour,Min,EndHour,EndMin;
        System.out.println("=== Start time ===");
        while(true){
            System.out.println("Enter hour digit (0 - 23) = ");
            Hour = readHour();
            if (Hour >=0 && Hour <=23){
                break;
            }
        }
        while(true){
            System.out.println("Enter minute digit (0 - 59) = ");
            Min = readMinute();
            if (Min >=0 && Min <=59){
                break;
            }
        }

        System.out.println("\n=== End time ===");
        while(true){
            System.out.println("Enter hour digit (0 - 23) = ");
            EndHour = readHour();
            if (EndHour >=0 && EndHour <=23){
                break;
            }
        }
        while(true){
            System.out.println("Enter minute digit (0 - 59) = ");
            EndMin = readMinute();
            if (EndMin >=0 && EndMin <=59){
                break;
            }
        }

        System.out.printf("\nstart time = %02d:%02d, End time = %02d:%02d ",Hour,Min,EndHour,EndMin);
        if(EndHour==Hour && EndMin<Min || EndHour<Hour){System.out.println("(tomorrow)");}
        int FinalHour,FinalMin;
        FinalHour = EndHour-Hour;
        if (FinalHour < 0){FinalHour+=24;}
        FinalMin = EndMin-Min;
        if(FinalMin<0){FinalMin+=60; FinalHour-=1;}

        System.out.printf("Duration = %1d hours, %1d minutes\n",FinalHour,FinalMin);
    }

    public static int readHour(){
        int Hour;
        Scanner input = new Scanner(System.in);
        Hour=input.nextInt();
        return Hour;
    }
    public static int readMinute(){
        int Min;
        Scanner input = new Scanner(System.in);
        Min=input.nextInt();
        return Min;
    }
}