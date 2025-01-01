package ucll.be.ip.minor.groep1210.Coin;

import ucll.be.ip.minor.groep1210.model.Coin;

public class CoinBuilder {

    private long id = 0;
    private String name;
    private String country;
    private String currency;
    private Double value;
    private int year;

    private CoinBuilder(){}

    public static CoinBuilder Coin() {return new CoinBuilder();}

    public CoinBuilder withId(long id){
        this.id = id;
        return this;
    }

    public CoinBuilder withName(String name){
        this.name = name;
        return this;
    }

    public CoinBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public CoinBuilder withCurrency(String currency){
        this.currency = currency;
        return this;
    }

    public CoinBuilder withValue(Double value){
        this.value = value;
        return this;
    }

    public CoinBuilder withYear(int year){
        this.year = year;
        return this;
    }

//    valid

    public static CoinBuilder aValidCoinTestcoin(){
        return Coin().withName("Testcoin").withCountry("Belgium").withCurrency("euro").withValue(8.2).withYear(2000);
    }

    public static CoinBuilder aValidCoinTestcoin2(){
        return Coin().withName("Testcoin2").withCountry("Netherlands").withCurrency("yen").withValue(5.9).withYear(1200);
    }

    public static CoinBuilder aValidCoinTestcoin3(){
        return Coin().withName("Testcoin3").withCountry("France").withCurrency("dollar").withValue(4.2).withYear(1450);
    }

    public static CoinBuilder aValidCoinWithDuplicateName(){
        return Coin().withName("Testmunt").withCountry("France").withCurrency("pessos").withValue(1.4).withYear(135).withId(1);
    }

    //    invalid

    public static CoinBuilder anInvalidCoinWithNoName(){
        return Coin().withCountry("Belgium").withCurrency("euro").withValue(3.2).withYear(1500);
    }

    public static CoinBuilder anInvalidCoinWithNoCountry(){
        return Coin().withName("No Country").withCurrency("dollar").withValue(7.2).withYear(1600);
    }

    public static CoinBuilder anInvalidCoinWithNoCurrency(){
        return Coin().withName("No Currency").withCountry("Belgium").withValue(3.2).withYear(1800);
    }

    public static CoinBuilder anInvalidCointWithNoValue(){
        return Coin().withName("Novalue").withCountry("Belgium").withCurrency("pessos").withYear(1850);
    }

    public static CoinBuilder anInvalidCoinWithValueLessThan0(){
        return Coin().withName("Valuelessthanzero").withCountry("Belgium").withCurrency("euro").withValue(-1.0).withYear(1800);
    }

    public static CoinBuilder anInvalidCointWithNoYear(){
        return Coin().withName("Noyear").withCountry("Belgium").withCurrency("dollar").withValue(3.2);
    }

    public static CoinBuilder anInvalidCoinWithYearGreaterThan2022(){
        return Coin().withName("Yeargreaterthan2022").withCountry("Belgium").withCurrency("euro").withValue(3.2).withYear(2050);
    }

    public Coin build(){
        Coin coin = new Coin();
        coin.setId(id);
        coin.setNaam(name);
        coin.setLand(country);
        coin.setMunteenheid(currency);
        coin.setWaarde(value);
        coin.setJaartal(year);
        return coin;
    }





}
