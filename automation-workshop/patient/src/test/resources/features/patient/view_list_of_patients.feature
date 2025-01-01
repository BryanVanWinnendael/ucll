Feature: View list of patients
  As a caretaker
  I want to get the list of all patients
  In order to consult the data of a certain patient
# Personas
# Martha - caretaker
  Rule: There must be at least one patient registered
    @UI
    Scenario: The list of patients is given when there are registered patients
      Given there are registered patients
      When Martha consults the list of patients
      Then Martha should get a list of all patients
    @UI
    Scenario: A message is given when there are no registered patients
      Given there are no registered patients
      When Martha consults the list of patients
      Then Martha should get a message explaining that there are no registered patients yet
