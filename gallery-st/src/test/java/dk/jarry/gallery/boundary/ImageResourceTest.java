package dk.jarry.gallery.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import dk.jarry.gallery.control.ChuckNorrisJokes;
import dk.jarry.gallery.control.ImageResourceClient;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ImageResourceTest {

    @Inject
    @RestClient
    ImageResourceClient resourceClient;

    @Test
    public void create() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("create - Image " + output);

    }

    @Test
    public void read() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("read - Image [1] " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        output = this.resourceClient.read(uuid);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("read - Image [2] " + output);

    }

    @Test
    public void update() {

        JsonObject input = getImageInputJsonObjectBuilder().build();

        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("update - Image [1] " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        JsonObjectBuilder todoUpdateBuilder = Json.createObjectBuilder(output);
        todoUpdateBuilder.add("subject", "new subject");
        var todoUpdated = todoUpdateBuilder.build();

        output = this.resourceClient.update(uuid, todoUpdated);

        assertEquals(todoUpdated.getString("subject"), "new subject");
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("update - Image [2] " + output);

    }

    @Test
    public void delete() {

       JsonObject input = getImageInputJsonObjectBuilder().build();
       
        var output = this.resourceClient.create(input);

        assertEquals(input.getString("subject"), output.getString("subject"));
        assertEquals(input.getString("body"), output.getString("body"));

        System.out.println("delete- Image " + output);

        UUID uuid = UUID.fromString(output.getString("uuid"));

        this.resourceClient.delete(uuid);

        try {
            output = this.resourceClient.read(uuid);
        } catch (jakarta.ws.rs.WebApplicationException we) {
            assertTrue(we.getResponse().getStatus() == 404);
        }
    }

    JsonObjectBuilder getImageInputJsonObjectBuilder(){
        JsonObjectBuilder createObjectBuilder = Json.createObjectBuilder();
        createObjectBuilder.add("name", "-name");
        createObjectBuilder.add("fileName", "-fileName");
        createObjectBuilder.add("subject", getSubject());
        createObjectBuilder.add("body", getBody());
        return createObjectBuilder;
    }

    String getSubject() {
        return "Subject - gallery-st - Timestamp : " + ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    }

    String getBody() {
        return ChuckNorrisJokes.getInstance().getRandomJoke();
    }

}