Feature: Consult patient details

    As a caretaker
    I want to consult the personal details of a patient
    In order to check the physical condition of that patient
    
    # Persona's
    # Martha - caretaker
    # Tom - patient
    # Sara - patient

    Rule: The most recent BMI is given

    @Unit
    Scenario: The most recent BMI is given
        Given Tom's last examined length is 184 cm
        And Tom's last examined weight is 76000 gr
        When Martha requests the patient details of Tom
        Then a BMI of 22.4 should be given


    Rule: The patient must be registered

    @UI
    Scenario: Patient details are given if the patient exists
        Given patient Tom is registered
        When Martha requests the patient details of Tom
        Then Tom's details should be given
        And Tom's BMI from the last examination should be given

    @Integration @UI
    Scenario: An error is given if the patient does not exist
        Given patient Sara is not registered
        When Martha requests the patient details of Sara
        Then Martha should be given an error message explaining that the requested patient does not exist

