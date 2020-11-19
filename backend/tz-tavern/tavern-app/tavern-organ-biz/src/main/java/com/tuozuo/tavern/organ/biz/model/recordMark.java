package com.tuozuo.tavern.organ.biz.model;

import java.util.List;

//记录的标记
public class recordMark {
    String word;//词根
    List<String> wordPinYingList;//词根的单字拼音列表，按照原顺序排列好的

    @Override
    public String toString() {
        return "recordMark{" +
                "word='" + word + '\'' +
                ", wordPinYingList=" + wordPinYingList +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getWordPinYingList() {
        return wordPinYingList;
    }

    public void setWordPinYingList(List<String> wordPinYingList) {
        this.wordPinYingList = wordPinYingList;
    }
}
