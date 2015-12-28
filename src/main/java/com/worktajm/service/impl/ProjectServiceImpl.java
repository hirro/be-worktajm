package com.worktajm.service.impl;

import com.worktajm.service.ProjectService;
import com.worktajm.domain.Project;
import com.worktajm.repository.ProjectRepository;
import com.worktajm.repository.search.ProjectSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
    
    @Inject
    private ProjectRepository projectRepository;
    
    @Inject
    private ProjectSearchRepository projectSearchRepository;
    
    /**
     * Save a project.
     * @return the persisted entity
     */
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        Project result = projectRepository.save(project);
        projectSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one project by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Project findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOne(id);
        return project;
    }

    /**
     *  delete the  project by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
        projectSearchRepository.delete(id);
    }

    /**
     * search for the project corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Project> search(String query) {
        
        log.debug("REST request to search Projects for query {}", query);
        return StreamSupport
            .stream(projectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
