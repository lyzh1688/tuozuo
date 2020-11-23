package com.tuozuo.tavern.organ.biz.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * 最大匹配分词算法
 *
 * @author JYC506
 *
 */
public class SplitString {
 private Set<String> set = new HashSet<String>();
 private int positiveOver = 0;
 private int reverseOver = 0;
 /**
  * 正向最大匹配
  *
  * @param str 要分词的句子
  * @return
  */
 public String[] positiveSplit(String str, int maxSize) {
  int tem = 0;
  int length = str.length();
  String[] ss = new String[length];
  char[] cc = str.toCharArray();
  for (int i = 0; i < length; i++) {
   positiveOver = 0;
   String sb = this.toStr(cc, i, maxSize);
   ss[tem++] = sb;
   i = i + positiveOver;
  }
  String[] ss2 = new String[tem];
  System.arraycopy(ss, 0, ss2, 0, tem);
  return ss2;
 }
 /**
  * 添加词库
  *
  * @param words
  */
 public void addWord(String[] words) {
  for (String st : words) {
   this.set.add(st);
  }
 }
 /**
  * 逆向最大匹配
  *
  * @param str
  * @param num
  * @return
  */
 public String[] reverseSplit(String str, int num) {
  int tem = 0;
  int length = str.length();
  String[] ss = new String[length];
  char[] cc = str.toCharArray();
  for (int i = str.length() - 1; i > -1; i--) {
   reverseOver = 0;
   String sb = this.toStr2(cc, i, num);
   tem++;
   ss[--length] = sb;
   i = i - reverseOver;
  }
  String[] ss2 = new String[tem];
  System.arraycopy(ss, str.length() - tem, ss2, 0, tem);
  return ss2;
 }
 private String toStr(char[] cs, int start, int num) {
  int num2 = num;
  out: for (int j = 0; j < num; j++) {
   StringBuffer sb = new StringBuffer();
   for (int i = 0; i < num2; i++) {
    if (start + i < cs.length) {
     sb.append(cs[start + i]);
    } else {
     num2--;
     j--;
     continue out;
    }
   }
   if (set.contains(sb.toString())) {
    positiveOver = num2 - 1;
    return sb.toString();
   }
   num2--;
  }
  return String.valueOf(cs[start]);
 }
 private String toStr2(char[] cs, int start, int num) {
  int num2 = num;
  for (int j = 0; j < num; j++) {
   StringBuffer sb = new StringBuffer();
   for (int i = 0; i < num2; i++) {
    int index = start - num2 + i + 1;
    if (index > -1) {
     sb.append(cs[index]);
    } else {
     num2--;
    }
   }
   if (set.contains(sb.toString())) {
    reverseOver = num2 - 1;
    return sb.toString();
   }
   num2--;
  }
  return String.valueOf(cs[start]);
 }
 public static void main(String[] args) {
//  String[] words = new String[] { "我们", "我们五人", "五人一组", "一组" };
  String[] words = new String[] { "上海", "安徽", "六安", "浙江" ,"杭州市","科技"};
  SplitString ss = new SplitString();
  /*添加词到词库*/
  ss.addWord(words);
  String st = "杭州东方网升科技股份有限公司";
//  String st = "我们五人一组";
  System.out.println("脚本之家测试结果：");
  System.out.println("要分词的句子：" + st);
  /*使用两种方式分词，下面我指定最大词长度为4*/
  String[] ss2 = ss.reverseSplit(st, 4);
  String[] ss1 = ss.positiveSplit(st, 4);
  System.out.println("正向最大匹配分词算法分词结果：" + Arrays.toString(ss1));
  System.out.println("逆向最大匹配分词算法分词结果：" + Arrays.toString(ss2));
 }
}