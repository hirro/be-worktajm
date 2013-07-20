//
///*
//* To change this template, choose Tools | Templates
//* and open the template in the editor.
// */
//package com.arnellconsulting.tps.repository.jpa;
//
//import com.arnellconsulting.tps.model.Person;
//import com.arnellconsulting.tps.model.Project;
//import com.arnellconsulting.tps.repository.ProjectRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.dao.DataAccessException;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//
///**
// *
// * @author jiar
// */
//@Slf4j
//public class JpaProjectRepository implements ProjectRepository {
//   @PersistenceContext
//   private transient EntityManager em;
//
//   @Override
//   public List<Project> findAll() {
//      log.debug("Listing all projects");
//
//      final Query query = em.createQuery("SELECT p FROM Project p");
//      final List<Project> results = query.getResultList();
//
//      log.debug("Ran query, found {} entries", results.size());
//
//      return results;
//   }
//
//   @Override
//   public List<Project> findAll(Sort sort) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public <S extends Project> List<S> save(Iterable<S> itrbl) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void flush() {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public Project saveAndFlush(Project t) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void deleteInBatch(Iterable<Project> itrbl) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void deleteAllInBatch() {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public Page<Project> findAll(Pageable pgbl) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public <S extends Project> S save(S s) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public Project findOne(Long id) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public boolean exists(Long id) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public Iterable<Project> findAll(Iterable<Long> itrbl) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public long count() {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void delete(Long id) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void delete(Project t) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void delete(Iterable<? extends Project> itrbl) {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//
//   @Override
//   public void deleteAll() {
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   }
//}
