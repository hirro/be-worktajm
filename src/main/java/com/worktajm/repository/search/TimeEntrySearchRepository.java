package com.worktajm.repository.search;

import com.worktajm.domain.TimeEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TimeEntry entity.
 */
public interface TimeEntrySearchRepository extends ElasticsearchRepository<TimeEntry, Long> {
}
