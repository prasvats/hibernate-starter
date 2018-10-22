package starter.hbm;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class AppTest {
  /**
   * One app/server for all the test of this class. If you want to start/stop a new server per test,
   * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
   */
  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  @Test
  public void allPets() {
    get("/pets")
        .then()
        .assertThat()
        .body(equalTo("[{\"id\":1,\"name\":\"Lala\"},{\"id\":2,\"name\":\"Mandy\"},{\"id\":3,\"name\":\"Fufy\"},{\"id\":4,\"name\":\"Dina\"}]"))
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }
}
