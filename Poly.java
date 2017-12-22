package poly;

import java.util.Scanner;
import java.util.Vector;


class Array{
    private int initial;
    private long[] nums = new long[Poly.len];

    public Array(){

    }
    public Array(int size){
        initial = size;
    }
    public void setNum(long num, int degree){
        nums[degree] = num + nums[degree];
    }
    public long getNum(int degree){
        return nums[degree];
    }
}

public class Poly {
    public Poly(){

    }
    public Poly(String input){

    }
    public static int len = 10000;
    private Array[] array = new Array[len];


    public Array result(String[] subString){
        Array result = new Array(len);
        return result;
    }

    public Array add(Array x, Array y){//加法
        int i;
        i = 0;
        Array result = new Array();
        for(i = 0; i < len; i++){
            result.setNum(x.getNum(i) + y.getNum(i), i);
        }
        return result;
    }
    public Array sub(Array x, Array y){//减法
        int i;
        i = 0;
        Array result = new Array();
        for(i = 0; i < len; i++){
            result.setNum(x.getNum(i) - y.getNum(i), i);
        }
        return result;
    }

    public boolean geshi(String subString){//正则表达式
        if(!subString.matches(
                "^[+-]?(\\{(\\(([+-]?[0-9]{1,8},[+]?[0-9]{1,6})?\\),)*"
                        + "\\(([+-]?[0-9]{1,8},[+]?[0-9]{1,6})?\\)\\}[+-])*"
                        + "\\{(\\(([+-]?[0-9]{1,8},[+]?[0-9]{1,6})?\\),)*"
                        + "\\(([+-]?[0-9]{1,8},[+]?[0-9]{1,6})?\\)\\}$"))
            return false;
        else
            return true;
    }

    public long[] getPoly(String subString){
        int i, j;
        i = 0;
        j = 0;
        long[] nums = new long[len];
        String[] numString = new String[len];
        numString = subString.split("[{(),}]");
        for(i = 0, j = 0; i < numString.length; i++){
            if(!numString[i].equals("")){
                nums[j++] = Integer.parseInt(numString[i]);
            }
        }
        return nums;
    }

    public Array getArray(long[] num){
        int i;
        i = 0;
        Array array = new Array(len);
        for(i = 0; i < num.length; i = i + 2){
            array.setNum(num[i], (int)num[i+1]);
        }
        return array;
    }

    public String operator(String input){
        int i;
        String op = new String();
        String[] axx = new String[len];
        axx = input.split("\\{(\\([+-]?[0-9]{1,8}\\,[+]?[0-9]{1,6}\\),)*\\([+-]?[0-9]{1,8}\\,[+]?[0-9]{1,6}\\)\\}");
        op = op + "+";
        for(i = 0; i < axx.length; i++){
            if(!axx[i].equals("")){
                op  = op + axx[i];
            }
        }
        return op;
    }

    public void print(Array result){
        int i;
        int judge;
        i = 0;
        judge = 0;
        System.out.print("{");
        for(i=0 ; i < len; i++){
            if(result.getNum(i) != 0){
                //System.out.print(result.getNum(i));
                System.out.print("(" + result.getNum(i) + "," + i + ")" + ",");
                judge = 1;
            }
        }
        if(judge == 1){
            //System.out.println("}");
            System.out.println("\b}");
        }
        else
            System.out.println("()}");
    }

    public void spacedel(String input){//删除空格
        String[] subString = new String[len];
        subString = input.split("\\}[+-]\\{");
		/*  int l,i;
     l=String.lenth;
     for(i=0;i<l;i++)
     {for(p=string;p;p++)
     if(p==' ')
     strcpy(p,p+1);}*/
    }

    public static void main(String argv[]){
        String init = "{(0,0)}";
        int i;
        i = 0;
        while(true){//try catch
            try{
                System.out.println("请输入要计算的多项式：");
                Poly poly = new Poly();
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if(input.startsWith("+") || input.startsWith("-")){
                    input = init + input;
                }
                input = input.replace(" ", "");//
              //  input =input.replace("  ","")//tab
                String[] subString = new String[len];
                subString = input.split("\\}[+-]\\{");
                Array array = new Array();
                Array result = new Array();
                String op = poly.operator(input);
                if(poly.geshi(input) == true){
                    long [] nums = new long[len];
                    for(i = 0; i < subString.length; i++){
                        nums = poly.getPoly(subString[i]);
                        array = poly.getArray(nums);

                        if(op.charAt(i) == '-'){
                            result = poly.sub(result, array);
                        }
                        else if(op.charAt(i) == '+'){
                            result = poly.add(result, array);
                        }
                    }
                    System.out.println("计算结果为：");
                    poly.print(result);
                }
                else
                    System.out.println("输入格式错误");
            }
            catch(Throwable t){
                System.out.println("error：" + t);
                continue;
            }
            finally{

            }
        }
    }

}


