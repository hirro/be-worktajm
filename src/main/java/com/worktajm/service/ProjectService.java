package com.worktajm.service;

import com.worktajm.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     * @return the persisted entity
     */
    public Project save(Project project);

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    public Page<Project> findAll(Pageable pageable);

    /**
     *  get the "id" project.
     *  @return the entity
     */
    public Project findOne(Long id);

    /**
     *  delete the "id" project.
     */
    public void delete(Long id);

    /**
     * search for the project corresponding
     * to the query.
     */
    public List<Project> search(String query);
}
