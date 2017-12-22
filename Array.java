package poly;

import java.util.Scanner;
import java.util.Vector;


public class Array{
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