package dk.jarry.gallery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@NamedQuery( //
        name = "Folder.findAll", //
        query = "SELECT f FROM Folder f ORDER BY f.name", //
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", updatable = false, nullable = false)
    @Schema(readOnly = true)
    public UUID uuid;

    @Schema(readOnly = true)
    public ZonedDateTime createdDate;

    @Schema(readOnly = true)
    public ZonedDateTime updatedDate;

    public String subject;
    public String body;

    public String name;
}
