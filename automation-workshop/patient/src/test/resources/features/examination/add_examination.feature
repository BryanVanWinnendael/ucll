Feature: Add examination

    As a caretaker
    I want to register the data of a new physical examination
    In order to get an update of the physical condition of my patient
    
    # Persona's
    # Martha - caretaker
    # Tom - patient

    Rule: The BMI is recalculated when an examination is added

    @UI
    Scenario: The BMI is recalculated when an examination is added
        Given Martha is viewing the patient file of Tom
        When Martha adds a new examination for Tom
        Then the examination should be added to Tom's examinations
        And the BMI should be recalculated


    Rule: The length must be between 30 cm and 300 cm

    @Unit
    Scenario Outline: The examination is added if the patient's length is between 30 and 300 cm
        Given Martha has chosen to add a new examination for Tom
        When Martha registers <length> cm as length
        Then the examination should be added to Tom's examinations

        Examples:
          | length  |
          | 30      |
          | 180     |
          | 300     |

    @Unit
    Scenario Outline: An error message is given if the length is below 30 cm or is above 300 cm
        Given Martha has chosen to add a new examination for Tom
        When Martha registers <length> cm as length
        Then Martha should be given an error message about the invalid length

        Examples:
          | length  |
          | -10     |
          | 0       |
          | 29      |
          | 301     |
          | 10000   |


    Rule: The weight must be between 1000 gr and 300000 gr

    @Unit
    Scenario Outline: The examination is added when the patient's weight is between 1000 gr and 300000 gr
        Given Martha has chosen to add a new examination for Tom
        When Martha registers <weight> gr as weight
        Then the examination should be added to Tom's examinations

        Examples:
          | weight 	|
          | 1000	|
          | 74500	|
          | 300000  |

    @Unit
    Scenario Outline: An error message is given if the weight is below 1000 gr or is above 300000 gr
        Given Martha has chosen to add a new examination for Tom
        When Martha registers <weight> gr as weight
        Then Martha should be given an error message about the invalid weight

        Examples:
          | weight 		|
          | -20000		|
          | 0			|
          | 999         |
          | 300001		|
          | 1000000000	|


    Rule: The examination date can't be in the future

    @Integration
    Scenario: An error message is given if the examination date is in the future
        Given Martha has chosen to add a new examination for Tom
        And today's date is 06-10-2020
        When Martha registers the future date 05-12-2020 as the examination date
        Then Martha should be given an error message explaining that the examination date can't be in the future

