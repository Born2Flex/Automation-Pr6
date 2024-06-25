package ua.edu.ukma;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.edu.ukma.calculations.Evaluator;
import ua.edu.ukma.calculations.Parser;
import ua.edu.ukma.calculations.exceptions.EquationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MathServiceTest {
    private Parser parser;
    private Evaluator evaluator;
    private MathRepository repository;
    @BeforeEach
    void prepare() {
        parser = mock(Parser.class);
        evaluator = mock(Evaluator.class);
        repository = mock(MathRepository.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2 * x + 5 = 17", "3 * x - 4 = 8", "x / 2 + 1 = 3"})
    void saveEquationTest_CorrectEquation(String equation) {
        MathService service = new MathService(parser, evaluator, repository);
        when(parser.validateParentheses(anyString())).thenReturn(true, true);
        assertDoesNotThrow(() -> {
            service.saveEquation(equation);
        });
        verify(repository, times(1)).saveEquation(any(), any(), any());
        verify(parser, times(2)).validateParentheses(anyString());
        verify(parser, times(2)).parseExpr(anyString());
    }

    @Test
    void testSaveEquation_InvalidEquation() {
        Assumptions.assumeFalse(System.getProperty("os.name").contains("Windows"), "Test disabled on Windows");
        MathService mathService = new MathService(parser, evaluator, repository);
        when(parser.validateParentheses(anyString())).thenReturn(false);
        EquationException exception = assertThrows(EquationException.class, () -> mathService.saveEquation("invalid equation"));
        verify(parser, never()).validateParentheses(anyString());
        verify(repository, never()).equationAlreadyExists(anyString());
        verify(repository, never()).saveEquation(anyString(), anyString(), anyString());
        assertEquals("Incorrect equation!", exception.getMessage());
    }
}