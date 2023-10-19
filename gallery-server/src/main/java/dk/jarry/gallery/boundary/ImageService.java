package dk.jarry.gallery.boundary;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.opentracing.Traced;

import dk.jarry.gallery.entity.Image;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Traced
@RequestScoped
public class ImageService {

    @Inject
    EntityManager entityManager;

    @Transactional
	public Image create(Image image) {
		if (image.uuid != null) {
			throw new WebApplicationException( //
					"Image not valid.", //
					Response.Status.BAD_REQUEST);
		}
		image.createdDate = ZonedDateTime.now();

		entityManager.persist(image);
		entityManager.flush();
		entityManager.refresh(image);

		return image;
	}

	@Transactional
	public Image read(UUID uuid) {
		Image image = entityManager.find(Image.class, uuid);
		if (image != null) {
			return image;
		} else {
			throw new WebApplicationException( //
					"Image with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public Image update(UUID uuid, Image image) {
		Image findImage = entityManager.find(Image.class, uuid);
		if (findImage != null) {
			image.updatedDate = ZonedDateTime.now();
			return entityManager.merge(image);
		} else {
			throw new WebApplicationException( //
					"Image with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public void delete(UUID uuid) {
		Image findImage = entityManager.find(Image.class, uuid);
		if (findImage != null) {
			entityManager.remove(findImage);
		} else {
			throw new WebApplicationException( //
					"Image with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public List<Image> list(Long from, Long limit) {
		return entityManager
				.createNamedQuery("Image.findAll", Image.class)
				.getResultList();
	}

}
