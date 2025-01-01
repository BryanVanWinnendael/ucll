package ucll.be.ip.minor.groep1210.collector;

import ucll.be.ip.minor.groep1210.model.Collector;

public class CollectorBuilder {

    private String naam;
    private String voornaam;
    private String regio;
    private Long leeftijd;

    private CollectorBuilder () {
    }

    public static CollectorBuilder aCollector () {
        return new CollectorBuilder();
    }

    public static CollectorBuilder aCollectorTim() {
        return aCollector().withNaan("Timmermans").withVoornaam("Tim").withRegio("Brussel").withLeeftijd(58L);
    }

    public static CollectorBuilder aCollectorDoja() {
        return aCollector().withNaan("Cat").withVoornaam("Doja").withRegio("America").withLeeftijd(26L);
    }

    public static CollectorBuilder aCollectorJohn() {
        return aCollector().withNaan("Johnssons").withVoornaam("John").withRegio("Leuven").withLeeftijd(36L);
    }

    /* Invalids */

    public static CollectorBuilder anInvalidCollectorWithNoNaam() {
        return aCollector().withNaan("").withVoornaam("John").withRegio("Leuven").withLeeftijd(36L);
    }

    public static CollectorBuilder anInvalidCollectorWithNaamTooShort() {
        return aCollector().withNaan("az").withVoornaam("John").withRegio("Leuven").withLeeftijd(36L);
    }

    public static CollectorBuilder anInvalidCollectorWithNoVoornaam() {
        return aCollector().withNaan("Johnssons").withVoornaam("").withRegio("Leuven").withLeeftijd(36L);
    }

    public static CollectorBuilder anInvalidCollectorWithLeeftijdTooSmall() {
        return aCollector().withNaan("Johnssons").withVoornaam("John").withRegio("Leuven").withLeeftijd(15L);
    }

    public static CollectorBuilder anInvalidCollectorWithLeeftijdTooBig() {
        return aCollector().withNaan("Johnssons").withVoornaam("John").withRegio("Leuven").withLeeftijd(110L);
    }

    public static CollectorBuilder anInvalidCollectorWithNoRegio() {
        return aCollector().withNaan("Johnssons").withVoornaam("John").withRegio("").withLeeftijd(36L);
    }

    public CollectorBuilder withNaan (String naam) {
        this.naam = naam;
        return this;
    }

    public CollectorBuilder withVoornaam (String voornaam) {
        this.voornaam = voornaam;
        return this;
    }

    public CollectorBuilder withRegio (String regio) {
        this.regio = regio;
        return this;
    }

    public CollectorBuilder withLeeftijd (Long leeftijd) {
        this.leeftijd = leeftijd;
        return this;
    }

    public Collector build() {
        Collector collector = new Collector();
        collector.setNaam(naam);
        collector.setVoornaam(voornaam);
        collector.setRegio(regio);
        collector.setLeeftijd(leeftijd);
        return collector;
    }
}
