package ua.edu.ukma;

import org.junit.jupiter.api.*;
import ua.edu.ukma.db.DBInitializer;
import ua.edu.ukma.dto.EquationDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class MathRepositoryTest {
    private static Properties properties;
    private MathRepository mathRepository;

    @Test
    void saveEquationTest() {
        mathRepository.saveEquation("5*x", "5", "5*x=5");
        EquationDto equationDto = mathRepository.findEquationById(1);

        assertThat(equationDto).isNotNull();
        assertThat(equationDto.getEquationId()).isEqualTo(1);
        assertThat(equationDto.getLeftPart()).isEqualTo("5*x");
        assertThat(equationDto.getRightPart()).isEqualTo("5");
        assertThat(equationDto.getEquation()).isEqualTo("5*x=5");
    }

    @Test
    void saveRootTest() {
        mathRepository.saveEquation("6*x", "5", "6*x=5");
        EquationDto equationDto = mathRepository.findEquationById(1);
        mathRepository.saveRoot(equationDto.getEquationId(), 2.0);
        List<String> equationsWithRoot = mathRepository.findEquationsByRoot(2.0);

        assertThat(equationsWithRoot).isNotNull();
        assertThat(equationsWithRoot).hasSize(1);
        assertThat(equationsWithRoot).contains("6*x=5");
    }


    @BeforeAll
    static void setup() throws SQLException {
        properties = new PropertiesLoader().loadProperties();
        new DBInitializer(properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"))
                .initialize();
        Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
        connection.createStatement().executeUpdate("ALTER TABLE equations SET REFERENTIAL_INTEGRITY FALSE");
    }

    @BeforeEach
    void prepare() {
        mathRepository = new MathRepository(properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
        try {
            Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));

            connection.createStatement().execute("TRUNCATE TABLE roots RESTART IDENTITY");
            connection.createStatement().execute("TRUNCATE TABLE equations RESTART IDENTITY");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}