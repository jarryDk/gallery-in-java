package dk.jarry.gallery.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.jarry.gallery.control.ChuckNorrisJokes;
import dk.jarry.gallery.control.FolderResourceClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class FolderResourceTest {

    @Inject //
    @RestClient //
    FolderResourceClient resourceClient;

    @Test
    public void create() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("create - Folder " + output);
    }

    @Test
    public void read() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("read - Folder [1] " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        output = this.resourceClient.read(uuid);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("read - Folder [2] " + output);
    }

    @Test
    public void update() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("update - Folder [1] " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        JsonObjectBuilder todoUpdateBuilder = Json.createObjectBuilder(output);
        todoUpdateBuilder.add("subject", "new subject");
        var todoUpdated = todoUpdateBuilder.build();

        output = this.resourceClient.update(uuid, todoUpdated);

        assertEquals(todoUpdated.getString("subject"), "new subject");
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("update - Folder [2] " + output);
    }

    @Test
    public void delete() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("delete- Folder " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        this.resourceClient.delete(uuid);

        try {
            output = this.resourceClient.read(uuid);
        } catch (jakarta.ws.rs.WebApplicationException we) {
            assertTrue(we.getResponse().getStatus() == 404);
        }
    }

    JsonObjectBuilder getImageInputJsonObjectBuilder() {
        JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
        createObjectBuilder.add("name", "-name");
        createObjectBuilder.add("subject", getSubject());
        createObjectBuilder.add("body", getBody());
        return createObjectBuilder;
    }

    String getSubject() {
        return "Subject - gallery-st - Timestamp : "
                + ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    }

    String getBody() {
        return ChuckNorrisJokes.getInstance().getRandomJoke();
    }
}
