Feature: Register patient

    As a caretaker
    I want to register a new patient
    In order to monitor that patient

    # Persona's
    # Martha - caretaker
    # Tom - patient
    # Sara - patient

    Rule: Patient can be registered when all personal and all examination details are provided

    @UI
    Scenario: A new patient is added when all personal and all examination details are filled in correctly
        Given Martha has chosen to add Sara as a patient
        When Martha provides Sara's personal details:
             | social security number   | birth date    | gender    |
             | 3219876540               | 26-03-1995    | female    |
        And Martha provides Sara's first examination details:
             | length   | weight  | examination date    |
             | 174      | 74000   | 07-12-2019          |
        And Martha tries to add Sara
        Then Sara should be registered as a patient
        And the examination should be added to Sara's examinations

    @UI
    Scenario: An error message is given when some required data isn't provided
        Given Martha has chosen to add Sara as a patient
        When Martha does not correctly provide all details for the registration of Sara
        Then Martha should be given an error message explaining that all details need to be provided correctly


    Rule: Patients have a unique social security number

    @Integration
    Scenario: An error message is given if the patient is already registered
        Given Martha has chosen to add Tom as a patient
        But patient Tom with SSN 93051822361 has already been registered
        When Martha registers 93051822361 as SSN
        Then Martha should be given an error message explaining that the patient is already registered


    Rule: A valid social security number contains between 9 and 13 digits

    @Unit
    Scenario Outline: The SSN must contain between 9 and 13 digits
        Given Martha has chosen to add Sara as a patient
        When Martha registers <ssn> as SSN
        Then Sara should be registered as a patient

        Examples:
          | ssn             |
          | 165948268       |
          | 4356285973      |
          | 721506897253    |
          | 3645928537210   |

    @Unit
    Scenario Outline: An error message is given if the SSN contains fewer than 9 or more than 13 digits
        Given Martha has chosen to add Sara as a patient
        When Martha registers <ssn> as SSN
        Then Martha should be given an error message about the invalid SSN

        Examples:
          | ssn                 |
          | 6598312             |
          | 93051822            |
          | 83165948735625      |
          | 54653125879435602   |


    Rule: A patient must be at least 2 years old

    @Unit
    Scenario: An error message is given if the patient is not at least 2 years old
        Given Martha has chosen to add Sara as a patient
        And today's date is 06-10-2020
        When Martha registers 28-01-2019 as birth date for the new patient
        Then Martha should be given an error message explaining that the patient is not old enough

