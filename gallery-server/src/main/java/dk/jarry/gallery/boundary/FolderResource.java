package dk.jarry.gallery.boundary;

import dk.jarry.gallery.entity.Folder;
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
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("folders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "folder Resource", description = "All folder Operations")
public class FolderResource {

    @Inject FolderService folderService;

    private final MeterRegistry registry;

    FolderResource(MeterRegistry registry) {
        this.registry = registry;
    }

    @POST
    @Operation( //
            description = "Create a new folder", //
            operationId = "createFolder")
    public Folder create(Folder folder) {
        registry.counter("createPerformed", "type", "natural") //
                .increment();
        Timer timer = registry.timer("createTimer");
        return timer.record(() -> folderService.create(folder));
    }

    @GET
    @Path("{uuid}")
    @Operation( //
            description = "Get a specific folder by id", //
            operationId = "readFolderByUuid")
    public Folder read(@PathParam("uuid") UUID uuid) {
        return folderService.read(uuid);
    }

    @PUT
    @Path("{uuid}")
    @Operation( //
            description = "Update an exiting folder", //
            operationId = "updateFolderByUuid")
    public Folder update(@PathParam("uuid") UUID uuid, Folder folder) {
        return folderService.update(uuid, folder);
    }

    @DELETE
    @Path("{uuid}")
    @Operation( //
            description = "Delete a specific folder", //
            operationId = "deleteFolderByUuid")
    public void delete(@PathParam("uuid") UUID uuid) {
        folderService.delete(uuid);
    }

    @GET
    @Operation( //
            description = "Get all the folers", //
            operationId = "listFolder")
    public List<Folder> list( //
            @QueryParam("from") Long from, //
            @QueryParam("limit") Long limit) {
        return folderService.list(from, limit);
    }
}
