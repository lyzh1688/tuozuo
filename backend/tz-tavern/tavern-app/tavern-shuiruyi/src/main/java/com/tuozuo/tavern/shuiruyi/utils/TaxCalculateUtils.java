package com.tuozuo.tavern.shuiruyi.utils;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/31 <br>
 */
public class TaxCalculateUtils {

//    增值税开票额100万，增值税税率10%，增值税是10万，实际收入90万，个税=实际收入*核定征收率*无级累进税率-速算扣除数=90*10%*10%-1500=7500
//    无级累进税率，速算扣除数，后台做成配置，前端不用体现
    //目前是固定的10% 1500
    //开票金额是价税合计金额，开票税率如果是3%，开票金额是100万，那么增值税就是100万/(1+3%)*3%

    public static double calValueAddedTax(double invoiceAmount, double tax) {
        return invoiceAmount / (1 + tax) * tax;
    }

    public static double calIncomeTax(double invoiceAmount, double tax, double progressiveTax, double deduction) {
        double realIncome = invoiceAmount - invoiceAmount / (1 + tax) * tax;
        return realIncome * tax * progressiveTax - deduction;
    }

}
