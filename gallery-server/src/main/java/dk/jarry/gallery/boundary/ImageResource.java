package dk.jarry.gallery.boundary;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dk.jarry.gallery.entity.Image;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("images")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Image Resource", description = "All Image Operations")
public class ImageResource {

	@Inject
	ImageService imageService;

	private final MeterRegistry registry;

	ImageResource(MeterRegistry registry) {
		this.registry = registry;
	}

	@POST
	@Operation( //
			description = "Create a new image", //
			operationId = "createImage")
	public Image create(Image image) {
		registry.counter("createPerformed", "type", "natural")
				.increment();
		Timer timer = registry.timer("createTimer");
		return timer.record(
				() -> imageService.create(image));
	}

	@GET
	@Path("{uuid}")
	@Operation( //
			description = "Get a specific image by id", //
			operationId = "readImageByUuid")
	public Image read(@PathParam("uuid") UUID uuid) {
		return imageService.read(uuid);
	}

	@PUT
	@Path("{uuid}")
	@Operation( //
			description = "Update an exiting image", //
			operationId = "updateImageByUuid")
	public Image update(@PathParam("uuid") UUID uuid, Image image) {
		return imageService.update(uuid, image);
	}

	@DELETE
	@Path("{uuid}")
	@Operation( //
			description = "Delete a specific image", //
			operationId = "deleteImageByUuid")
	public void delete(@PathParam("uuid") UUID uuid) {
		imageService.delete(uuid);
	}

	@GET
	@Operation( //
			description = "Get all the images", //
			operationId = "listImage")
	public List<Image> list( //
			@QueryParam("from") Long from, //
			@QueryParam("limit") Long limit) {
		return imageService.list(from, limit);
	}

}
