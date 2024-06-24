package ua.edu.ukma;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ua.edu.ukma.calculations.Evaluator;
import ua.edu.ukma.calculations.Parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class MathServiceStepDefs {
    private Parser parser;
    private Evaluator evaluator;
    private MathRepository repository;
    private MathService service;

    @Before
    public void setUp() {
        parser = new Parser();
        evaluator = new Evaluator();
        repository = mock(MathRepository.class);
        service = new MathService(parser, evaluator, repository);
    }

    @Given("the equation {string}")
    public void theEquation(String equation) {
        when(repository.equationAlreadyExists(equation)).thenReturn(false);
    }

    @When("I save the equation {string}")
    public void iSaveTheEquation(String equation) {
        assertDoesNotThrow(() -> service.saveEquation(equation));
    }

    @Then("the equation is saved successfully")
    public void theEquationIsSavedSuccessfully() {
        verify(repository, times(1)).saveEquation(any(), any(), any());
    }
}
