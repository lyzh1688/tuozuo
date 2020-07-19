package com.tuozuo.tavern.shuiruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum TradeEvent {

    INVEST("1"), CUT_PAYMENT("2");
    private String event;

    public String getEvent() {
        return event;
    }

    TradeEvent(String event) {
        this.event = event;
    }
}
