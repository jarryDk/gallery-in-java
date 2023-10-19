package dk.jarry.gallery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class ImageLike {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", updatable = false, nullable = false)
    @Schema(readOnly = true)
    public UUID uuid;

    @Schema(readOnly = true)
    public ZonedDateTime createdDate;

    @Schema(readOnly = true)
    public ZonedDateTime updatedDate;

    public UUID imageUuid;
    public String ip;
}
