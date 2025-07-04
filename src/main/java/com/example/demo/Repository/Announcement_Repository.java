package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Announcement;
@Repository
public interface Announcement_Repository extends JpaRepository<Announcement, Long>{
	 @Query("SELECT a FROM Announcement a WHERE a.expiryDate IS NULL OR a.expiryDate >= CURRENT_DATE ORDER BY a.postDate DESC")
	    List<Announcement> findCurrentAnnouncements();
	 
	// For admin - get all announcements sorted by date (newest first)
	    List<Announcement> findAllByOrderByPostDateDesc();
}
