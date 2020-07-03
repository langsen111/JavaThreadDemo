package com.study.conditionThread;



/**
 * @author harry
 * @create 2020-07-01 16:50
 * @Version 1.0
 */
public enum CountryEnum {

    ONE(1, "qi"), TWO(2, "chu"), THREE(3, "yan"), FOUR(4, "zhao"), FIVE(5, "wei"), SIX(6, "han");


    private Integer retCode;
    private String retMessage;

    public String getRetMessage() {
        return retMessage;
    }

    public Integer getRetCode() {
        return retCode;
    }


    CountryEnum(Integer retCode, String retMessage){
        this.retCode = retCode;
        this.retMessage  = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index){

        CountryEnum[] myArray = CountryEnum.values();
        for(CountryEnum element : myArray){
            if(index == element.getRetCode()){
                return element;
            }
        }
        return null;
    }

}
