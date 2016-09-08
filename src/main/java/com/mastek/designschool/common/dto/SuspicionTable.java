package com.mastek.designschool.common.dto;

public class SuspicionTable {
	
	public static final int ADDRESS=0;
	public static final int PHONE_NO=1;
	public static final int EMAIL_ADD=2;
	public static final int PIN=3;
	public static final int EMPLOYER_NAME=4;
	public static final int BANK_NAME=5;
	public static final int BANK_ACC_NO=6;
	
	
	public static final double suspicionMatrix[][]={
			{0.1,  2.5,  0.35, 1, 0.15, 0.15, 0.9},
			{0.25, 0.2,  0.7,  1, 0.25, 0.25, 0.9},
			{0.35, 0.7,  0.3,  1, 0.55, 0.35, 0.9},
			{1.0,  1.0,  1.0,  1, 1.0 , 1.0,  1.0},
			{0.15, 0.25, 0.55, 1, 0.03, 0.05, 0.9},
			{0.15, 0.25, 0.35, 1, 0.05, 0.03, 0.9},
			{0.9,  0.9,  0.9,  1, 0.9,  0.9,  0.9}	
	};
	
	public static double [] ADDRESS_MULTIPLIER={1,1,3,3,8,8,8,8,10,10};
	public static double [] PIN_MULTIPLIER={1,1,1,1,1,1,1};
	public static double [] BANK_ACC_NO_MULTIPLIER={1,1,3,3,8,8,8,8,10,10};
	public static double [] PHONE_NO_MULTIPLIER={1,1,5,5,5,5,5};
	public static double [] EMAIL_ADD_MULTIPLIER={1,1,3.3,3.3,3.3,3.3,3.3};
	public static double [] EMPLOYER_MULTIPLIER={1,1,1,1,1,1,1};
	public static double [] BANK_NAME_MULTIPLIER={1,1,1,1,1,1,1};
}
