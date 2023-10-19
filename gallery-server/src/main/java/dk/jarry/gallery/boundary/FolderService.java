package dk.jarry.gallery.boundary;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.opentracing.Traced;

import dk.jarry.gallery.entity.Folder;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Traced
@RequestScoped
public class FolderService {

    @Inject
    EntityManager entityManager;

    @Transactional
	public Folder create(Folder folder) {
		if (folder.uuid != null) {
			throw new WebApplicationException( //
					"Folder not valid.", //
					Response.Status.BAD_REQUEST);
		}
		folder.createdDate = ZonedDateTime.now();

		entityManager.persist(folder);
		entityManager.flush();
		entityManager.refresh(folder);

		return folder;
	}

	@Transactional
	public Folder read(UUID uuid) {
		Folder folder = entityManager.find(Folder.class, uuid);
		if (folder != null) {
			return folder;
		} else {
			throw new WebApplicationException( //
					"Folder with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public Folder update(UUID uuid, Folder folder) {
		Folder findImage = entityManager.find(Folder.class, uuid);
		if (findImage != null) {
			folder.updatedDate = ZonedDateTime.now();
			return entityManager.merge(folder);
		} else {
			throw new WebApplicationException( //
					"Folder with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public void delete(UUID uuid) {
		Folder findImage = entityManager.find(Folder.class, uuid);
		if (findImage != null) {
			entityManager.remove(findImage);
		} else {
			throw new WebApplicationException( //
					"Folder with uuid of " + uuid + " does not exist.", //
					Response.Status.NOT_FOUND);
		}
	}

	@Transactional
	public List<Folder> list(Long from, Long limit) {
		return entityManager
				.createNamedQuery("Folder.findAll", Folder.class)
				.getResultList();
	}

}
